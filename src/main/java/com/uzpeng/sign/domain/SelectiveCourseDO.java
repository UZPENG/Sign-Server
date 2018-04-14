package com.uzpeng.sign.domain;

/**
 * @author serverliu on 2018/4/11.
 */
public class SelectiveCourseDO {
    private Integer course_id;
    private Integer student_id;
    private String type;

    public Integer getCourseId() {
        return course_id;
    }

    public void setCourseId(Integer courseId) {
        this.course_id = courseId;
    }

    public Integer getStudentId() {
        return student_id;
    }

    public void setStudentId(Integer studentId) {
        this.student_id = studentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
