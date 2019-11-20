package com.xblog.entity.blog;

import lombok.Data;

import javax.persistence.*;

/**
 * 标签
 * Created by Administrator on 2017/9/27.
 */
@Entity
@Table
@Data
public class Tag {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name;
}
