package com.xblog.entity.sys;

import lombok.Data;

import javax.persistence.*;

/**
 * 用户表
 * Created by Administrator on 2017/9/27.
 */
@Entity
@Table
@Data
public class User {

    @Id
    @Column
    private String id;
    @Column
    private String username;
    @Column
    private String password; // 密码
    @Column
    private String pic; // 头像
    @Column
    private String nickname;
    @Column
    private int status;

}
