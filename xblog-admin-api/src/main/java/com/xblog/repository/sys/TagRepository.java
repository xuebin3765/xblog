package com.xblog.repository.sys;


import com.xblog.entity.sys.Menu;
import com.xblog.entity.sys.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 角色表
 * Created by lovebin on 2017/4/19.
 */
public interface TagRepository extends JpaRepository<Tag, String> {

    Tag findByName(String name);

    List<Tag> findAll();
}
