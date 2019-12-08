package com.xblog.service;

import com.xblog.entity.blog.Article;

/**
 * desc:
 * author: xuebin3765@163.com
 * date: 2019/08/04
 */
public interface ArticleService {
    void delete(String id);

    Article add(Article article);

    Article findById(String id);

    Article update(Article article);
}
