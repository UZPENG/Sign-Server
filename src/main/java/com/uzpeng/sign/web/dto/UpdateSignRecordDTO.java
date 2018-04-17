package com.uzpeng.sign.web.dto;

/**
 * @author uzpeng on 2018/4/17.
 */
public class UpdateSignRecordDTO {
    private Integer id;
    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
