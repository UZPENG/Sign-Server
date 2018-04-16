package com.uzpeng.sign.util;

import com.uzpeng.sign.dao.bo.CourseBO;
import com.uzpeng.sign.dao.bo.SemesterBO;
import com.uzpeng.sign.dao.bo.StudentBO;
import com.uzpeng.sign.domain.*;
import com.uzpeng.sign.web.dto.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author serverliu on 2018/4/6.
 */
public class ObjectTranslateUtil {

    public static UserDO registerDTOToUserDO(RegisterDTO registerDTO){
        UserDO userDO = new UserDO();

        userDO.setName(registerDTO.getUsername());
        userDO.setPassword(CryptoUtil.encodePassword(registerDTO.getPassword()));
        userDO.setEmail(registerDTO.getEmail());
        userDO.setRegisterTime(LocalDateTime.now());

        return userDO;
    }

    public static TeacherDO teacherDTOToTeacherDO(TeacherDTO teacherDTO){
        TeacherDO teacherDO = new TeacherDO();
        teacherDO.setCardNum(teacherDTO.getNum());
        teacherDO.setName(teacherDTO.getName());

        return teacherDO;
    }

    public static SemesterDO semesterDTOToSemesterDO(SemesterDTO semesterDTO){
        SemesterDO semesterDO = new SemesterDO();

        Integer semester = Integer.parseInt(semesterDTO.getSemester());
        Integer startYear = Integer.parseInt(semesterDTO.getStartYear());

        Integer id = startYear * 10 + semester;

        semesterDO.setId(id);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        semesterDO.setName(DateUtil.semesterIdToName(id));
        semesterDO.setStartTime(LocalDateTime.parse(semesterDTO.getDate().get(0), dateTimeFormatter));
        semesterDO.setEndTime(LocalDateTime.parse(semesterDTO.getDate().get(1), dateTimeFormatter));

        return semesterDO;
    }

    public static CourseDO courseDTOToCourseDO(CourseDTO courseDTO){
        CourseDO courseDO = new CourseDO();

        courseDO.setId(Integer.parseInt(courseDTO.getCourseId()));
        courseDO.setTeacherId(courseDTO.getTeacherId());
        courseDO.setName(courseDTO.getCourseName());
        courseDO.setCourseNum(courseDTO.getCourseNum());
        //todo 非法参数处理
        courseDO.setSemester(DateUtil.semesterNameToId(courseDTO.getSemester()));
        courseDO.setStartWeek(courseDTO.getStartWeek());
        courseDO.setEndWeek(courseDTO.getEndWeek());

        return courseDO;
    }

    public static List<CourseTimeDO> courseDTOToCourseTimeDO(CourseDTO courseDTO, int courseId){
        List<CourseTimeDO> courseTimeList = new ArrayList<>();

        List<CourseDTO.CourseTimeDetail> courseTimeDetails =courseDTO.getTime();
        for (CourseDTO.CourseTimeDetail time:
             courseTimeDetails) {

            CourseTimeDO courseTimeDO = new CourseTimeDO();
            courseTimeDO.setLoc(time.getLoc());
            courseTimeDO.setCourseSectionStart(time.getStart());
            courseTimeDO.setCourseSectionEnd(time.getEnd());
            courseTimeDO.setCourseWeekday(time.getWeekday());
            courseTimeDO.setCourseId(courseId);

            courseTimeList.add(courseTimeDO);
        }

        return courseTimeList;
    }

    public static StudentDO studentDTOToStudentDO(StudentDTO studentDTO){
        StudentDO studentDO = new StudentDO();

        studentDO.setName(studentDTO.getName());
        studentDO.setClassInfo(studentDTO.getClassInfo());
        studentDO.setNum(studentDTO.getNum());

        return studentDO;
    }

   public static LoginDTO passwordDTOToLoginDTO(PasswordDTO passwordDTO){
       LoginDTO loginDTO = new LoginDTO();

       loginDTO.setUsername(passwordDTO.getUserName());
       loginDTO.setPassword(passwordDTO.getOldPassword());

       return loginDTO;
   }

   public static SemesterBO semesterDOToSemesterBO(SemesterDO semesterDO){
       SemesterBO semesterBO = new SemesterBO();
       semesterBO.setSemesterId(semesterDO.getId());
       semesterBO.setSemesterName(String.valueOf(semesterDO.getName()));
       DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
       semesterBO.setStartTime(semesterDO.getStartTime().format(dateTimeFormatter));
       semesterBO.setEndTime(semesterDO.getEndTime().format(dateTimeFormatter));

       return semesterBO;
   }

   public static StudentBO studentDOToStudentBO(StudentDO studentDO){
        StudentBO studentBO = new StudentBO();

        studentBO.setId(studentDO.getId());
        studentBO.setName(studentDO.getName());
        studentBO.setStudentNum(studentDO.getNum());
        studentBO.setClassInfo(studentDO.getClassInfo());

        return studentBO;
   }

   public static CourseBO courseDOToCourseBO(CourseDO courseDO, List<CourseTimeDO> courseTimeDOList,
                                             SemesterBO semesterBO){
       CourseBO courseBO = new CourseBO();
       courseBO.setCourseId(courseDO.getId());
       courseBO.setCourseName(courseDO.getName());
       courseBO.setCourseNum(courseDO.getCourseNum());
       courseBO.setSemester(semesterBO.getSemesterName());
       courseBO.setStartWeek(courseDO.getStartWeek());
       courseBO.setEndWeek(courseDO.getEndWeek());
       courseBO.setTime(new ArrayList<>());
       courseBO.setTeacherId(courseDO.getTeacherId());

       for (CourseTimeDO courseTimeDO :
               courseTimeDOList) {
           CourseBO.CourseTimeDetail timeDetail = new CourseBO.CourseTimeDetail();

           timeDetail.setStart(courseTimeDO.getCourseSectionStart());
           timeDetail.setEnd(courseTimeDO.getCourseSectionEnd());
           timeDetail.setWeekday(courseTimeDO.getCourseWeekday());
           timeDetail.setLoc(courseTimeDO.getLoc());

           courseBO.getTime().add(timeDetail);
       }
       return courseBO;
   }
}
