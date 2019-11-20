package com.xblog.service;

import com.xblog.open.entity.sys.Menu;

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
//
//    /**
//     * 获取当前菜单的所有子菜单
//     * @param parentId
//     * @return
//     */
//    public List<Menu> findAllByParent(int parentId);
//
//    public List<Menu> findAll();
//
//    public List<Menu> findTopMenu();
//
//    public List<Menu> findAllPublic();
}
