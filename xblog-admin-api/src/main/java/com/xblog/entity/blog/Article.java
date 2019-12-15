package com.xblog.entity.blog;

import com.xblog.entity.sys.Navigate;
import com.xblog.entity.sys.Tag;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 文章
 * Created by Administrator on 2017/9/27.
 */
@Data
@Entity
@Table
public class Article {
    @Id
    @Column
    private String id;
    @Column
    private Integer userId;
    @Column
    private String title;          // 标题
    @Column
    private String imgUrl;         // 封面图
    @Column
    private int type;              // 原创，转载，翻译
    @Column
    private String loadUrl;        // 转载的url,原文地址
    @Column
    private String decoration;     // 描述，不填获取正文前半部分内容
    @Column
    private String context;        // 正文
    @Column
    private long created;          // 创建时间
    @Column
    private long modify;           // 修改时间
    @Column
    private int status;            // 状态 -1，删除； 0，草稿； 1，发布
    @Column
    private boolean stick;         // 文章置顶
    // 文章分类id
    @Transient
    private List<String> navigateIds;
    // 文章标签id
    @Transient
    private List<String> tagIds;
    @Transient
    private List<Navigate> navigateList;
    @Transient
    private List<Tag> tagList;
    @Transient
    private String userName;
    @Transient
    private int pageView; // 阅读量
    @Transient
    private int commentNumber; // 阅读量
    @Transient
    private String typeName = "热点资讯";
    @Transient
    private Navigate navigate;
}
