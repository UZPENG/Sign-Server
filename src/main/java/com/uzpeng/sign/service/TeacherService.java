package com.uzpeng.sign.service;

import com.uzpeng.sign.dao.TeacherDAO;
import com.uzpeng.sign.domain.TeacherDO;
import com.uzpeng.sign.util.ObjectTranslateUtil;
import com.uzpeng.sign.web.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author serverliu on 2018/4/10.
 */
@Service
public class TeacherService {
    @Autowired
    private TeacherDAO teacherDAO;

    public void addTeacher(TeacherDTO teacherDTO){
        teacherDAO.addTeacher(ObjectTranslateUtil.teacherDTOToTeacherDO(teacherDTO));
    }

    public void updateTeacher(TeacherDO teacherDO){

    }
}
