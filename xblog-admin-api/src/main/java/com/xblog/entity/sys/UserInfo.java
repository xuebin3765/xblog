package com.xblog.entity.sys;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户详细信息表
 * Created by Administrator on 2017/9/27.
 */
@Entity
@Table
@Data
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

}
