package com.xblog.entity.blog;

import javax.persistence.*;
import java.util.Date;

/**
 * 评论表
 * Created by Administrator on 2017/9/27.
 */
@Entity
@Table
public class Comment {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private int userId;   // 评论用户id
    @Column
    private int beUserId; // 被评论用户id
    @Column
    private int articleId;// 文章id
    @Column
    private int state;  // 评论状态
    @Column
    private String context;
    @Column
    private Date created;
    @Column
    private int type ; // 1:文章评论，2：资源评论

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

    public int getBeUserId() {
        return beUserId;
    }

    public void setBeUserId(int beUserId) {
        this.beUserId = beUserId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
