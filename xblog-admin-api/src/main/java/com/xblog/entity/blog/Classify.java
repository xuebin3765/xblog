package com.xblog.entity.blog;

import javax.persistence.*;

/**
 * 用户文章分类，分类和用户相关
 * Created by Administrator on 2017/9/27.
 */
@Entity
@Table
public class Classify {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name; // 分类名称
    @Column
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
