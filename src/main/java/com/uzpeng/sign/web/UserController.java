package com.uzpeng.sign.web;

import com.uzpeng.sign.domain.RoleDO;
import com.uzpeng.sign.domain.SessionAttribute;
import com.uzpeng.sign.domain.UserDO;
import com.uzpeng.sign.interceptor.AuthenticatedInterceptor;
import com.uzpeng.sign.service.UserService;
import com.uzpeng.sign.util.CommonResponseHandler;
import com.uzpeng.sign.util.SessionStoreKey;
import com.uzpeng.sign.util.UserMap;
import com.uzpeng.sign.util.VerifyCodeGenerator;
import com.uzpeng.sign.validation.AuthenticatedRequest;
import com.uzpeng.sign.web.dto.EmailDTO;
import com.uzpeng.sign.web.dto.LoginDTO;
import com.uzpeng.sign.web.dto.RegisterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author serverliu on 2018/3/29.
 */
@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/v1/register/verify", method = RequestMethod.GET,
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String sendVerifyCode(@Valid EmailDTO emailDTO, HttpSession session, HttpServletRequest request,
                                 HttpServletResponse response){
        String email = emailDTO.getEmail();
        boolean isValid = userService.checkEmailAddress(email);

        if(isValid){
            SessionAttribute storedSessionAttr = (SessionAttribute)session.getAttribute(SessionStoreKey.KEY_VERIFY_CODE);
            String verifyCode;

            if(storedSessionAttr == null || storedSessionAttr.getExpireDate().isBefore(LocalDateTime.now())) {
                verifyCode = VerifyCodeGenerator.generate();
                HashMap<String, String> verifyCodeMap = new HashMap<>();
                verifyCodeMap.put(email, verifyCode);

                SessionAttribute verifyCodeAttribute = new SessionAttribute(verifyCodeMap, LocalDateTime.now().plusMinutes(30));

                session.setAttribute(SessionStoreKey.KEY_VERIFY_CODE, verifyCodeAttribute);
            } else {
                verifyCode = (String)((Map)storedSessionAttr.getObj()).get(email);
            }
            userService.sendVerifyCodeByEmail(email, verifyCode);

            response.setStatus(200);
            return CommonResponseHandler.handleResponse(env.getProperty("msg.register.verified"),  env.getProperty("link.register"));
        }else {
            response.setStatus(403);
            return CommonResponseHandler.handleResponse(env.getProperty("msg.email.invalid"),  env.getProperty("link.host"));
        }
    }

    @RequestMapping(value = "/v1/register" ,method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String register(@Valid final RegisterDTO registerDTO, HttpSession session, HttpServletResponse response){
        boolean isValid = userService.checkEmailAddress(registerDTO.getEmail());
        if(!isValid){
            return CommonResponseHandler.handleResponse(env.getProperty("msg.email.invalid"),  env.getProperty("link.host"));
        }

        SessionAttribute verifyCodeAttr = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_VERIFY_CODE);

        String errorMsg =  CommonResponseHandler.handleResponse(env.getProperty("msg.register.verify.error"),  env.getProperty("link.host"));
        if(verifyCodeAttr == null){
            return errorMsg;
        }
        Map verifyCodeMap = (Map) verifyCodeAttr.getObj();

        if(!registerDTO.getVerifyCode().equals(verifyCodeMap.get(registerDTO.getEmail()))){
            return errorMsg;
        }

        userService.registerNewUser(registerDTO);

        String cookieValue = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        SessionAttribute authInfo = new SessionAttribute(cookieValue, LocalDateTime.MAX);
        session.setAttribute(SessionStoreKey.KEY_AUTH, authInfo);

        Cookie cookie = new Cookie(SessionStoreKey.KEY_AUTH, cookieValue);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        session.removeAttribute(SessionStoreKey.KEY_VERIFY_CODE);

        return CommonResponseHandler.handleResponse(
                env.getProperty("msg.register.success"),  env.getProperty("link.login"));
    }

    @RequestMapping(value = "/v1/login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String login(@Valid LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response,
                        HttpSession session){

        logger.info("start check password!...");
        Integer id = userService.loginCheck(loginDTO);
        logger.info("finish check password, start set cookie...");

        if(id != null) {
            String cookieValue = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
            SessionAttribute authInfo = new SessionAttribute(cookieValue, LocalDateTime.MAX);
            session.setAttribute(SessionStoreKey.KEY_AUTH, authInfo);

            Cookie cookie = new Cookie(SessionStoreKey.KEY_AUTH, cookieValue);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            RoleDO roleDO = userService.getRole(id);
            UserMap.putId(cookieValue, roleDO);

            logger.info("Login successfully! user is is "+roleDO.getRoleId());

            return CommonResponseHandler.handleResponse(
                    env.getProperty("msg.login.success"),  env.getProperty("link.login"));
        } else {
            return CommonResponseHandler.handleResponse(
                    env.getProperty("msg.login.error"),  env.getProperty("link.host"));
        }
    }

    @RequestMapping(value = "/v1/user/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getUserInfo(@PathVariable(name = "id") String id, final HttpServletRequest request, HttpSession session){
        UserDO userInfo = userService.getUserInfo(id);
        return CommonResponseHandler.handleResponse(userInfo, UserDO.class);
    }

    @RequestMapping(value = "/v1/logout", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String logout(final HttpServletResponse response, HttpSession session){
        session.removeAttribute(SessionStoreKey.KEY_AUTH);

        Cookie cookie = new Cookie(SessionStoreKey.KEY_AUTH, "");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return CommonResponseHandler.handleResponse(
                env.getProperty("msg.logout.success"),  env.getProperty("link.login"));
    }
}
