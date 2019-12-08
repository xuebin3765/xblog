package com.xblog.entity.blog;

import lombok.Data;

import javax.persistence.*;

/**
 * desc: 标签和文章关联关系
 *      文章与标签多对多关系
 * author: xuebin3765@163.com
 * date: 2019/11/26
 */
@Data
@Entity
@Table
public class ArticleTagRel {
    @Id
    @Column
    private String id;
    @Column
    private String tagId;
    @Column
    private String articleId;

    public ArticleTagRel(String id, String tagId, String articleId) {
        this.id = id;
        this.tagId = tagId;
        this.articleId = articleId;
    }
}
