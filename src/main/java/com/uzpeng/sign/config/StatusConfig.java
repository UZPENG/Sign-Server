package com.uzpeng.sign.config;

/**
 * @author serverliu on 2018/4/10.
 */
public interface StatusConfig {
    String SUCCESS = "success";
    String FAILED = "failed";

    Integer SIGN_CREATE_FLAG = -1;
    Integer SIGN_START_FLAG = 1;
    Integer SIGN_FINISH_FLAG = 0;

    Integer RECORD_CREATED = 0;
    Integer RECORD_SIGNED = 1;
    Integer RECORD_FAILED = 2;
    Integer RECORD_SUCCESS = 3;

    Integer HISTORY = 0;
    Integer TODAY = 1;
    Integer ALL = 2;
}
