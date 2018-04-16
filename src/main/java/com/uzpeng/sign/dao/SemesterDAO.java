package com.uzpeng.sign.dao;

import com.uzpeng.sign.dao.bo.SemesterBO;
import com.uzpeng.sign.dao.bo.SemesterBOList;
import com.uzpeng.sign.domain.SemesterDO;
import com.uzpeng.sign.persistence.SemesterMapper;
import com.uzpeng.sign.util.ObjectTranslateUtil;
import org.omg.PortableInterceptor.INACTIVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public SemesterBOList getSemester(){
        List<SemesterDO> semesterDOs = semesterMapper.getSemester();
        List<SemesterBO> semesterBOs = new ArrayList<>();

        for (SemesterDO semesterDO:
             semesterDOs) {
            logger.info("semester startTime is "+semesterDO.getStartTime());

            semesterBOs.add(ObjectTranslateUtil.semesterDOToSemesterBO(semesterDO));
        }

        SemesterBOList semesterBOList = new SemesterBOList();
        semesterBOList.setSemesterList(semesterBOs);

        return semesterBOList;
    }

    public SemesterBO getSemesterById(Integer id){
        SemesterDO semesterDO = semesterMapper.getSemesterById(id);

        return ObjectTranslateUtil.semesterDOToSemesterBO(semesterDO);
    }

    public void updateSemester(SemesterDO semesterDO){
        semesterMapper.updateSemester(semesterDO);
    }

    public void deleteSemester(Integer id){
        semesterMapper.deleteSemester(id);
    }
}
