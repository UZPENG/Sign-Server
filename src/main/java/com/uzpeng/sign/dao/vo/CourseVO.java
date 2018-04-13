package com.uzpeng.sign.dao.vo;

import com.uzpeng.sign.web.dto.CourseDTO;

import java.util.List;

/**
 * @author serverliu on 2018/4/12.
 */
public class CourseVO {
    private List<CourseDTO> courseList;

    public List<CourseDTO> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseDTO> courseList) {
        this.courseList = courseList;
    }
}
