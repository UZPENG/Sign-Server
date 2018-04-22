package com.uzpeng.sign.net;

import com.uzpeng.sign.config.StatusConfig;
import com.uzpeng.sign.bo.SignHttpLinkBO;
import com.uzpeng.sign.net.dto.SignDTO;
import com.uzpeng.sign.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Base64;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * @author uzpeng on 2018/4/19.
 */
@Component
public class SignWebSocketHandler extends TextWebSocketHandler{
    @Autowired
    private Environment environment;
    private static final Logger logger = LoggerFactory.getLogger(SignWebSocketHandler.class);
    private static final Integer DEFAULT_REFRESH_TIME = 15000;
    private Timer timer = new Timer();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("handleTextMessage:"+payload);

        SignDTO signDTO = SerializeUtil.fromJson(payload, SignDTO.class);
        if(signDTO.getStart().equals(StatusConfig.START)) {
            timer = new Timer();
            SendSignLinkTask sendSignLinkTask = new SendSignLinkTask(session, signDTO);
            logger.info("start construct-sign-link task .....");
            timer.schedule(sendSignLinkTask, 0, DEFAULT_REFRESH_TIME);
        } else {
            logger.info("cancel construct-sign-link task, close websocket session ");

            session.close();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("afterConnectionEstablished");
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("afterConnectionClosed");
        timer.cancel();
        super.afterConnectionClosed(session, status);
    }

    private void constructSignLink(WebSocketSession session, SignDTO signDTO) throws Exception{

        String randomStr = UUID.randomUUID().toString();
        String sourceStr = randomStr+signDTO.getSignId()+signDTO.getCourseId();
        String token = new String(Base64.getEncoder().encode(Sha512DigestUtils.sha(sourceStr)), "utf-8");

        String url = environment.getProperty("link.host") + "/v1/course/"+signDTO.getCourseId()+"/sign/"+signDTO.getSignId()+"?token="+token;
        logger.info("token is "+token+", url:"+url);

        SignHttpLinkBO signHttpLinkBO = new SignHttpLinkBO();
        signHttpLinkBO.setLink(url);

        session.sendMessage(new TextMessage(SerializeUtil.toJson(signHttpLinkBO, SignHttpLinkBO.class)));
    }

    private class SendSignLinkTask extends TimerTask{
        private WebSocketSession session;
        private SignDTO signDTO;

        private SendSignLinkTask(WebSocketSession session, SignDTO signDTO) {
            this.signDTO = signDTO;
            this.session = session;
        }

        @Override
        public void run(){
            try {
                constructSignLink(session, signDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
