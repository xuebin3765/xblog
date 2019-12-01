package com.xblog.service;

import com.xblog.entity.blog.Article;
import com.xblog.entity.blog.ArticleNavigateRel;

import java.util.List;

/**
 * Description: 文章标题关联表
 * Created by Administrator
 * Date 2019/12/1 20:47
 */
public interface ArticleNavigateRelService {
    List<ArticleNavigateRel> findAllByArticleId(String articleId);

    List<ArticleNavigateRel> findAllByNavigateId(String navigateId);

    void deleteAllByArticleId(String articleId);

    void deleteAllByNavigateId(String navigateId);

    ArticleNavigateRel findByNavAndAriId(String navigateId, String articleId);

    List<ArticleNavigateRel> addAll(List<String> navigateIds, String articleId);

    ArticleNavigateRel addOne(String navigateId, String articleId);
}
