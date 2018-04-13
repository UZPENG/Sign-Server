package com.uzpeng.sign.service;

import com.uzpeng.sign.config.EmailConfig;
import com.uzpeng.sign.dao.UserDAO;
import com.uzpeng.sign.domain.RoleDO;
import com.uzpeng.sign.domain.UserDO;
import com.uzpeng.sign.util.ObjectTranslateUtil;
import com.uzpeng.sign.util.ThreadPool;
import com.uzpeng.sign.web.dto.LoginDTO;
import com.uzpeng.sign.web.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author serverliu on 2018/3/29.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private EmailConfig emailConfig;

    public void registerNewUser(RegisterDTO registerDTO){
        userDAO.insertUser(ObjectTranslateUtil.registerDTOToUserDO(registerDTO));
    }

    public Integer loginCheck(LoginDTO loginDTO){
        return userDAO.checkUserAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
    }

    public boolean checkEmailAddress(String email){
        return userDAO.checkEmailValid(email);
    }

    public void sendVerifyCodeByEmail(String email, String code){
        System.out.println("email:"+email+",verify code:"+code);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText(code);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom(emailConfig.getFrom());

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setPort(Integer.parseInt(emailConfig.getPort()));
        mailSender.setHost(emailConfig.getHost());
        mailSender.setUsername(emailConfig.getUserName());
        mailSender.setPassword(emailConfig.getPassword());

        //todo
//        ThreadPool.run(()-> mailSender.send(simpleMailMessage));
    }

    public RoleDO getRole(int id){
        return userDAO.getRole(id);
    }

    public UserDO getUserInfo(String id){
        return userDAO.getUserInfo(Integer.parseInt(id));
    }
}
