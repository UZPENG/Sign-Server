package com.uzpeng.sign.web.dto;

import java.util.List;

/**
 * @author serverliu on 2018/4/10.
 */
public class CourseDTO {
    private String courseId;
    private Integer teacherId;
    private String courseName;
    private String courseNum;
    private String semesterId;
    private Integer startWeek;
    private Integer endWeek;
    private List<CourseTimeDetailDTO> time;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public List<CourseTimeDetailDTO> getTime() {
        return time;
    }

    public void setTime(List<CourseTimeDetailDTO> time) {
        this.time = time;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    public String getSemester() {
        return semesterId;
    }

    public void setSemester(String semester) {
        this.semesterId = semester;
    }
}
