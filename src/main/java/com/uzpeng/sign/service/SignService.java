package com.uzpeng.sign.service;

import com.uzpeng.sign.dao.SignDAO;
import com.uzpeng.sign.dao.bo.SignRecordTimeListBO;
import com.uzpeng.sign.dao.bo.SignRecordListBO;
import com.uzpeng.sign.web.dto.UpdateSignRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author uzpeng on 2018/4/16.
 */
@Service
public class SignService {
    @Autowired
    private SignDAO signDAO;

    public void createSign(Integer courseTimeId){
        signDAO.createSign(courseTimeId);
    }

    public SignRecordListBO getSignRecordByParam(Integer courseId, Integer time, Integer week, String num){
        return signDAO.getSignRecordByTime(courseId, time, week, num);
    }

    public SignRecordTimeListBO getSignWeek(Integer courseId){
        return signDAO.getRecordWeek(courseId);
    }

    public void updateSignState(UpdateSignRecordDTO updateSignRecordDTO){
        signDAO.UpdateSignRecordStatus(updateSignRecordDTO);
    }
}
