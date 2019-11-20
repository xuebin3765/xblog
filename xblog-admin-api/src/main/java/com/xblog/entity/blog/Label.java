package com.xblog.entity.blog;

import javax.persistence.*;

/**
 * 标签
 * Created by Administrator on 2017/9/27.
 */
@Entity
@Table
public class Label {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name;

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
}
