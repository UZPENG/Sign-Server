package com.uzpeng.sign.web.dto;

/**
 * @author uzpeng on 2018/4/22.
 */
public class StudentChangePasswordDTO {
    private String openId;
    private String newPassword;
    private String oldPassword;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
