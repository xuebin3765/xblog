package com.xblog.common.utils;

import com.google.common.collect.Lists;
import com.xblog.entity.sys.Menu;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * desc: 构建菜单树
 * author: xuebin3765@163.com
 * date: 2019/11/16
 */
public class MenuTree {
    private List<Menu> menuAllList = Lists.newArrayList();

    public MenuTree(List<Menu> menuList) {
        this.menuAllList = menuList;
    }

    public List<Menu> buildTree(){
        List<Menu> treeMenu = Lists.newArrayList();
        // 遍历所有的顶级节点，查找子节点
        for (Menu menu: buildRootTree()) {
            menu = buildChildTree(menu);
            treeMenu.add(menu);
        }
        return treeMenu;
    }

    /**
     * 查找当前节点的所有子节点
     * @param rootMenu
     * @return
     */
    public Menu buildChildTree(Menu rootMenu){
        List<Menu> childMenuList = Lists.newArrayList();
        for (Menu menu: menuAllList) {
            if (menu.getParentId() == rootMenu.getId()){
                childMenuList.add(buildChildTree(menu));
            }
        }
        rootMenu.setChildren(childMenuList);
        return rootMenu;
    }

    /**
     * 构建目录树根节点，也就是找出所有顶级目录
     * @return
     */
    public List<Menu> buildRootTree(){
        return Optional.ofNullable(menuAllList).orElse(Lists.newArrayList())
                .stream()
                .filter(menu -> menu.getParentId() == 0)
                .collect(Collectors.toList());
    }
}
