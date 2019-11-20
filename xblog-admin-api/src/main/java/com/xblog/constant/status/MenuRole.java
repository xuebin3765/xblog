package com.xblog.constant.status;

/**
 * desc: 菜单权限常量
 * author: xuebin3765@163.com
 * date: 2019/11/15
 */
public enum MenuRole {
    PUBLIC(0, "all"),
    ADMIN(1, "admin"),
    PARTAL(2, "portal");


    private int role;
    private String desc;

    MenuRole(int role, String desc) {
        this.role = role;
        this.desc = desc;
    }

    public int getRole() {
        return role;
    }

    public String getDesc() {
        return desc;
    }
}
