package com.uzpeng.sign.dao;

import com.uzpeng.sign.dao.bo.SemesterBO;
import com.uzpeng.sign.domain.SemesterDO;
import com.uzpeng.sign.persistence.SemesterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.format.DateTimeFormatter;

/**
 * @author serverliu on 2018/4/10.
 */
@Repository
public class SemesterDAO {
    private static final Logger logger = LoggerFactory.getLogger(SemesterDAO.class);

    @Autowired
    private SemesterMapper semesterMapper;

    public void addSemester(SemesterDO semesterDO){
        semesterMapper.addSemester(semesterDO);
    }

    public SemesterBO getSemester(){
        SemesterDO semesterDO = semesterMapper.getSemester();

        logger.info("semester startTime is "+semesterDO.getStartTime());

        SemesterBO semesterBO = new SemesterBO();
        semesterBO.setSemesterName(String.valueOf(semesterDO.getId()));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        semesterBO.setStartTime(semesterDO.getStartTime().format(dateTimeFormatter));
        semesterBO.setEndTime(semesterDO.getEndTime().format(dateTimeFormatter));

        return semesterBO;
    }
}
