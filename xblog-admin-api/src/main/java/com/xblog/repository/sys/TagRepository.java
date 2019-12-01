package com.xblog.repository.sys;


import com.xblog.entity.blog.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 角色表
 * Created by lovebin on 2017/4/19.
 */
public interface TagRepository extends JpaRepository<Tag, String> {

    Tag findByName(String name);

    List<Tag> findAll();
}
