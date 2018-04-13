package com.uzpeng.sign.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author serverliu on 2018/4/11.
 */
public class SerializeUtil {
    private static final Gson gson= new GsonBuilder()
            .serializeNulls()
            .create();

    public static <T> T fromJson(Reader reader, Class<T> clazz){
        return gson.fromJson(reader, clazz);
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz){
        return gson.fromJson(jsonString, clazz);
    }

    public static String readStringFromReader(Reader reader){
        BufferedReader bufReader = new BufferedReader(reader);
        try {
            StringBuilder result = new StringBuilder();

            String line = bufReader.readLine();
            while(line != null) {
                result.append(line);
                line = bufReader.readLine();
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
