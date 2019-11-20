package com.xblog.entity.sys;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户详细信息表
 * Created by Administrator on 2017/9/27.
 */
@Entity
@Table
public class UserInfo {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private Integer userId;
    @Column
    private boolean openBlog;       // 是否开通博客
    @Column
    private int blogId;              // 博客Id，一个用户一个博客
    @Column
    private boolean sex;             // 性别
    @Column
    private String birthday;         // 生日
    @Column
    private String email;
    @Column
    private int loginCount;
    @Column
    private String lastLoginIp;
    @Column
    private Date lastLoginDate;
    @Column
    private String qq;
    @Column
    private String weiChat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isOpenBlog() {
        return openBlog;
    }

    public void setOpenBlog(boolean openBlog) {
        this.openBlog = openBlog;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeiChat() {
        return weiChat;
    }

    public void setWeiChat(String weiChat) {
        this.weiChat = weiChat;
    }
}
