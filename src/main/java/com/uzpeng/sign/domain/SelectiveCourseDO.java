package com.uzpeng.sign.domain;

/**
 * @author serverliu on 2018/4/11.
 */
public class SelectiveCourseDO {
    private Integer courseId;
    private Integer studentId;
    private String type;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
