package com.xblog.repository.blog;


import com.xblog.entity.blog.ArticleNavigateRel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 用户文章分类
 * Created by lovebin on 2017/4/19.
 */

public interface ArticleNavigateRelRepository extends JpaRepository<ArticleNavigateRel, String> {

    List<ArticleNavigateRel> findAllByArticleId(String article);

    List<ArticleNavigateRel> findAllByNavigateId(String navigateId);
}
