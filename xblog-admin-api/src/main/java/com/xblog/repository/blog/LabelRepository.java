package com.xblog.repository.blog;


import com.xblog.open.entity.blog.Label;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 文章标签
 * Created by lovebin on 2017/4/19.
 */

public interface LabelRepository extends JpaRepository<Label, Integer> {

}
