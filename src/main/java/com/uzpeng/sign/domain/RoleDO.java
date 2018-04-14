package com.uzpeng.sign.domain;

/**
 * @author serverliu on 2018/4/11.
 */
public class RoleDO {
    private Integer role_id;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getRoleId() {
        return role_id;
    }

    public void setRoleId(Integer roleId) {
        this.role_id = roleId;
    }
}
