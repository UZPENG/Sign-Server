package com.uzpeng.sign.domain;

/**
 * @author serverliu on 2018/4/10.
 */
public class CourseTimeDO {
    private Integer id;
    private Integer courseId;
    private Integer courseWeekday;
    private Integer course_section;
    private String loc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getCourseWeekday() {
        return courseWeekday;
    }

    public void setCourseWeekday(Integer courseWeekday) {
        this.courseWeekday = courseWeekday;
    }

    public Integer getCourse_section() {
        return course_section;
    }

    public void setCourse_section(Integer course_section) {
        this.course_section = course_section;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
