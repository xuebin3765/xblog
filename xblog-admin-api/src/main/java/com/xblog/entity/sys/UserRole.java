package com.xblog.entity.sys;

import lombok.Data;

import javax.persistence.*;

/**
 * 用户角色表
 * Created by Administrator on 2017/9/27.
 */
@Entity
@Table
@Data
public class UserRole {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private Integer userId;
    @Column
    private Integer roleId;

}
