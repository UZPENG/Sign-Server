package com.uzpeng.sign.dao;

import com.uzpeng.sign.domain.StudentDO;
import com.uzpeng.sign.domain.UserDO;
import com.uzpeng.sign.persistence.StudentMapper;
import com.uzpeng.sign.persistence.UserMapper;
import com.uzpeng.sign.util.CryptoUtil;
import com.uzpeng.sign.util.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author serverliu on 2018/4/7.
 */
@Repository
public class StudentDAO {

    private static final Logger logger = LoggerFactory.getLogger(StudentDAO.class);

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserMapper userMapper;

    public void insertStudents(List<StudentDO> students){
        studentMapper.insertStudentList(students);
        List<UserDO> users = new ArrayList<>();

        logger.info("student list's size is " + students.size());
        for (int i = 0; i < students.size(); i++) {
            UserDO tmpUser = new UserDO();
            StudentDO currentStudent = students.get(i);
            tmpUser.setName(currentStudent.getNum());
            tmpUser.setPassword(CryptoUtil.encodePassword(currentStudent.getNum()));
            tmpUser.setRole(Role.STUDENT);
            tmpUser.setRoleId(students.get(i).getId());
            tmpUser.setRegisterTime(LocalDateTime.now());
            users.add(tmpUser);
        }
        
        userMapper.insertUserList(users);
    }
}
