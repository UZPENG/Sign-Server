package com.uzpeng.sign.domain;

import java.time.LocalDateTime;

/**
 * @author serverliu on 2018/4/10.
 */
public class SignRecordDO {
    private Integer id;
    private Integer course_sign_id;
    private Integer student_id;
    private Double longitude;
    private Double latitude;
    private Integer state;
    private String accuracy;
    private String device_no;
    private LocalDateTime sign_time;

    public Integer getSignId() {
        return course_sign_id;
    }

    public void setSignId(Integer sign_id) {
        this.course_sign_id = sign_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getDeviceNo() {
        return device_no;
    }

    public void setDeviceNo(String device_no) {
        this.device_no = device_no;
    }

    public LocalDateTime getSignTime() {
        return sign_time;
    }

    public void setSignTime(LocalDateTime time) {
        this.sign_time = time;
    }


    public Integer getStudentId() {
        return student_id;
    }

    public void setStudentId(Integer student_id) {
        this.student_id = student_id;
    }

}
