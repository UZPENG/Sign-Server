package com.uzpeng.sign.web;

import com.uzpeng.sign.dao.bo.SemesterBO;
import com.uzpeng.sign.dao.bo.SemesterBOList;
import com.uzpeng.sign.service.SemesterService;
import com.uzpeng.sign.util.CommonResponseHandler;
import com.uzpeng.sign.util.SerializeUtil;
import com.uzpeng.sign.web.dto.SemesterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * @author serverliu on 2018/4/10.
 */
@Controller
public class SemesterController {
    private static final Logger logger = LoggerFactory.getLogger(SemesterController.class);
    @Autowired
    private SemesterService semesterService;

    @RequestMapping(value = "/v1/semester", method = RequestMethod.POST,
            consumes = "application/json", produces="application/json;charset=utf-8")
    @ResponseBody
    public String addSemester(HttpServletRequest request, HttpServletResponse response){
        try {
            BufferedReader bodyReader = request.getReader();
            String str = SerializeUtil.readStringFromReader(bodyReader);
            logger.info("Parameter is "+str);

            SemesterDTO semesterDTO = SerializeUtil.fromJson(str, SemesterDTO.class);
            semesterService.addSemester(semesterDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //todo 插入学期返回结果
        return "{}";
    }

    @RequestMapping(value = "/v1/semester", method = RequestMethod.GET,
            produces="application/json;charset=utf-8")
    @ResponseBody
    public String getSemester(HttpServletRequest request, HttpServletResponse response){
        try {
            SemesterBOList semesterBOList = semesterService.getSemester();

            return SerializeUtil.toJson(semesterBOList, SemesterBOList.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/v1/semester/{id}", method = RequestMethod.GET,
            produces="application/json;charset=utf-8")
    @ResponseBody
    public String getSemesterById(@PathVariable("id")String id, HttpServletRequest request, HttpServletResponse response){
        try {
            SemesterBO semesterBO = semesterService.getSemesterById(Integer.parseInt(id));

            return SerializeUtil.toJson(semesterBO, SemesterBO.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/v1/semester/{id}", method = RequestMethod.PUT,
            produces="application/json;charset=utf-8")
    @ResponseBody
    public String updateSemesterById(@PathVariable("id")String id, HttpServletRequest request, HttpServletResponse response){
        try {
            BufferedReader bodyReader = request.getReader();
            String str = SerializeUtil.readStringFromReader(bodyReader);
            logger.info("Parameter is "+str);

            SemesterDTO semesterDTO = SerializeUtil.fromJson(str, SemesterDTO.class);
            semesterService.updateSemester(semesterDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/v1/semester/{id}", method = RequestMethod.DELETE,
            produces="application/json;charset=utf-8")
    @ResponseBody
    public String deleteSemesterById(@PathVariable("id")String id, HttpServletRequest request, HttpServletResponse response){
        try {
            semesterService.deleteSemester(Integer.parseInt(id));

            return "{}";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
