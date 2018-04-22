package com.uzpeng.sign.web.dto;

/**
 * @author uzpeng on 2018/4/17.
 */
public class CreateSignRecordDTO {
    private Integer courseTimeId;
    private Integer week;

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getCourseTimeId() {
        return courseTimeId;
    }

    public void setCourseTimeId(Integer courseTimeId) {
        this.courseTimeId = courseTimeId;
    }
}
