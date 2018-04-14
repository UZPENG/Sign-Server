package com.uzpeng.sign.web;

import com.uzpeng.sign.config.StatusConfig;
import com.uzpeng.sign.dao.bo.CourseListBO;
import com.uzpeng.sign.domain.RoleDO;
import com.uzpeng.sign.support.SessionAttribute;
import com.uzpeng.sign.service.CourseService;
import com.uzpeng.sign.util.*;
import com.uzpeng.sign.web.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

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
                BufferedReader reader = request.getReader();

                CourseListBO courseVO = courseService.getCourse(role.getRoleId());
                return SerializeUtil.toJson(courseVO, CourseListBO.class);
            } else {
                return CommonResponseHandler.handleNoAuthentication(response);
            }
        } catch (IOException e) {
            e.printStackTrace();

            return CommonResponseHandler.handleException();
        }
    }

}
