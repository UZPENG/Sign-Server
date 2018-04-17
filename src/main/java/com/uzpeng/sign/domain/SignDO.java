package com.uzpeng.sign.domain;

import java.time.LocalDateTime;

/**
 * @author uzpeng on 2018/4/17.
 */
public class SignDO {
    private Integer id;
    private LocalDateTime create_time;
    private Integer course_time_id;
    private Integer course_id;
    private Integer week;

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

    public LocalDateTime getCreateTime() {
        return create_time;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.create_time = createTime;
    }

    public Integer getCourseTimeId() {
        return course_time_id;
    }

    public void setCourseTimeId(Integer courseTimeId) {
        this.course_time_id = courseTimeId;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }
}
