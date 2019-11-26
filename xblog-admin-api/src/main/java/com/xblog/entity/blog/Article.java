package com.xblog.entity.blog;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private Integer userId;
    @Column
    private Integer menuId;        // 菜单id
    @Column
    private String title;          // 标题
    @Column
    private String imgUrl;         // 封面图
    @Column
    private int type;              // 原创，转载，翻译
    @Column
    private String loadUrl;        // 转载的url
    @Column
    private String decoration;     // 描述，不填获取正文前半部分内容
    @Column
    private String context;        // 正文
    @Column
    private Date created;          // 创建时间
    @Column
    private Date modify;           // 修改时间
    @Column
    private int status;            // 状态
    @Column
    private boolean stick;         // 文章置顶
    @Column
    private int hot;               // 是否热门推荐。0：默认，1：热门
}
