package com.uzpeng.sign.web;

import com.uzpeng.sign.config.StatusConfig;
import com.uzpeng.sign.bo.SignWebSocketLinkBO;
import com.uzpeng.sign.bo.SignRecordBO;
import com.uzpeng.sign.bo.SignRecordListBO;
import com.uzpeng.sign.bo.SignRecordTimeListBO;
import com.uzpeng.sign.exception.DuplicateDataException;
import com.uzpeng.sign.service.SignService;
import com.uzpeng.sign.util.CommonResponseHandler;
import com.uzpeng.sign.util.RandomUtil;
import com.uzpeng.sign.util.SerializeUtil;
import com.uzpeng.sign.web.dto.CreateSignRecordDTO;
import com.uzpeng.sign.web.dto.StartSignDTO;
import com.uzpeng.sign.web.dto.UpdateSignRecordDTO;
import org.apache.logging.log4j.core.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private Environment env;

    @RequestMapping(value = "/v1/course/{courseId}/sign", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String createSign(@PathVariable("courseId")String courseId, HttpServletResponse response,
                                          HttpServletRequest request) throws DuplicateDataException, IOException{
        try {
            String json = SerializeUtil.readStringFromReader(request.getReader());

            CreateSignRecordDTO createSignRecordDTO = SerializeUtil.fromJson(json, CreateSignRecordDTO.class);

            logger.info("courseTimeId"+createSignRecordDTO.getCourseTimeId());

            signService.createSign(createSignRecordDTO);
            return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                    env.getProperty("status.success"), env.getProperty("link.doc"));
        } catch (DuplicateDataException | IOException e) {
           throw e;
        }
    }

    @RequestMapping(value = "/v1/course/{courseId}/sign/{signId}", method = RequestMethod.PUT, params = { "token"},
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String startSign(@PathVariable("courseId")String courseId, HttpServletResponse response,
                       HttpServletRequest request) {
        try {
            //todo
            return CommonResponseHandler.handleResponse(StatusConfig.SUCCESS,
                    env.getProperty("status.success"), env.getProperty("link.doc"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponseHandler.handleException(response);
    }

    @RequestMapping(value = "/v1/course/{courseId}/sign/{signId}/link", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getSignLink(@PathVariable("signId")String signIdStr, HttpServletResponse response,
                            HttpServletRequest request) {
        try {
            int signId = Integer.parseInt(signIdStr);
            String json = SerializeUtil.readStringFromReader(request.getReader());

            StartSignDTO startSignDTO = SerializeUtil.fromJson(json, StartSignDTO.class);
            int parameterState = startSignDTO.getStart();
            int storedState = signService.getSignState(signId);

            if((storedState == StatusConfig.CREATE || storedState == StatusConfig.START) && parameterState ==
                    StatusConfig.START) {
                signService.updateSignState(signId, StatusConfig.START);

                SignWebSocketLinkBO signWebSocketLinkBO = new SignWebSocketLinkBO();
                signWebSocketLinkBO.setLink(env.getProperty("ws.sign.link"));

                return CommonResponseHandler.handleResponse(signWebSocketLinkBO, SignWebSocketLinkBO.class);
            } else if (storedState == StatusConfig.START && parameterState == StatusConfig.FINISH){
                signService.updateSignState(signId, StatusConfig.FINISH);

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
        headers.setContentDispositionFormData("attachment", "test.xlsx");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        byte[] bytes = signService.downloadSignAllRecord(Integer.parseInt(courseId));
        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }
}
