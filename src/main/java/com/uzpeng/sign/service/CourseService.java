package com.uzpeng.sign.service;

import com.uzpeng.sign.dao.CourseDAO;
import com.uzpeng.sign.dao.CourseTimeDAO;
import com.uzpeng.sign.dao.bo.CourseListBO;
import com.uzpeng.sign.util.ObjectTranslateUtil;
import com.uzpeng.sign.web.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
