package com.uzpeng.sign.domain;

import java.time.LocalDateTime;

/**
 * @author serverliu on 2018/4/10.
 */
public class SemesterDO {
    private Integer id;
    private Integer teacher_id;
    private String name;
    private LocalDateTime start_time;
    private LocalDateTime end_time;

    public Integer getTeacherId() {
        return teacher_id;
    }

    public void setTeacherId(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return start_time;
    }

    public void setStartTime(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public LocalDateTime getEndTime() {
        return end_time;
    }

    public void setEndTime(LocalDateTime end_time) {
        this.end_time = end_time;
    }
}
