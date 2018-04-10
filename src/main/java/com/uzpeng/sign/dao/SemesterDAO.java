package com.uzpeng.sign.dao;

import com.uzpeng.sign.domain.SemesterDO;
import com.uzpeng.sign.persistence.SemesterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author serverliu on 2018/4/10.
 */
@Repository
public class SemesterDAO {
    @Autowired
    private SemesterMapper semesterMapper;

    public void addSemester(SemesterDO semesterDO){
        semesterMapper.addSemester(semesterDO);
    }
}
