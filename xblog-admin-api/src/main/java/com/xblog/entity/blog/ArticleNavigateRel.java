package com.xblog.entity.blog;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * 文章分类关联表
 * Created by Administrator on 2017/10/11.
 */
@Data
@Entity
@Table
public class ArticleNavigateRel {
    @Id
    @Column
    private String id;
    @Column
    private String articleId;
    @Column
    private String navigateId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleNavigateRel that = (ArticleNavigateRel) o;
        return articleId.equals(that.articleId) &&
                navigateId.equals(that.navigateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, navigateId);
    }

    public ArticleNavigateRel() {}

    public ArticleNavigateRel(String id, String articleId, String navigateId) {
        this.id = id;
        this.articleId = articleId;
        this.navigateId = navigateId;
    }
}
