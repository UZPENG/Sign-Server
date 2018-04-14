package com.uzpeng.sign.exception;

import com.uzpeng.sign.dao.bo.ErrorBO;
import com.uzpeng.sign.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author serverliu on 2018/3/29.
 */
@Component
public class CommonExceptionHandler extends ExceptionHandlerExceptionResolver {

    @Autowired
    private Environment env;

    @Override
    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception exception) {
        try {
            response.setStatus(500);
            Writer writer = response.getWriter();

            ErrorBO errorBO = new ErrorBO();
            errorBO.setMsg("Internal Error!");
            errorBO.setDoc(env.getProperty("link.doc"));
            writer.write(SerializeUtil.toJson(errorBO, ErrorBO.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
