package com.uzpeng.sign.dao;

import com.uzpeng.sign.domain.TeacherDO;
import com.uzpeng.sign.domain.UserDO;
import com.uzpeng.sign.persistence.TeacherMapper;
import com.uzpeng.sign.util.CryptoUtil;
import com.uzpeng.sign.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * @author serverliu on 2018/4/10.
 */
@Repository
public class TeacherDAO {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private UserDAO userDAO;

    public void addTeacher(TeacherDO teacherDO){
        teacherMapper.addTeacher(teacherDO);

        UserDO userDO = new UserDO();
        userDO.setName(String.valueOf(teacherDO.getCardNum()));
        userDO.setPassword(CryptoUtil.encodePassword(String.valueOf(teacherDO.getCardNum())));
        userDO.setRegisterTime(LocalDateTime.now());
        userDO.setRole(Role.TEACHER);
        userDO.setId(teacherDO.getId());
    }

    public TeacherDO getTeacherId(Integer teacherId){
        return teacherMapper.getTeacher(teacherId);
    }

    public void updateTeacher(TeacherDO teacherDO){
        teacherMapper.updateTeacher(teacherDO);
    }
}
