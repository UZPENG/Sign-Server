package com.uzpeng.sign.domain;

/**
 * @author serverliu on 2018/4/10.
 */
public class TeacherDO {
    private Integer id;
    private String name;
    private Integer card_num;
    private String tel_number;
    private String office_hour;
    private String office_loc;
    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCardNum() {
        return card_num;
    }

    public void setCardNum(Integer cardNum) {
        this.card_num = cardNum;
    }

    public String getTelNumber() {
        return tel_number;
    }

    public void setTelNumber(String telNumber) {
        this.tel_number = telNumber;
    }

    public String getOfficeHour() {
        return office_hour;
    }

    public void setOfficeHour(String officeHour) {
        this.office_hour = officeHour;
    }

    public String getOfficeLoc() {
        return office_loc;
    }

    public void setOfficeLoc(String officeLoc) {
        this.office_loc = officeLoc;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
