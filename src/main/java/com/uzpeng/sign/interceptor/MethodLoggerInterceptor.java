package com.uzpeng.sign.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author uzpeng on 2018/4/20.
 */
public class MethodLoggerInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //todo 拦截所有方法  打印传入参数和返回值
        return null;
    }
}
