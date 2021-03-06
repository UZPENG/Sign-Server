package com.uzpeng.sign.service;

import com.uzpeng.sign.dao.SemesterDAO;
import com.uzpeng.sign.bo.SemesterBO;
import com.uzpeng.sign.bo.SemesterBOList;
import com.uzpeng.sign.util.ObjectTranslateUtil;
import com.uzpeng.sign.web.dto.SemesterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author serverliu on 2018/4/10.
 */
@Service
public class SemesterService {
    private static final Logger logger = LoggerFactory.getLogger(SemesterService.class);

    @Autowired
    private SemesterDAO semesterDAO;

    public void addSemester(SemesterDTO semesterDTO, Integer teacherId){
        logger.info("Add semester, Parameter id:"+semesterDTO.getSemester()+", startTime:"+semesterDTO.getSemester()+
                ",endTime:"+semesterDTO.getEndYear());

        semesterDAO.addSemester(ObjectTranslateUtil.semesterDTOToSemesterDO(semesterDTO, teacherId));
    }

    public SemesterBO getSemesterById(Integer semesterId, Integer teacherId){
        return semesterDAO.getSemesterById(semesterId, teacherId);
    }

    public SemesterBOList getSemester(Integer teacherId){
        return semesterDAO.getSemester(teacherId);
    }

    public void updateSemester(SemesterDTO semesterDTO, Integer teacherId){
        semesterDAO.updateSemester(ObjectTranslateUtil.semesterDTOToSemesterDO(semesterDTO, teacherId));
    }

    public void deleteSemester(Integer semesterId){
        semesterDAO.deleteSemester(semesterId);
    }
}
