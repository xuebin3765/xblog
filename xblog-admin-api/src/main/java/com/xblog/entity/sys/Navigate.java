package com.xblog.entity.sys;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * Description: 页面导航
 * Created by Administrator
 * Date 2019/11/26 21:49
 */
@Data
@Entity
@Table
public class Navigate {
    @Id
    @Column
    private String id;
    @Column
    private String name;
    @Column
    private String url;
    @Column
    private String parentId;
    @Column
    private int sort; // 排序字段

    @Transient
    private String parentName;

    public Navigate() {
    }

    public Navigate(String name, String url, String id) {
        this.name = name;
        this.url = url;
        this.id = id;
    }
}
