package com.xblog.entity.blog;

import javax.persistence.*;

/**
 * 滚动图
 * Created by Administrator on 2017/10/10.
 */
@Entity
@Table
public class Banner {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String title;
    @Column
    private String imageUrl;
    @Column
    private int appId;
    @Column
    private int type; // 什么位置的滚动图；1111
    @Column
    private int objectId;
    @Column
    private String url;
    @Column
    private boolean innerUrl;  // true：内部链接；flase：外部链接，通过url访问

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isInnerUrl() {
        return innerUrl;
    }

    public void setInnerUrl(boolean innerUrl) {
        this.innerUrl = innerUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }
}
