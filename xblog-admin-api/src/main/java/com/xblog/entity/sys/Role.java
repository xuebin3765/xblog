package com.xblog.entity.sys;

import javax.persistence.*;

/**
 * 角色表
 * Created by Administrator on 2017/9/27.
 */
@Entity
@Table
public class Role {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
