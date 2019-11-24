package com.xblog.service.impl;

import com.xblog.entity.blog.Article;
import com.xblog.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * desc:
 * author: xuebin3765@163.com
 * date: 2019/08/04
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Override
    public Article delete(Integer id) {
        return null;
    }
}
