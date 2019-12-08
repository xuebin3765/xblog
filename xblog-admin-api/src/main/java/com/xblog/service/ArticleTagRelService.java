package com.xblog.service;

import com.xblog.entity.blog.ArticleTagRel;

import java.util.List;

/**
 * Description:
 * Created by Administrator
 * Date 2019/12/8 21:15
 */
public interface ArticleTagRelService {
    List<ArticleTagRel> findAllByArticleId(String articleId);

    List<ArticleTagRel> addAll(List<String> articleTagRelList, String articleId);
}
