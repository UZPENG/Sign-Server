package com.uzpeng.sign.dao;

import com.uzpeng.sign.dao.bo.StudentBO;
import com.uzpeng.sign.dao.bo.StudentBOList;
import com.uzpeng.sign.domain.SelectiveCourseDO;
import com.uzpeng.sign.domain.StudentDO;
import com.uzpeng.sign.domain.UserDO;
import com.uzpeng.sign.persistence.StudentMapper;
import com.uzpeng.sign.util.CryptoUtil;
import com.uzpeng.sign.util.ObjectTranslateUtil;
import com.uzpeng.sign.util.RandomUtil;
import com.uzpeng.sign.util.Role;
import com.uzpeng.sign.web.dto.SignRecordDTO;
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
    private UserDAO userDAO;
    @Autowired
    private SelectiveCourseDAO selectiveCourseDAO;
    @Autowired
    private SignDAO signDAO;

    public void insertStudents(List<StudentDO> students, Integer courseId){
        logger.info("student list's size is " + students.size());

        List<String> existNumList = studentMapper.getStudentNum();

        logger.info("existNumList is "+existNumList.size());

        List<StudentDO> noAccountStudents = new ArrayList<>();
        for (StudentDO student :
                students) {
            if (existNumList.size() == 0 || !existNumList.contains(student.getNum())){
                noAccountStudents.add(student);
            }
        }

        logger.info("noAccountStudents is "+noAccountStudents.size());

        if(noAccountStudents.size() > 0) {
            studentMapper.insertStudentList(noAccountStudents);

            List<UserDO> users = new ArrayList<>();
            for (StudentDO currentStudent : noAccountStudents) {
                UserDO tmpUser = new UserDO();

                tmpUser.setName(String.valueOf(currentStudent.getNum()));
                tmpUser.setPassword(CryptoUtil.encodePassword(String.valueOf(currentStudent.getNum())));
                tmpUser.setRole(Role.STUDENT);
                tmpUser.setRoleId(currentStudent.getId());
                tmpUser.setRegisterTime(LocalDateTime.now());
                users.add(tmpUser);
            }
            //todo 处理已经存在的异常
            userDAO.insertUserList(users);
        }

        insertSelectiveCourse(students, courseId);
    }

    public void insertStudent(StudentDO studentDO, Integer courseId){
        List<StudentDO> student = new ArrayList<>();
        student.add(studentDO);

        insertStudents(student, courseId);
    }

    public StudentBOList getStudent(Integer courseId){
        List<StudentBO> studentBOs = new ArrayList<>();

        List<Integer> studentId = selectiveCourseDAO.getStudentIdByCourseId(courseId);

        if(studentId == null || studentId.size() == 0){
            StudentBOList studentBOList = new StudentBOList();
            studentBOList.setStudentList(studentBOs);
            return studentBOList;
        }
        List<StudentDO> studentDOs = studentMapper.getStudentListByStudentId(studentId);

        for (StudentDO studentDO :
                studentDOs) {
            studentBOs.add(ObjectTranslateUtil.studentDOToStudentBO(studentDO));
        }

        StudentBOList studentBOList = new StudentBOList();
        studentBOList.setStudentList(studentBOs);

        return studentBOList;
    }

    public StudentBOList  searchStudentByNum(String num){
        String queryStr = "%" + num + "%";

        StudentBOList studentBOList = new StudentBOList();

        List<StudentBO> studentBOS = new ArrayList<>();
        List<StudentDO> studentDOS = studentMapper.getStudentListByStudentNum(queryStr);
        for (StudentDO studentDO :
                studentDOS) {
            StudentBO studentBO = ObjectTranslateUtil.studentDOToStudentBO(studentDO);
            studentBOS.add(studentBO);
        }

        studentBOList.setStudentList(studentBOS);

        return studentBOList;
    }

    public StudentDO getStudentById(Integer studentId){
        return studentMapper.getStudent(studentId);
    }


    public void removeStudent(Integer courseId, Integer studentId){
        selectiveCourseDAO.removeStudent(courseId, studentId);
    }

    private void insertSelectiveCourse(List<StudentDO> studentDOS, Integer courseId){
        logger.info("insert selective course info....");

        List<String> numList = new ArrayList<>();

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
        selectiveCourseDAO.addSelectiveCourseList(selectiveCourseDOS);
    }

    public StudentBOList pickStudent(Integer courseId, int amount){
        StudentBOList studentBOList = getStudent(courseId);

        List<StudentBO> studentBOS = studentBOList.getStudentList();

        List<StudentBO> result = RandomUtil.pickAmountRandomly(studentBOS, amount);
        studentBOList.setStudentList(result);

        return studentBOList;
    }

    public void sign(SignRecordDTO signRecordDTO){

    }
}
