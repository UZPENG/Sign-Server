package com.uzpeng.sign.web;

import com.uzpeng.sign.service.SemesterService;
import com.uzpeng.sign.util.SerializeUtil;
import com.uzpeng.sign.web.dto.SemesterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
}
