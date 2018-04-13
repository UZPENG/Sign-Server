package com.uzpeng.sign.service;

import com.uzpeng.sign.dao.SemesterDAO;
import com.uzpeng.sign.domain.SemesterDO;
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

    public void addSemester(SemesterDTO semesterDTO){
        logger.info("Add semester, Parameter id:"+semesterDTO.getSemester()+", startTime:"+semesterDTO.getSemester()+
                ",endTime:"+semesterDTO.getEndYear());

        semesterDAO.addSemester(ObjectTranslateUtil.semesterDTOToSemesterDO(semesterDTO));
    }

}
