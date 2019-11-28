package com.xblog.entity.blog;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * desc: 图片信息存储类
 * author: xuebin3765@163.com
 * date: 2019/11/27
 */
@Data
@Entity
@Table
public class Image {
    @Id
    @Column
    private long id;
    @Column
    private String url;

    public Image() {
    }

    public Image(String url) {
        this.url = url;
    }
}
