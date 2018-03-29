package com.uzpeng.sign.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author serverliu on 2018/3/29.
 */
@Controller
public class TestController {
    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(){
        return "hello world";
    }
}
