package com.uzpeng.sign.domain;

/**
 * @author serverliu on 2018/4/7.
 */
public class StudentDO {
    private Integer id;
    private String student_num;
    private String name;
    private String class_info;
    private String college_id;
    private Integer entrance_year;
    private String note;

    public String getCollegeId() {
        return college_id;
    }

    public void setCollegeId(String collegeId) {
        this.college_id = collegeId;
    }

    public Integer getEntranceYear() {
        return entrance_year;
    }

    public void setEntranceYear(Integer entranceYear) {
        this.entrance_year = entranceYear;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNum() {
        return student_num;
    }

    public void setNum(String num) {
        this.student_num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassInfo() {
        return class_info;
    }

    public void setClassInfo(String classInfo) {
        this.class_info = classInfo;
    }
}
