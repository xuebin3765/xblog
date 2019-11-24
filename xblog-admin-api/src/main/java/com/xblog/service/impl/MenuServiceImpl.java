package com.xblog.service.impl;

import com.xblog.common.utils.MenuTree;
import com.xblog.entity.sys.Menu;
import com.xblog.repository.sys.MenuRepository;
import com.xblog.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * desc: 菜单操作工具类
 * author: xuebin3765@163.com
 * date: 2019/11/15
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuRepository menuRepository;

    /**
     * 新增菜单
     * @param menu 菜单对象
     * @return saved menu
     */
    @Override
    public Menu add(Menu menu) {
        if (null == menu)
            return null;
        menu.setCreateTime(System.currentTimeMillis());
        return menuRepository.save(menu);
    }

    /**
     * 查询相同标题或者url的菜单是否存在
     * @param title 菜单标题
     * @param url 菜单url
     * @return menu
     */
    @Override
    public Menu findByTitleOrUrl(String title, String url) {
        List<Menu> menuList = menuRepository.findAllByTitleOrUrlPathOrderByCreateTimeAsc(title, url);
        if (null != menuList && menuList.size() > 0){
            return menuList.get(0);
        }
        return null;
    }

    @Override
    public Menu findById(int id) {
        return menuRepository.findById(id).orElse(null);
    }

    @Override
    public List<Menu> findAllMenu(Integer parentId) {
        List<Menu> menuList = null;
        if (-1 == parentId){
            // 查询所有
            menuList = menuRepository.findAll();
        }else {
            menuList = menuRepository.findAllByParentId(parentId);
        }
        if (menuList != null && menuList.size() > 0){
            // 构造菜单树
            MenuTree menuTree = new MenuTree(menuList);
            return menuTree.buildTree();
        }
        return null;
    }
}
