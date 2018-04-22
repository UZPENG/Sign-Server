package com.uzpeng.sign.bo;

/**
 * @author uzpeng on 2018/4/16.
 */


public class ResponseBO<T>  {
    private String status;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
