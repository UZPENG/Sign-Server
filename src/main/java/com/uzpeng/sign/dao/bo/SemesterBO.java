package com.uzpeng.sign.dao.bo;

/**
 * @author serverliu on 2018/4/12.
 */
public class SemesterBO {
    private Integer semesterId;
    private String semesterName;
    private String startTime;
    private String endTime;

    public Integer getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Integer semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String id) {
        this.semesterName = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
