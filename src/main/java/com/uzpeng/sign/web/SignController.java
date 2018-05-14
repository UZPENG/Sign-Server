package com.uzpeng.sign.web;

import com.uzpeng.sign.bo.*;
import com.uzpeng.sign.config.StatusConfig;
import com.uzpeng.sign.exception.DuplicateDataException;
import com.uzpeng.sign.exception.NoAuthenticatedException;
import com.uzpeng.sign.service.CourseService;
import com.uzpeng.sign.service.SignService;
import com.uzpeng.sign.service.StudentService;
import com.uzpeng.sign.util.*;
import com.uzpeng.sign.web.dto.CreateSignRecordDTO;
import com.uzpeng.sign.web.dto.SignRecordDTO;
import com.uzpeng.sign.web.dto.StartSignDTO;
import com.uzpeng.sign.web.dto.UpdateSignRecordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author serverliu on 2018/4/10.
 */
@Controller
public class SignController {
    private static final Logger logger = LoggerFactory.getLogger(SignController.class);

    @Autowired
    private SignService signService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private Environment env;

    @RequestMapping(value = "/v1/course/{courseId}/sign", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String createSign(@PathVariable("courseId")String courseId, HttpServletResponse response,
                                          HttpServletRequest request) throws DuplicateDataException, IOException{
        String json = SerializeUtil.readStringFromReader(request.getReader());

        CreateSignRecordDTO createSignRecordDTO = SerializeUtil.fromJson(json, CreateSignRecordDTO.class);

        logger.info("courseTimeId"+createSignRecordDTO.getCourseTimeId());

        signService.createSign(createSignRecordDTO);
        return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                env.getProperty("status.success"), env.getProperty("link.doc"));
    }

    @RequestMapping(value = "/v1/student/sign", method = RequestMethod.PUT, params = { "token"},
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String startSign(HttpServletResponse response, HttpServletRequest request) throws NoAuthenticatedException,IOException {
        String json = SerializeUtil.readStringFromReader(request.getReader());
        SignRecordDTO signRecordDTO = SerializeUtil.fromJson(json, SignRecordDTO.class);
        String openId = signRecordDTO.getOpenId();

        if(openId != null ) {
            Integer studentId = studentService.getStudentByOpenId(openId);

            String token = request.getParameter("token");
            logger.info("token:" + token);

            String storedToken = UserMap.getToken(Integer.parseInt(signRecordDTO.getSignId()));
            logger.info("storedToken:" + storedToken);
            if (token != null && !token.equals("") && token.equals(storedToken)) {
                signService.doingSign(signRecordDTO, studentId);
                return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                        env.getProperty("status.success"), env.getProperty("link.doc"));
            } else {
                throw SpringContextUtil.getBeanByClass(NoAuthenticatedException.class);
            }
        } else {
            throw SpringContextUtil.getBeanByClass(NoAuthenticatedException.class);
        }
    }

    @RequestMapping(value = "/v1/course/{courseId}/sign/{signId}/link", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String signStateControl(@PathVariable("signId")String signIdStr, HttpServletResponse response,
                                   HttpServletRequest request) {
        try {
            logger.info("signStateControl...");
            int signId = Integer.parseInt(signIdStr);
            String json = SerializeUtil.readStringFromReader(request.getReader());

            StartSignDTO startSignDTO = SerializeUtil.fromJson(json, StartSignDTO.class);
            int parameterState = startSignDTO.getStart();
            int storedState = signService.getSignState(signId);

            if((storedState == StatusConfig.SIGN_CREATE_FLAG || storedState == StatusConfig.SIGN_START_FLAG) && parameterState ==
                    StatusConfig.SIGN_START_FLAG) {
                signService.updateSignState(signId, StatusConfig.SIGN_START_FLAG);

                SignWebSocketLinkBO signWebSocketLinkBO = new SignWebSocketLinkBO();
                signWebSocketLinkBO.setLink(env.getProperty("ws.sign.link"));

                return CommonResponseHandler.handleResponse(signWebSocketLinkBO, SignWebSocketLinkBO.class);
            } else if (storedState == StatusConfig.SIGN_START_FLAG && parameterState == StatusConfig.SIGN_FINISH_FLAG){
                signService.updateSignState(signId, StatusConfig.SIGN_FINISH_FLAG);
                signService.evaluateSignResult(signId);
                return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                        env.getProperty("status.success"),  env.getProperty("link.doc"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleException(response);
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

        return CommonResponseHandler.handleException(response);
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
            String amount = request.getParameter("amount");

            SignRecordListBO signRecordListBO = signService.getSignRecordByParam(
                    Integer.parseInt(courseId), Integer.parseInt(timeId), Integer.parseInt(week), num);
            if(amount != null){
                List<SignRecordBO> signRecordList = signRecordListBO.getList();
                List<SignRecordBO> result =  RandomUtil.pickAmountRandomly(signRecordList, Integer.parseInt(amount));

                signRecordListBO.setList(result);
            }
            return CommonResponseHandler.handleResponse(signRecordListBO, SignRecordListBO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleException(response);
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

        return CommonResponseHandler.handleException(response);
    }

    @RequestMapping(value="/v1/course/{courseId}/sign/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response, @PathVariable
            ("courseId") String courseId) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        CourseBO courseBO = courseService.getCourseById(Integer.parseInt(courseId));
        headers.setContentDispositionFormData("attachment", java.net.URLEncoder.encode(
                courseBO.getCourseName()+"签到记录.xlsx", "UTF-8"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        byte[] bytes = signService.downloadSignAllRecord(Integer.parseInt(courseId));
        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }
}
