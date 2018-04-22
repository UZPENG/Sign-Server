package com.uzpeng.sign.config;

/**
 * @author serverliu on 2018/4/10.
 */
public interface StatusConfig {
    String SUCCESS = "success";
    String FAILED = "failed";

    Integer CREATE = -1;
    Integer START = 1;
    Integer FINISH = 0;

    Integer HISTORY = 0;
    Integer TODAY = 1;
    Integer ALL = 2;
}
