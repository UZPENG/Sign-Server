package com.uzpeng.sign.web;

import com.uzpeng.sign.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author serverliu on 2018/4/7.
 */
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/v1/student", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String importStudentList(@RequestParam("file") MultipartFile file){
        boolean status = false;
        try{
            InputStream studentList = file.getInputStream();
            status = studentService.insertStudentsByFile(studentList, file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(status) {
            return "{}";
        } else {
            return "{}";
        }
    }
}
