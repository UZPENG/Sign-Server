package com.uzpeng.sign.web;

import com.uzpeng.sign.config.StatusConfig;
import com.uzpeng.sign.dao.bo.StudentBO;
import com.uzpeng.sign.dao.bo.StudentBOList;
import com.uzpeng.sign.domain.RoleDO;
import com.uzpeng.sign.exception.NoAuthenticatedException;
import com.uzpeng.sign.support.SessionAttribute;
import com.uzpeng.sign.service.StudentService;
import com.uzpeng.sign.util.*;
import com.uzpeng.sign.web.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.List;

/**
 * @author serverliu on 2018/4/7.
 */
@Controller
public class StudentController {
    @Autowired
    private Environment env;

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/v1/course/{courseId}/student", method = RequestMethod.POST,consumes = "multipart/form-data",
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String insertStudentList(@RequestParam("file") MultipartFile file, @PathVariable("courseId") String id,
                                    HttpSession session, HttpServletResponse response){
        SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
        RoleDO role = UserMap.getId((String)auth.getObj());
        if(role != null && role.getRole().equals(Role.TEACHER)) {
            try {
                Integer courseId = Integer.parseInt(id);
                InputStream studentList = file.getInputStream();
                studentService.insertStudentsByFile(studentList, file.getOriginalFilename(), courseId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                    env.getProperty("msg.register.verified"),  env.getProperty("link.doc"));
        } else {
            return CommonResponseHandler.handleNoAuthentication(response);
        }
    }

    @RequestMapping(value = "/v1/course/{courseId}/student", method = RequestMethod.POST,
            consumes = "application/json;charset=utf-8", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String insertStudent(HttpServletRequest request,  HttpSession session, HttpServletResponse response,
                                @PathVariable("courseId") String id) throws NoAuthenticatedException{
        SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
        RoleDO role = UserMap.getId((String)auth.getObj());
        if(role != null && role.getRole().equals(Role.TEACHER)) {
            try {
                String json = SerializeUtil.readStringFromReader(request.getReader());
                StudentDTO studentDTO = SerializeUtil.fromJson(json, StudentDTO.class);

                studentService.insertStudent(studentDTO, Integer.parseInt(id));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                    env.getProperty("msg.register.verified"),  env.getProperty("link.doc"));
        } else {
            return CommonResponseHandler.handleNoAuthentication(response);
        }
    }

    @RequestMapping(value = "/v1/course/{courseId}/student", method = RequestMethod.GET,
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getStudentByCourseId(@PathVariable("courseId")String courseId, HttpServletRequest request,
                                       HttpServletResponse response){
        try {
           StudentBOList studentBOS = studentService.getStudentByCourseId(courseId);

           return SerializeUtil.toJson(studentBOS, StudentBOList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleException();
    }

    @RequestMapping(value = "/v1/course/{courseId}/student/{studentId}", method = RequestMethod.DELETE,
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String deleteStudentByCourseId(@PathVariable("courseId")String courseId, HttpServletResponse response,
                                          HttpServletRequest request, @PathVariable("studentId") String studentId) {
        try {
            studentService.removeStudent(courseId, studentId);
            return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                    env.getProperty("msg.register.verified"), env.getProperty("link.doc"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleNoAuthentication(response);
    }
}
