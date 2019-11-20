package com.xblog.repository.blog;


import com.xblog.open.entity.blog.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 评论
 * Created by lovebin on 2017/4/19.
 */

public interface CommentRepository extends JpaRepository<Article, Integer> {

}
