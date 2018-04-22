package com.uzpeng.sign.web;

import com.uzpeng.sign.config.StatusConfig;
import com.uzpeng.sign.bo.StudentBOList;
import com.uzpeng.sign.domain.RoleDO;
import com.uzpeng.sign.domain.UserDO;
import com.uzpeng.sign.exception.NoAuthenticatedException;
import com.uzpeng.sign.service.StudentService;
import com.uzpeng.sign.support.SessionAttribute;
import com.uzpeng.sign.util.*;
import com.uzpeng.sign.web.dto.StudentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;

/**
 * @author serverliu on 2018/4/7.
 */
@Controller
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private Environment env;

    @Autowired
    private StudentService studentService;

    @CrossOrigin(allowedHeaders = "withCredentials")
    @RequestMapping(value = "/v1/course/{courseId}/student", method = RequestMethod.POST,consumes = "multipart/form-data",
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String insertStudentList(@RequestParam("file") MultipartFile file, @PathVariable("courseId") String id,
                                    HttpSession session, HttpServletResponse response){
        logger.info("start upload file "+file.getOriginalFilename());

        SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
        UserDO role = UserMap.getUser((String)auth.getObj());
        if(role != null && role.getRole().equals(Role.TEACHER)) {
            try {
                Integer courseId = Integer.parseInt(id);
                InputStream studentList = file.getInputStream();
                studentService.insertStudentsByFile(studentList, file.getOriginalFilename(), courseId);
                return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                        env.getProperty("status.success"),  env.getProperty("link.doc"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return CommonResponseHandler.handleException(response);
        } else {
            return CommonResponseHandler.handleNoAuthentication(response);
        }
    }

    @RequestMapping(value = "/v1/course/{courseId}/student", method = RequestMethod.POST
            , produces = "application/json;charset=utf-8")
    @ResponseBody
    public String insertStudent(HttpServletRequest request,  HttpSession session, HttpServletResponse response,
                                @PathVariable("courseId") String id) throws NoAuthenticatedException{
        SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
        UserDO role = UserMap.getUser((String)auth.getObj());
        if(role != null && role.getRole().equals(Role.TEACHER)) {
            try {
                String json = SerializeUtil.readStringFromReader(request.getReader());
                StudentDTO studentDTO = SerializeUtil.fromJson(json, StudentDTO.class);

                logger.info("start add student "+json);
                studentService.insertStudent(studentDTO, Integer.parseInt(id));

                return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                        env.getProperty("status.success"),  env.getProperty("link.doc"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return CommonResponseHandler.handleResponse(StatusConfig.FAILED,
                    env.getProperty("status.failed"),  env.getProperty("link.doc"));
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
            String num = request.getParameter("num");
            String amountStr = request.getParameter("amount");

            if(amountStr != null){
                int amount = Integer.parseInt(amountStr);
                StudentBOList studentBOList = studentService.pickStudent(Integer.parseInt(courseId), amount);

                return  CommonResponseHandler.handleResponse(studentBOList, StudentBOList.class);
            } else if(num != null){
                StudentBOList studentBOList = studentService.searchStudentByNum(num);
                return  CommonResponseHandler.handleResponse(studentBOList, StudentBOList.class);
            } else {
                StudentBOList studentBOS = studentService.getStudentByCourseId(courseId);

                return CommonResponseHandler.handleResponse(studentBOS, StudentBOList.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleException(response);
    }

    @RequestMapping(value = "/v1/course/{courseId}/student/{studentId}", method = RequestMethod.DELETE,
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String deleteStudentByCourseId(@PathVariable("courseId")String courseId, HttpServletResponse response,
                                          HttpServletRequest request, @PathVariable("studentId") String studentId) {
        try {
            studentService.removeStudent(courseId, studentId);
            return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                    env.getProperty("status.success"), env.getProperty("link.doc"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleNoAuthentication(response);
    }
}
