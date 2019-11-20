package com.xblog.entity.sys;

import lombok.Data;

import javax.persistence.*;

/**
 * 角色表
 * Created by Administrator on 2017/9/27.
 */
@Entity
@Table
@Data
public class Role {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
}
