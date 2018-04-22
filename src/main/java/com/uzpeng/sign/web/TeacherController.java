package com.uzpeng.sign.web;

import com.uzpeng.sign.config.StatusConfig;
import com.uzpeng.sign.domain.TeacherDO;
import com.uzpeng.sign.service.TeacherService;
import com.uzpeng.sign.util.CommonResponseHandler;
import com.uzpeng.sign.util.SerializeUtil;
import com.uzpeng.sign.web.dto.CourseDTO;
import com.uzpeng.sign.web.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

/**
 * @author serverliu on 2018/4/10.
 */
@Controller
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private Environment env;

    @RequestMapping(value = "/v1/teacher", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String addTeacher(HttpServletRequest request, HttpServletResponse response){

        try {
            Reader reader = request.getReader();
            String json = SerializeUtil.readStringFromReader(reader);
            TeacherDTO teacherDTO = SerializeUtil.fromJson(json, TeacherDTO.class);

            teacherService.addTeacher(teacherDTO);

            return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                    env.getProperty("status.success"),  env.getProperty("link.doc"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResponseHandler.handleException(response);
    }

    @RequestMapping(value = "/v1/teacher", method = RequestMethod.PUT,
            produces = "application/json;charset=utf-8")
    public void updateTeacher( HttpServletRequest request){

    }
}
