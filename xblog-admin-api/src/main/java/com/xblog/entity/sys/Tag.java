package com.xblog.entity.sys;

import lombok.Data;

import javax.persistence.*;
import java.lang.annotation.Target;

/**
 * Description: 标签
 * Created by Administrator
 * Date 2019/11/24 13:34
 */
@Entity
@Table
@Data
public class Tag {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String name;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }
}
