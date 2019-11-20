package com.xblog.entity.blog;

import javax.persistence.*;

/**
 * 资源分享表，提供下载链接
 * Created by Administrator on 2017/9/27.
 */
@Entity
@Table
public class Source {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private int userId;
    @Column
    private String title;
    @Column
    private int type;
    @Column
    private String label;         // 标签，多个将id拼接起来
    @Column
    private String url;
    @Column
    private String imgUrl;   // 封面图
    @Column
    private int viewCount;  // 浏览次数
    @Column
    private int loadCount;  // 下载次数
    @Column
    private int integral; // 下载资源所需积分，默认是0分，免费

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getLoadCount() {
        return loadCount;
    }

    public void setLoadCount(int loadCount) {
        this.loadCount = loadCount;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
