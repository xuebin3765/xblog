package com.xblog.repository.sys;


import com.xblog.entity.sys.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 角色表
 * Created by lovebin on 2017/4/19.
 */
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    List<Menu> findAllByTitleOrUrlPathOrderByCreateTimeAsc(String title, String url);

    List<Menu> findAllByParentId(Integer parentId);
}
