package com.xblog.entity.blog;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 文章分类关联表
 * Created by Administrator on 2017/10/11.
 */
@Data
@Entity
@Table
public class ArticleNavigateRel {
    @Column
    private String articleId;
    @Column
    private String navigateId;
}
