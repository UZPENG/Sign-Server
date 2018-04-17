package com.uzpeng.sign.web;

import com.uzpeng.sign.config.StatusConfig;
import com.uzpeng.sign.dao.bo.SignRecordTimeListBO;
import com.uzpeng.sign.dao.bo.SignRecordListBO;
import com.uzpeng.sign.service.SignService;
import com.uzpeng.sign.util.CommonResponseHandler;
import com.uzpeng.sign.util.SerializeUtil;
import com.uzpeng.sign.web.dto.CreateSignRecordDTO;
import com.uzpeng.sign.web.dto.UpdateSignRecordDTO;
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

/**
 * @author serverliu on 2018/4/10.
 */
@Controller
public class SignController {
    private static final Logger logger = LoggerFactory.getLogger(SignController.class);

    @Autowired
    private SignService signService;
    @Autowired
    private Environment env;

    @RequestMapping(value = "/v1/course/{courseId}/sign", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String createSign(@PathVariable("courseId")String courseId, HttpServletResponse response,
                                          HttpServletRequest request) {
        try {
            String json = SerializeUtil.readStringFromReader(request.getReader());

            CreateSignRecordDTO createSignRecordDTO = SerializeUtil.fromJson(json, CreateSignRecordDTO.class);

            logger.info("courseTimeId"+createSignRecordDTO.getCourseTimeId());

            signService.createSign(createSignRecordDTO.getCourseTimeId());
            return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                    env.getProperty("status.success"), env.getProperty("link.doc"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleException();
    }

    @RequestMapping(value = "/v1/course/{courseId}/sign", method = RequestMethod.PUT, params = {"time", "token"},
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String startSign(@PathVariable("courseId")String courseId, HttpServletResponse response,
                       HttpServletRequest request,  @PathVariable("timeId") String timeId) {
        try {
            //todo
            return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                    env.getProperty("status.success"), env.getProperty("link.doc"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleException();
    }


    @RequestMapping(value = "/v1/course/{courseId}/sign", method = RequestMethod.GET,
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getSignRecordWeek(@PathVariable("courseId")String courseId, HttpServletResponse response,
                       HttpServletRequest request) {
        try {
            SignRecordTimeListBO signRecordTimeListBO = signService.getSignWeek(Integer.parseInt(courseId));
            return CommonResponseHandler.handleResponse(signRecordTimeListBO, SignRecordTimeListBO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleException();
    }


    @RequestMapping(value = "/v1/course/{courseId}/sign", method = RequestMethod.GET, params = {"time","week"},
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getSignRecordByWeek(@PathVariable("courseId")String courseId,
                                      HttpServletResponse response, HttpServletRequest request) {
        try {
            String timeId = request.getParameter("time");
            String week = request.getParameter("week");
            String num = request.getParameter("num");


            SignRecordListBO signRecordListBO = signService.getSignRecordByParam(
                    Integer.parseInt(courseId), Integer.parseInt(timeId), Integer.parseInt(week), num);
            return CommonResponseHandler.handleResponse(signRecordListBO, SignRecordListBO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleException();
    }

    @RequestMapping(value = "/v1/course/{courseId}/sign/{id}", method = RequestMethod.PUT,
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String updateSignRecord(@PathVariable("id")String signId, HttpServletResponse response,
                                   HttpServletRequest request) {
        try {
            String json = SerializeUtil.readStringFromReader(request.getReader());
            UpdateSignRecordDTO updateSignRecordDTO = SerializeUtil.fromJson(json, UpdateSignRecordDTO.class);

            updateSignRecordDTO.setId(Integer.parseInt(signId));

            logger.info("sign record id is "+updateSignRecordDTO.getId()+", state is "+updateSignRecordDTO.getState());

            signService.updateSignState(updateSignRecordDTO);
            return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                    env.getProperty("status.success"),  env.getProperty("link.doc"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleException();
    }
}
