package com.xblog.repository.sys;

import com.xblog.entity.blog.ArticleTagRel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 用户
 * Created by lovebin on 2017/4/19.
 */

public interface ArticleTagRelRepository extends JpaRepository<ArticleTagRel, String> {
    List<ArticleTagRel> findAllByArticleId(String articleId);
}
