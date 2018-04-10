package com.uzpeng.sign.web;

import com.sun.deploy.net.HttpResponse;
import com.uzpeng.sign.domain.SemesterDO;
import com.uzpeng.sign.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author serverliu on 2018/4/10.
 */
@Controller
public class SemesterController {
    @Autowired
    private SemesterService semesterService;

    @RequestMapping(value = "/v1/semester", method = POST, produces="application/json;charset=utf-8")
    @ResponseBody
    public String addSemester(SemesterDO semesterDO, HttpServletResponse response){
        semesterService.addSemester(semesterDO);

        //todo 插入学期返回结果
        return "{}";
    }
}
