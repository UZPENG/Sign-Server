package com.uzpeng.sign.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uzpeng.sign.domain.CommonResponse;

/**
 * @author serverliu on 2018/4/3.
 */
public class CommonResponseHandler {
    private static final  Gson gson = new GsonBuilder().create();;

    public static String handleResponse(String msg, String link){

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setMsg(msg);
        commonResponse.setLink(link);

        return gson.toJson(commonResponse);
    }

    public static String handleResponse(Object obj, Class clazz){
        return gson.toJson(obj, clazz);
    }
}
