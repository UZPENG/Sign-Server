package com.uzpeng.sign.web;

import com.uzpeng.sign.web.dto.TeacherDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author serverliu on 2018/4/10.
 */
@Controller
public class TeacherController {
    @RequestMapping(value = "/v1/teacher", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public void addTeacher(TeacherDTO teacherDTO){

    }
}
