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
public class TagArticleRel {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private int tagId;
    @Column
    private int articleId;
}
