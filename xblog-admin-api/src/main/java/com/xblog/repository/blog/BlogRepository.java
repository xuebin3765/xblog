package com.xblog.repository.blog;


import com.xblog.open.entity.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 博客
 * Created by lovebin on 2017/4/19.
 */

public interface BlogRepository extends JpaRepository<Blog, Integer> {

}
