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
public class Users {

    private static final int STATUS = 0;
    private static final String DESCRIPTION = "description";
    private static final String PIC = "pic";

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String pic;
    @Column
    private String nickname;
    @Column
    private int status;
    @Column
    private String description;

}
