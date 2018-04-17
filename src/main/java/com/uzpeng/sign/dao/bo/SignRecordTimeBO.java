package com.uzpeng.sign.dao.bo;

/**
 * @author uzpeng on 2018/4/17.
 */
public class SignRecordTimeBO {
    private Integer courseId;
    private Integer courseTimeId;
    private String weekday;
    private Integer week;
    private Integer amount;
    private Integer signedAmount;

    public Integer getCourseTimeId() {
        return courseTimeId;
    }

    public void setCourseTimeId(Integer courseTimeId) {
        this.courseTimeId = courseTimeId;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getSignedAmount() {
        return signedAmount;
    }

    public void setSignedAmount(Integer signedAmount) {
        this.signedAmount = signedAmount;
    }
}
