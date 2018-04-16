package com.uzpeng.sign.interceptor;

import com.uzpeng.sign.support.SessionAttribute;
import com.uzpeng.sign.util.SessionStoreKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author serverliu on 2018/3/30.
 */
public class AuthenticatedInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticatedInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SessionAttribute authInfo = (SessionAttribute)request.getSession().getAttribute(SessionStoreKey.KEY_AUTH);

        if(authInfo == null){
            logger.info("no authentication information!");
            response.setStatus(404);
            response.sendRedirect(request.getContextPath()+"/v1/login");
            return false;
        }

        String authId = (String)authInfo.getObj();
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(SessionStoreKey.KEY_AUTH) && cookie.getValue().equals(authId)) {
                    logger.info("authenticate successfully!");
                    return true;
                }
            }
        }
        logger.info("no authentication information!");
        response.setStatus(404);
//        response.sendRedirect(request.getContextPath()+"/v1/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
