package com.uzpeng.sign.service;

import com.uzpeng.sign.dao.CourseDAO;
import com.uzpeng.sign.dao.CourseTimeDAO;
import com.uzpeng.sign.dao.bo.CourseBO;
import com.uzpeng.sign.dao.bo.CourseListBO;
import com.uzpeng.sign.domain.CourseTimeDO;
import com.uzpeng.sign.util.ObjectTranslateUtil;
import com.uzpeng.sign.web.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author serverliu on 2018/4/11.
 */
@Service
public class CourseService {
    @Autowired
    private CourseDAO courseDAO;
    @Autowired
    private CourseTimeDAO courseTimeDAO;

    public void addCourse(CourseDTO courseDTO){
        int courseId = courseDAO.addCourse(ObjectTranslateUtil.courseDTOToCourseDO(courseDTO));
        courseTimeDAO.addCourseTimeList(ObjectTranslateUtil.courseDTOToCourseTimeDO(courseDTO, courseId));
    }

    public CourseListBO getCourse(Integer teacherId){
        return courseDAO.getCourseList(teacherId);
    }

    public CourseBO getCourseById(Integer courseId){
        return courseDAO.getCourseById(courseId);
    }

    public CourseListBO getCourseByName(String name){
        return courseDAO.getCourseByName(name);
    }

    public void deleteCourseById(Integer id){
         courseDAO.deleteCourseById(id);
    }

    public void updateCourse(CourseDTO courseDTO){
        Integer courseId = Integer.parseInt(courseDTO.getCourseId());
        courseTimeDAO.deleteCourseTime(courseId);

        courseTimeDAO.addCourseTimeList(ObjectTranslateUtil.courseDTOToCourseTimeDO(courseDTO, courseId));
        courseDAO.updateCourse(courseDTO);
    }
}
