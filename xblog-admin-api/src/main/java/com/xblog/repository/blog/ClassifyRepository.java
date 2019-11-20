package com.xblog.repository.blog;


import com.xblog.open.entity.blog.Classify;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户文章分类
 * Created by lovebin on 2017/4/19.
 */

public interface ClassifyRepository extends JpaRepository<Classify, Integer> {

}
