package com.uzpeng.sign.web;

import com.uzpeng.sign.domain.RoleDO;
import com.uzpeng.sign.domain.SessionAttribute;
import com.uzpeng.sign.service.CourseService;
import com.uzpeng.sign.util.Role;
import com.uzpeng.sign.util.SerializeUtil;
import com.uzpeng.sign.util.SessionStoreKey;
import com.uzpeng.sign.util.UserMap;
import com.uzpeng.sign.web.dto.CourseDTO;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value = "/v1/course", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String addCourse(HttpServletRequest request, HttpSession session){
        //todo 添加返回信息
        try {
            SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
            RoleDO role = UserMap.getId((String)auth.getObj());
            if(role != null && role.getRole().equals(Role.TEACHER)) {
                BufferedReader reader = request.getReader();
                String json = SerializeUtil.readStringFromReader(reader);
                CourseDTO courseDTO = SerializeUtil.fromJson(json, CourseDTO.class);

                courseDTO.setTeacherId(role.getRoleId());
                courseService.addCourse(courseDTO);
                return "{}";
            } else {
                //todo 非法操作处理
                return "{not teacher!}";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/v1/course", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getCourse(HttpServletRequest request, HttpSession session){
        //todo 添加返回信息
        try {
            SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
            RoleDO role = UserMap.getId((String)auth.getObj());
            if(role != null && role.getRole().equals(Role.TEACHER)) {
                BufferedReader reader = request.getReader();
                String json = SerializeUtil.readStringFromReader(reader);
                CourseDTO courseDTO = SerializeUtil.fromJson(json, CourseDTO.class);

                courseDTO.setTeacherId(role.getRoleId());
                courseService.addCourse(courseDTO);
                return "{}";
            } else {
                //todo 非法操作处理
                return "{not teacher!}";
            }
        } catch (IOException e) {
            e.printStackTrace();
            //todo 异常处理
            return "{exception!}";
        }
    }

}
