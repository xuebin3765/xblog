package com.xblog.service;

import com.xblog.entity.sys.Menu;

import java.util.List;

/**
 * desc:
 * author: xuebin3765@163.com
 * date: 2019/11/15
 */
public interface MenuService {

    /**
     * 增加菜单
     * @param menu 菜单对象
     * @return 增加后的菜单
     */
    public Menu add(Menu menu);

    Menu findByTitleOrUrl(String title, String url);

    Menu findById(int id);

    List<Menu> findAllMenu(Integer parentId);
}
