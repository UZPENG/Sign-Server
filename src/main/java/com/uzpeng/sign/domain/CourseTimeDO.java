package com.uzpeng.sign.domain;

/**
 * @author serverliu on 2018/4/10.
 */
public class CourseTimeDO {
    private Integer id;
    private Integer course_id;
    private Integer course_weekday;
    private Integer course_section_start;
    private Integer course_section_end;
    private String  loc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return course_id;
    }

    public void setCourseId(Integer courseId) {
        this.course_id = courseId;
    }

    public Integer getCourseWeekday() {
        return course_weekday;
    }

    public void setCourseWeekday(Integer courseWeekday) {
        this.course_weekday = courseWeekday;
    }

    public Integer getCourseSectionStart() {
        return course_section_start;
    }

    public void setCourseSectionStart(Integer courseSectionStart) {
        this.course_section_start = courseSectionStart;
    }

    public Integer getCourseSectionEnd() {
        return course_section_end;
    }

    public void setCourseSectionEnd(Integer courseSectionEnd) {
        this.course_section_end = courseSectionEnd;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
