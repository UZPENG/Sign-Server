package com.uzpeng.sign.util;

import com.uzpeng.sign.domain.*;
import com.uzpeng.sign.web.dto.CourseDTO;
import com.uzpeng.sign.web.dto.RegisterDTO;
import com.uzpeng.sign.web.dto.SemesterDTO;
import com.uzpeng.sign.web.dto.TeacherDTO;

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

        Integer id = startYear * 10 + semester+1;

        semesterDO.setId(id);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        semesterDO.setStartTime(LocalDateTime.parse(semesterDTO.getDate().get(0), dateTimeFormatter));
        semesterDO.setEndTime(LocalDateTime.parse(semesterDTO.getDate().get(1), dateTimeFormatter));

        return semesterDO;
    }

    public static CourseDO courseDTOToCourseDO(CourseDTO courseDTO){
        CourseDO courseDO = new CourseDO();

        courseDO.setTeacherId(courseDTO.getTeacherId());
        courseDO.setName(courseDTO.getCourseName());
        courseDO.setCourseNum(courseDTO.getCourseNum());
        courseDO.setSemester(Integer.parseInt(courseDTO.getSemester()));
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


}
