package com.uzpeng.sign.web;

import com.uzpeng.sign.service.StudentService;
import com.uzpeng.sign.util.SerializeUtil;
import com.uzpeng.sign.web.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * @author serverliu on 2018/4/7.
 */
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/v1/student", method = RequestMethod.POST,params = {"file", "courseId"},
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String insertStudentList(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        boolean status = false;
        try{
            Integer courseId = Integer.parseInt(request.getParameter("courseId"));
            InputStream studentList = file.getInputStream();
            status = studentService.insertStudentsByFile(studentList, file.getOriginalFilename(), courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(status) {
            return "{}";
        } else {
            return "{}";
        }
    }

    @RequestMapping(value = "/v1/student", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String insertStudent(HttpServletRequest request){
        try{
            String json = SerializeUtil.readStringFromReader(request.getReader());
            StudentDTO studentDTO = SerializeUtil.fromJson(json, StudentDTO.class);

            studentService.insertStudent(studentDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "{}";
    }
}
