package com.uzpeng.sign.dao;

import com.uzpeng.sign.domain.TeacherDO;
import com.uzpeng.sign.persistence.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author serverliu on 2018/4/10.
 */
@Repository
public class TeacherDAO {
    @Autowired
    private TeacherMapper teacherMapper;

    public void addTeacher(TeacherDO teacherDO){
        teacherMapper.addTeacher(teacherDO);
    }

    public void updateTeacher(TeacherDO teacherDO){
        teacherMapper.updateTeacher(teacherDO);
    }
}
