package com.uzpeng.sign.service;

import com.uzpeng.sign.dao.SemesterDAO;
import com.uzpeng.sign.domain.SemesterDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author serverliu on 2018/4/10.
 */
@Service
public class SemesterService {
    @Autowired
    private SemesterDAO semesterDAO;

    public void addSemester(SemesterDO semesterDO){
        semesterDAO.addSemester(semesterDO);
    }

}
