package com.uzpeng.sign.web;

import com.uzpeng.sign.config.StatusConfig;
import com.uzpeng.sign.dao.bo.CourseBO;
import com.uzpeng.sign.dao.bo.CourseListBO;
import com.uzpeng.sign.dao.bo.CourseTimeListBO;
import com.uzpeng.sign.dao.bo.StudentBOList;
import com.uzpeng.sign.domain.RoleDO;
import com.uzpeng.sign.exception.CommonExceptionHandler;
import com.uzpeng.sign.support.SessionAttribute;
import com.uzpeng.sign.service.CourseService;
import com.uzpeng.sign.util.*;
import com.uzpeng.sign.web.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Base64;

/**
 * @author serverliu on 2018/4/11.
 */
@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/v1/course", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String addCourse(HttpServletRequest request, HttpSession session, HttpServletResponse response){
        try {
            SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
            RoleDO role = UserMap.getId((String)auth.getObj());
            if(role != null && role.getRole().equals(Role.TEACHER)) {
                BufferedReader reader = request.getReader();
                String json = SerializeUtil.readStringFromReader(reader);
                CourseDTO courseDTO = SerializeUtil.fromJson(json, CourseDTO.class);

                courseDTO.setTeacherId(role.getRoleId());
                courseService.addCourse(courseDTO);
                return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                        env.getProperty("msg.success"),  env.getProperty("link.doc"));
            } else {
                return CommonResponseHandler.handleNoAuthentication(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResponseHandler.handleException();
        }
    }

    @RequestMapping(value = "/v1/course", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getCourse(HttpServletRequest request, HttpSession session, HttpServletResponse response){
        try {
            SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
            RoleDO role = UserMap.getId((String)auth.getObj());

            if(role != null && role.getRole().equals(Role.TEACHER)) {
                String courseName = request.getParameter("name");

                if(courseName != null){
                    String decodeCourseName = URLDecoder.decode(courseName, "utf-8");
                    CourseListBO courseListBO = courseService.getCourseByName(decodeCourseName);
                    return CommonResponseHandler.handleResponse(courseListBO, CourseListBO.class);
                }else {
                    BufferedReader reader = request.getReader();

                    CourseListBO courseListBO = courseService.getCourse(role.getRoleId());
                    return CommonResponseHandler.handleResponse(courseListBO, CourseListBO.class);
                }
            } else {
                return CommonResponseHandler.handleNoAuthentication(response);
            }
        } catch (IOException e) {
            e.printStackTrace();

            return CommonResponseHandler.handleException();
        }
    }

    @RequestMapping(value = "/v1/course/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getCourseById(@PathVariable("id")String id, HttpServletRequest request, HttpSession session,
                                HttpServletResponse response){
        try {
            SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
            RoleDO role = UserMap.getId((String)auth.getObj());
            if(role != null && role.getRole().equals(Role.TEACHER)) {
                BufferedReader reader = request.getReader();

                Integer courseId = Integer.parseInt(id);
                CourseBO courseBO = courseService.getCourseById(courseId);
                return CommonResponseHandler.handleResponse(courseBO, CourseBO.class);
            } else {
                return CommonResponseHandler.handleNoAuthentication(response);
            }
        } catch (IOException e) {
            e.printStackTrace();

            return CommonResponseHandler.handleException();
        }
    }

    @RequestMapping(value = "/v1/course/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String deleteCourseById(@PathVariable("id")String id, HttpServletRequest request, HttpSession session,
                                HttpServletResponse response){
        try {
            SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
            RoleDO role = UserMap.getId((String)auth.getObj());
            if(role != null && role.getRole().equals(Role.TEACHER)) {
                courseService.deleteCourseById(Integer.parseInt(id));
                return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                        env.getProperty("status.success"),  env.getProperty("link.login"));
            } else {
                return CommonResponseHandler.handleNoAuthentication(response);
            }
        } catch (Exception e) {
            e.printStackTrace();

            return CommonResponseHandler.handleException();
        }
    }

    @RequestMapping(value = "/v1/course/{id}", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String updateCourseById(@PathVariable("id")String id, HttpServletRequest request, HttpSession session,
                                   HttpServletResponse response){
        try {
            SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
            RoleDO role = UserMap.getId((String)auth.getObj());
            if(role != null && role.getRole().equals(Role.TEACHER)) {
                BufferedReader reader = request.getReader();
                String json = SerializeUtil.readStringFromReader(reader);
                CourseDTO courseDTO = SerializeUtil.fromJson(json, CourseDTO.class);

                courseDTO.setCourseId(id);
                courseDTO.setTeacherId(role.getRoleId());
                courseService.updateCourse(courseDTO);
                return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                        env.getProperty("msg.success"),  env.getProperty("link.doc"));
            } else {
                return CommonResponseHandler.handleNoAuthentication(response);
            }
        } catch (Exception e) {
            e.printStackTrace();

            response.setStatus(500);
            return CommonResponseHandler.handleException();
        }
    }


    @RequestMapping(value = "/v1/course/{id}/time", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getCourseTimeById(@PathVariable("id")String id, HttpServletRequest request, HttpSession session,
                                HttpServletResponse response){
        try {
            SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
            RoleDO role = UserMap.getId((String)auth.getObj());
            if(role != null && role.getRole().equals(Role.TEACHER)) {
                Integer courseId = Integer.parseInt(id);
                CourseTimeListBO courseTimeListBO = courseService.getCourTimeById(courseId);
                return CommonResponseHandler.handleResponse(courseTimeListBO, CourseTimeListBO.class);
            } else {
                return CommonResponseHandler.handleNoAuthentication(response);
            }
        } catch (Exception e) {
            e.printStackTrace();

            return CommonResponseHandler.handleException();
        }
    }
}
