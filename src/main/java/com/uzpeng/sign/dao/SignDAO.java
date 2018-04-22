package com.uzpeng.sign.dao;

import com.uzpeng.sign.config.StatusConfig;
import com.uzpeng.sign.bo.*;
import com.uzpeng.sign.domain.*;
import com.uzpeng.sign.exception.DuplicateDataException;
import com.uzpeng.sign.persistence.SignMapper;
import com.uzpeng.sign.persistence.SignRecordMapper;
import com.uzpeng.sign.util.DateUtil;
import com.uzpeng.sign.util.ObjectTranslateUtil;
import com.uzpeng.sign.util.SpringContextUtil;
import com.uzpeng.sign.util.Status;
import com.uzpeng.sign.web.dto.SignRecordDTO;
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
    @Autowired
    private TeacherDAO teacherDAO;

    public void createSign(Integer courseTimeId, Integer week) throws DuplicateDataException{
        CourseTimeDO courseTimeDO = courseTimeDAO.getCourseTimeById(courseTimeId);

        Integer courseId = courseTimeDO.getCourseId();

        StudentBOList studentList = studentDAO.getStudent(courseId);

        CourseBO courseBO = courseDAO.getCourseById(courseId);

        String startTimeStr = semesterDAO.getSemesterById(courseBO.getSemesterId(),
                courseBO.getTeacherId()).getStartTime();

        LocalDateTime startTime = LocalDateTime.parse(startTimeStr);

        if(signMapper.checkExistSign(courseTimeId, week) > 0){
            throw SpringContextUtil.getBeanByClass(DuplicateDataException.class);
        }
        SignDO signDO = new SignDO();
        signDO.setCourseId(courseId);
        signDO.setCreateTime(LocalDateTime.now());
        signDO.setWeek(week);
        signDO.setCourseTimeId(courseTimeId);
        signDO.setState(StatusConfig.CREATE);
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
        SignDO signDO = signMapper.getSignByTimeAndCourse(courseId, courseTimeId, week);

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

        SignRecordListBO signRecordListBO = new SignRecordListBO();
        signRecordListBO.setList(signRecordBOs);
        signRecordListBO.setSignId(signId);
        return signRecordListBO;
    }

    public DownloadSignRecordBOList getAllSignRecord(Integer courseId){
        StudentBOList studentBOList = studentDAO.getStudent(courseId);
        List<StudentBO> studentBOS = studentBOList.getStudentList();

        DownloadSignRecordBOList downloadSignRecordBOList = new DownloadSignRecordBOList();
        List<List<StudentSignRecordBO>> studentSignList = new ArrayList<>();
        for (StudentBO studentBO :
                studentBOS) {
            int studentId = studentBO.getId();

            StudentSignRecordListBO studentSignRecordListBO = getSignRecordByStudentId(studentId, StatusConfig.ALL);
            List<StudentSignRecordBO> studentSignRecordBOList = studentSignRecordListBO.getStudentSignRecordBOList();
            for (StudentSignRecordBO record :
                    studentSignRecordBOList) {
                if(!record.getCourseId().equals(courseId)){
                    studentSignRecordBOList.remove(record);
                }
            }
            studentSignList.add(studentSignRecordBOList);
        }
        downloadSignRecordBOList.setDownloadSignRecordLists(studentSignList);
        return downloadSignRecordBOList;
    }

    public void UpdateSignRecordStatus(UpdateSignRecordDTO updateSignRecordDTO){
        SignRecordDO signRecordDO = new SignRecordDO();

        signRecordDO.setState(updateSignRecordDTO.getState());
        signRecordDO.setId(updateSignRecordDTO.getId());
        signRecordDO.setSignTime(LocalDateTime.now());

        signRecordMapper.updateSignRecord(signRecordDO);
    }

    public void sign(SignRecordDTO signRecordDTO){
        signRecordMapper.sign(signRecordDTO);
    }

   public SignRecordTimeListBO getRecordWeek(Integer courseId){
        int count = selectiveCourseDAO.getStudentCount(courseId);

        List<SignDO> signDOs = signMapper.getSign(courseId);
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

   public void deleteSignRecord(Integer courseId){
        List<Integer> signIds = signMapper.getSignIdByCourseId(courseId);
        if(signIds.size() > 0) {
            signRecordMapper.deleteBySignIdList(signIds);
            signMapper.deleteSignByCourseId(courseId);
        }
   }

   public Integer getSignState(Integer signId){
        return signMapper.getStateById(signId);
   }

    public void updateSignState(Integer signId, Integer state){
        signMapper.updateStateById(signId, state);
    }

    public StudentSignRecordListBO getSignRecordByStudentId(Integer studentId, Integer type){
        List<SignRecordDO> signRecordDOs = signRecordMapper.getSignRecordByStudentId(studentId);

        StudentSignRecordListBO studentSignRecordListBO = new StudentSignRecordListBO();

        List<StudentSignRecordBO> studentSignRecordBOS = new ArrayList<>();
        List<StudentSignRecordBO> historyStudentSignRecordBOS = new ArrayList<>();
        List<StudentSignRecordBO> todayStudentSignRecordBOS = new ArrayList<>();
        for (SignRecordDO signRecordDO :
                signRecordDOs) {
            int signId = signRecordDO.getSignId();

            SignDO signDO = signMapper.getSignById(signId);
            CourseBO courseBO = courseDAO.getCourseById(signDO.getCourseId());
            SemesterBO semesterBO = semesterDAO.getSemesterById(courseBO.getSemesterId(), courseBO.getTeacherId());
            TeacherDO teacherDO = teacherDAO.getTeacherId(courseBO.getTeacherId());
            CourseTimeDO courseTimeDO = courseTimeDAO.getCourseTimeById(signDO.getCourseTimeId());
            StudentDO studentDO = studentDAO.getStudentById(studentId);

            String weekday = ObjectTranslateUtil.courseTimeDoToString(courseTimeDO);
            String time = "第"+signDO.getWeek()+"周 "+weekday;

            StudentSignRecordBO studentSignRecordBO = new StudentSignRecordBO();
            studentSignRecordBO.setCourse(courseBO.getCourseName());
            studentSignRecordBO.setCourseId(courseBO.getCourseId());
            studentSignRecordBO.setTeacher(teacherDO.getName());
            studentSignRecordBO.setTime(time);
            studentSignRecordBO.setState(signRecordDO.getState());
            studentSignRecordBO.setLoc(courseTimeDO.getLoc());
            studentSignRecordBO.setClassInfo(studentDO.getClassInfo());
            studentSignRecordBO.setStudentNum(studentDO.getNum());
            studentSignRecordBO.setStudentName(studentDO.getName());

            int signWeek = signDO.getWeek();
            int signWeekday = courseTimeDO.getCourseWeekday();
            int currentWeek = DateUtil.getWeekFrom(LocalDateTime.parse(semesterBO.getStartTime()));
            int currentWeekday = LocalDateTime.now().getDayOfWeek().getValue();
            if(currentWeek < signWeek || (currentWeek == signWeek  && signWeekday >=currentWeekday)){
                todayStudentSignRecordBOS.add(studentSignRecordBO);
            } else {
                historyStudentSignRecordBOS.add(studentSignRecordBO);
            }
            studentSignRecordBOS.add(studentSignRecordBO);
        }
        if(type.equals(StatusConfig.HISTORY)){
             studentSignRecordListBO.setStudentSignRecordBOList(historyStudentSignRecordBOS);
        } else if(type.equals(StatusConfig.TODAY)){
             studentSignRecordListBO.setStudentSignRecordBOList(todayStudentSignRecordBOS);
        } else {
            studentSignRecordListBO.setStudentSignRecordBOList(studentSignRecordBOS);
        }
        return  studentSignRecordListBO;
    }


}
