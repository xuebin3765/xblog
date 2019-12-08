package com.xblog.repository.sys;

import com.xblog.entity.blog.ArticleNavigateRel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 用户
 * Created by lovebin on 2017/4/19.
 */

public interface NavigateArticleRelRepository extends JpaRepository<ArticleNavigateRel, String> {
    List<ArticleNavigateRel> findAllByArticleId(String articleId);
}
