package com.xblog.entity.blog;

import lombok.Data;

import javax.persistence.*;

/**
 * 滚动图
 * Created by Administrator on 2017/10/10.
 */
@Entity
@Table
@Data
public class Banner {
    @Id
    @Column
    private String id;
    @Column
    private String title;
    @Column
    private String imageUrl;
    @Column
    private String url;
    @Column(length = 2)
    private int status; // 0: 不可用，1：可用
    @Column(length = 2)
    private int sort;
    @Column
    private boolean toOther; // true: 新打开窗口，false：当前窗口
}
