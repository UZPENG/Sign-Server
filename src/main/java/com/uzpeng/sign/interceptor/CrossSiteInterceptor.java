package com.uzpeng.sign.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author serverliu on 2018/4/13.
 */
public class CrossSiteInterceptor implements HandlerInterceptor{
    private static final Logger logger = LoggerFactory.getLogger(CrossSiteInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Method is "+request.getMethod()+" add cross site header!");
        response.setHeader("Access-Control-Allow-Origin", "http://172.29.108.39:8080");
        response.setHeader("Access-Control-Allow-Methods","POST, PUT, GET, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, authorization," +
                "withCredentials, Content-Type, Accept");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials","true");
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(200);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
