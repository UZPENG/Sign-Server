package com.uzpeng.sign.service;

import com.uzpeng.sign.dao.bo.SemesterBO;
import com.uzpeng.sign.dao.SemesterDAO;
import com.uzpeng.sign.dao.bo.SemesterBOList;
import com.uzpeng.sign.util.ObjectTranslateUtil;
import com.uzpeng.sign.web.dto.SemesterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author serverliu on 2018/4/10.
 */
@Service
public class SemesterService {
    private static final Logger logger = LoggerFactory.getLogger(SemesterService.class);

    @Autowired
    private SemesterDAO semesterDAO;

    public void addSemester(SemesterDTO semesterDTO){
        logger.info("Add semester, Parameter id:"+semesterDTO.getSemester()+", startTime:"+semesterDTO.getSemester()+
                ",endTime:"+semesterDTO.getEndYear());

        semesterDAO.addSemester(ObjectTranslateUtil.semesterDTOToSemesterDO(semesterDTO));
    }

    public SemesterBO getSemesterById(Integer semesterId){
        return semesterDAO.getSemesterById(semesterId);
    }


    public SemesterBOList getSemester(){
        return semesterDAO.getSemester();
    }

    public void updateSemester(SemesterDTO semesterDTO){
        semesterDAO.updateSemester(ObjectTranslateUtil.semesterDTOToSemesterDO(semesterDTO));
    }

    public void deleteSemester(Integer semesterId){
        semesterDAO.deleteSemester(semesterId);
    }
}
