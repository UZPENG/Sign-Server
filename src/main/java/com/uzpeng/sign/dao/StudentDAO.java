package com.uzpeng.sign.dao;

import com.uzpeng.sign.dao.vo.StudentVO;
import com.uzpeng.sign.domain.SelectiveCourseDO;
import com.uzpeng.sign.domain.StudentDO;
import com.uzpeng.sign.domain.UserDO;
import com.uzpeng.sign.persistence.SelectiveCourseMapper;
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
    @Autowired
    private SelectiveCourseMapper selectiveCourseMapper;

    public void insertStudents(List<StudentDO> students, Integer courseId){
        logger.info("student list's size is " + students.size());

        List<Integer> existNumList = studentMapper.getStudentNum();

        List<StudentDO> noAccountStudents = new ArrayList<>();
        for (StudentDO student :
                students) {
            if (!existNumList.contains(student.getNum())){
                noAccountStudents.add(student);
            }
        }

        studentMapper.insertStudentList(noAccountStudents);

        List<UserDO> users = new ArrayList<>();
        for (StudentDO currentStudent : students) {
            UserDO tmpUser = new UserDO();

            tmpUser.setName(String.valueOf(currentStudent.getNum()));
            tmpUser.setPassword(CryptoUtil.encodePassword(String.valueOf(currentStudent.getNum())));
            tmpUser.setRole(Role.STUDENT);
            tmpUser.setRoleId(currentStudent.getId());
            tmpUser.setRegisterTime(LocalDateTime.now());
            users.add(tmpUser);
        }
        
        userMapper.insertUserList(users);

        insertSelectiveCourse(students, courseId);
    }

    public void insertStudent(StudentDO studentDO, Integer courseId){
        List<StudentDO> student = new ArrayList<>();
        student.add(studentDO);

        insertStudents(student, courseId);
    }

    public List<StudentVO> getStudent(Integer courseId){
//        List<StudentDO> studentDOList = studentMapper.getStudent(1);
        //todo 
        return null;
    }

    private void insertSelectiveCourse(List<StudentDO> studentDOS, Integer courseId){
        List<Integer> numList = new ArrayList<>();

        for (StudentDO student :
                studentDOS) {
            numList.add(student.getNum());
        }

        List<Integer> ids = studentMapper.getStudentIdByNum(numList);

        List<SelectiveCourseDO> selectiveCourseDOS = new ArrayList<>();
        for (Integer id : ids) {
            SelectiveCourseDO selectiveCourseDO = new SelectiveCourseDO();
            selectiveCourseDO.setCourseId(courseId);
            selectiveCourseDO.setStudentId(id);

            selectiveCourseDOS.add(selectiveCourseDO);
        }
        selectiveCourseMapper.addSelectiveCourseList(selectiveCourseDOS);
    }
}
