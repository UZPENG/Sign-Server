package com.uzpeng.sign.domain;

/**
 * @author serverliu on 2018/4/10.
 */
public class CourseDO {
    private Integer id;
    private String course_num;
    private String name;
    private Integer semester;
    private Integer teacher_id;
    private Integer credit;
    private Integer amount;
    private Integer start_week;
    private Integer end_week;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseNum() {
        return course_num;
    }

    public void setCourseNum(String courseNum) {
        this.course_num = courseNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getTeacherId() {
        return teacher_id;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacher_id = teacherId;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getStartWeek() {
        return start_week;
    }

    public void setStartWeek(Integer startWeek) {
        this.start_week = startWeek;
    }

    public Integer getEndWeek() {
        return end_week;
    }

    public void setEndWeek(Integer endWeek) {
        this.end_week = endWeek;
    }
}
