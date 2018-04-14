package com.uzpeng.sign.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uzpeng.sign.dao.bo.ErrorBO;
import com.uzpeng.sign.support.CommonResponse;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletResponse;

/**
 * @author serverliu on 2018/4/3.
 */
public class CommonResponseHandler {
    @Autowired
    private static Environment environment;

    private static final  Gson gson = new GsonBuilder().create();

    public static String handleResponse(String status, String msg, String link){

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setStatus(status);
        commonResponse.setMsg(msg);
        commonResponse.setLink(link);

        return gson.toJson(commonResponse);
    }

    public static String handleResponse(Object obj, Class clazz){
        return gson.toJson(obj, clazz);
    }

    public static String handleNoAuthentication(HttpServletResponse response){
        response.setStatus(403);
        ErrorBO errorBO = new ErrorBO();

        errorBO.setMsg("Authentication Invalid");
        errorBO.setMsg(environment.getProperty("link.doc"));

        return gson.toJson(errorBO, ErrorBO.class);
    }

    public static String handleException(){
        ErrorBO errorBO = new ErrorBO();
        errorBO.setMsg("Internal Error!");
        errorBO.setDoc(environment.getProperty("link.doc"));
        return SerializeUtil.toJson(errorBO, ErrorBO.class);
    }
}
