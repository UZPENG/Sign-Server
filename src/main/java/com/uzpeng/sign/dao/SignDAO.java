package com.uzpeng.sign.dao;

import com.uzpeng.sign.dao.bo.*;
import com.uzpeng.sign.domain.CourseTimeDO;
import com.uzpeng.sign.domain.SignDO;
import com.uzpeng.sign.domain.SignRecordDO;
import com.uzpeng.sign.domain.StudentDO;
import com.uzpeng.sign.persistence.SignMapper;
import com.uzpeng.sign.persistence.SignRecordMapper;
import com.uzpeng.sign.util.DateUtil;
import com.uzpeng.sign.util.ObjectTranslateUtil;
import com.uzpeng.sign.util.Status;
import com.uzpeng.sign.web.dto.UpdateSignRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author uzpeng on 2018/4/16.
 */
@Repository
public class SignDAO {
    @Autowired
    private SignRecordMapper signRecordMapper;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private SemesterDAO semesterDAO;
    @Autowired
    private CourseDAO courseDAO;
    @Autowired
    private SelectiveCourseDAO selectiveCourseDAO;
    @Autowired
    private CourseTimeDAO courseTimeDAO;
    @Autowired
    private SignMapper signMapper;

    public void createSign(Integer courseTimeId){
        CourseTimeDO courseTimeDO = courseTimeDAO.getCourseTimeById(courseTimeId);

        Integer courseId = courseTimeDO.getCourseId();

        StudentBOList studentList = studentDAO.getStudent(courseId);

        CourseBO courseBO = courseDAO.getCourseById(courseId);

        String startTimeStr = semesterDAO.getSemesterById(courseBO.getSemesterId(),
                courseBO.getTeacherId()).getStartTime();

        LocalDateTime startTime = LocalDateTime.parse(startTimeStr);

        SignDO signDO = new SignDO();
        signDO.setCourseId(courseId);
        signDO.setCreateTime(LocalDateTime.now());
        signDO.setWeek(DateUtil.getWeekFrom(startTime));
        signDO.setCourseTimeId(courseTimeId);
        List<SignDO> signDOs = new ArrayList<>();
        signDOs.add(signDO);
        signMapper.insertSignList(signDOs);

        List<StudentBO> studentBOS = studentList.getStudentList();
        List<SignRecordDO> signRecordDOS = new ArrayList<>();
        for (StudentBO studentBO :
                studentBOS) {
            SignRecordDO signRecordDO = new SignRecordDO();

            signRecordDO.setStudentId(studentBO.getId());
            signRecordDO.setSignId(signDO.getId());
            signRecordDO.setSignTime(LocalDateTime.of(1, 1,1,1,1));
            signRecordDO.setState(Status.CREATED);
            signRecordDOS.add(signRecordDO);
        }

        signRecordMapper.insertSignRecordList(signRecordDOS);
    }

    public SignRecordListBO getSignRecordByTime(Integer courseId, Integer courseTimeId, Integer week, String num){
        List<SignDO> signDOs = signMapper.getSignRecordByTimeAndCourse(courseId, courseTimeId, week);
        List<Integer> signIds = signMapper.getSignIdByCourseId(courseId);

        List<SignRecordBO> signRecordBOs = new ArrayList<>();
        List<Integer> studentIds = new ArrayList<>();
        if(num != null ){
            StudentBOList studentBOList = studentDAO.searchStudentByNum(num);
            List<StudentBO> studentBOs = studentBOList.getStudentList();

            for (StudentBO studentBO :
                    studentBOs) {
                studentIds.add(studentBO.getId());
            }
        }
        for (SignDO signDO : signDOs) {
            Integer signId = signDO.getId();
            List<SignRecordDO> records = signRecordMapper.getSignRecord(signId);

            for (SignRecordDO recordDO :
                    records) {
                Integer studentId = recordDO.getStudentId();
                if(num != null){
                    if(!studentIds.contains(studentId)){
                        continue;
                    }
                }
                StudentDO studentDO = studentDAO.getStudentById(studentId);

                SignRecordBO signRecordBO = new SignRecordBO();
                signRecordBO.setCourseId(courseId);
                signRecordBO.setStudentNum(studentDO.getNum());
                signRecordBO.setName(studentDO.getName());
                signRecordBO.setClass_info(studentDO.getClassInfo());
                signRecordBO.setId(recordDO.getId());
                signRecordBO.setWeek(signDO.getWeek());
                signRecordBO.setState(recordDO.getState());

                CourseTimeDO courseTimeDO = courseTimeDAO.getCourseTimeById(courseTimeId);

                signRecordBO.setWeekday(ObjectTranslateUtil.courseTimeDoToString(courseTimeDO));

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
                signRecordBO.setSignTime(dateTimeFormatter.format(recordDO.getSignTime()));

                signRecordBOs.add(signRecordBO);
            }
        }

        SignRecordListBO signRecordListBO = new SignRecordListBO();
        signRecordListBO.setList(signRecordBOs);
        return signRecordListBO;
    }

    public void UpdateSignRecordStatus(UpdateSignRecordDTO updateSignRecordDTO){
        signRecordMapper.updateSignRecord(updateSignRecordDTO.getId(), updateSignRecordDTO.getState());
    }

    public void sign(SignRecordDO signRecordDO){
        signRecordMapper.sign(signRecordDO);
    }

   public SignRecordTimeListBO getRecordWeek(Integer courseId){
        int count = selectiveCourseDAO.getStudentCount(courseId);

        List<SignDO> signDOs = signMapper.getSignRecord(courseId);
        List<SignRecordTimeBO> signRecordWeekBOs = new ArrayList<>();
        for (SignDO signDO :
               signDOs) {
            int signedCount = signRecordMapper.getSignCountBySignId(signDO.getId(), Status.SUCCESS);

            SignRecordTimeBO signRecordTimeBO = new SignRecordTimeBO();

            CourseTimeDO courseTimeDO = courseTimeDAO.getCourseTimeById(signDO.getCourseTimeId());

            signRecordTimeBO.setCourseId(courseId);
            signRecordTimeBO.setWeek(signDO.getWeek());
            signRecordTimeBO.setAmount(count);
            signRecordTimeBO.setCourseTimeId(signDO.getCourseTimeId());
            signRecordTimeBO.setWeekday(ObjectTranslateUtil.courseTimeDoToString(courseTimeDO));
            signRecordTimeBO.setSignedAmount(signedCount);

            signRecordWeekBOs.add(signRecordTimeBO);
        }

       SignRecordTimeListBO signRecordTimeListBO = new SignRecordTimeListBO();
       signRecordTimeListBO.setList(signRecordWeekBOs);

       return signRecordTimeListBO;
   }

}
