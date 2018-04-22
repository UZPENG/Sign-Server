package com.uzpeng.sign.web;

import com.uzpeng.sign.config.StatusConfig;
import com.uzpeng.sign.bo.SemesterBO;
import com.uzpeng.sign.bo.SemesterBOList;
import com.uzpeng.sign.domain.UserDO;
import com.uzpeng.sign.service.SemesterService;
import com.uzpeng.sign.support.SessionAttribute;
import com.uzpeng.sign.util.CommonResponseHandler;
import com.uzpeng.sign.util.SerializeUtil;
import com.uzpeng.sign.util.SessionStoreKey;
import com.uzpeng.sign.util.UserMap;
import com.uzpeng.sign.web.dto.SemesterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author serverliu on 2018/4/10.
 */
@Controller
public class SemesterController {
    private static final Logger logger = LoggerFactory.getLogger(SemesterController.class);
    @Autowired
    private SemesterService semesterService;
    @Autowired
    private Environment env;


    @RequestMapping(value = "/v1/semester", method = RequestMethod.POST,
            produces="application/json;charset=utf-8")
    @ResponseBody
    public String addSemester(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        try {
            SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
            UserDO role = UserMap.getUser((String)auth.getObj());

            BufferedReader bodyReader = request.getReader();
            String str = SerializeUtil.readStringFromReader(bodyReader);
            logger.info("Parameter is "+str);

            SemesterDTO semesterDTO = SerializeUtil.fromJson(str, SemesterDTO.class);
            semesterService.addSemester(semesterDTO, role.getRoleId());

            return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                    env.getProperty("status.success"),  env.getProperty("link.doc"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleResponse(StatusConfig.FAILED,
                env.getProperty("status.failed"),  env.getProperty("link.doc"));
    }

    @RequestMapping(value = "/v1/semester", method = RequestMethod.GET,
            produces="application/json;charset=utf-8")
    @ResponseBody
    public String getSemester(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        try {
            SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
            UserDO role = UserMap.getUser((String)auth.getObj());

            SemesterBOList semesterBOList = semesterService.getSemester(role.getRoleId());

            return CommonResponseHandler.handleResponse(semesterBOList, SemesterBOList.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleResponse(StatusConfig.FAILED,
                env.getProperty("status.failed"),  env.getProperty("link.doc"));
    }

    @RequestMapping(value = "/v1/semester/{id}", method = RequestMethod.GET,
            produces="application/json;charset=utf-8")
    @ResponseBody
    public String getSemesterById(@PathVariable("id")String id, HttpServletRequest request, HttpServletResponse response,
                                  HttpSession session){
        try {
            SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
            UserDO role = UserMap.getUser((String)auth.getObj());

            SemesterBO semesterBO = semesterService.getSemesterById(Integer.parseInt(id), role.getRoleId());

            return CommonResponseHandler.handleResponse(semesterBO, SemesterBO.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleResponse(StatusConfig.FAILED,
                env.getProperty("status.failed"),  env.getProperty("link.doc"));
    }

    @RequestMapping(value = "/v1/semester/{id}", method = RequestMethod.PUT,
            produces="application/json;charset=utf-8")
    @ResponseBody
    public String updateSemesterById(@PathVariable("id")String id, HttpServletRequest request,
                                     HttpServletResponse response, HttpSession session){
        try {
            SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
            UserDO role = UserMap.getUser((String)auth.getObj());

            BufferedReader bodyReader = request.getReader();
            String str = SerializeUtil.readStringFromReader(bodyReader);
            logger.info("Parameter is "+str);

            SemesterDTO semesterDTO = SerializeUtil.fromJson(str, SemesterDTO.class);
            semesterDTO.setId(Integer.parseInt(id));
            semesterService.updateSemester(semesterDTO, role.getRoleId());

            return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                    env.getProperty("status.success"),  env.getProperty("link.doc"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleResponse(StatusConfig.FAILED,
                env.getProperty("status.failed"),  env.getProperty("link.doc"));
    }

    @RequestMapping(value = "/v1/semester/{id}", method = RequestMethod.DELETE,
            produces="application/json;charset=utf-8")
    @ResponseBody
    public String deleteSemesterById(@PathVariable("id")String id, HttpServletRequest request,
                                     HttpServletResponse response, HttpSession session){
        try {
            SessionAttribute auth = (SessionAttribute) session.getAttribute(SessionStoreKey.KEY_AUTH);
            UserDO role = UserMap.getUser((String)auth.getObj());

            semesterService.deleteSemester(Integer.parseInt(id));

            return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                    env.getProperty("status.success"),  env.getProperty("link.doc"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResponseHandler.handleResponse(StatusConfig.FAILED,
                env.getProperty("status.failed"),  env.getProperty("link.doc"));
    }
}
