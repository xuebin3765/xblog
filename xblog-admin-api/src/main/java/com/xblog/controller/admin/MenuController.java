package com.xblog.controller.admin;

import com.commons.validator.ValidResult;
import com.commons.validator.ValidatorFactory;
import com.xblog.open.common.base.BaseController;
import com.xblog.open.common.response.MenuRespCode;
import com.xblog.open.common.response.RespCode;
import com.xblog.open.common.utils.JsonUtil;
import com.xblog.open.entity.sys.Menu;
import com.xblog.open.model.RespEntity;
import com.xblog.open.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {

    @Resource
    private MenuService menuService;

    /**
     * 新增菜单数据
     * @param menu 菜单对象
     * @return view
     */
    @PostMapping("/add")
    public String add(Model model, @RequestBody Menu menu){

        debug("step into add(), menu: {}", JsonUtil.toStringNoRelation(menu));

        String successView = "/admin/menu/list", errorView = "/admin/menu/addMenu";
        String respCode = RespCode.error;

        ValidResult validResult = ValidatorFactory.validBean(menu);
        if (!validResult.isHasErrors()){

            Menu oldMenu = menuService.findByTitleOrUrl(menu.getTitle(), menu.getUrlPath());

            if (null != oldMenu){
                debug("menu exist, title: {}, url: {}", oldMenu.getTitle(), menu.getUrlPath());
                setDataToModelView(model, MenuRespCode.MENU_TITLE_OR_URL_REPEAT, null);
                return errorView;
            }

            menu = menuService.add(menu);

            if (null != menu){
                debug(" return success, view: {}", successView);
                setDataToModelView(model, menu);
                return successView;
            }
            respCode = MenuRespCode.MENU_ADD_ERROR;
        }
        setDataToModelView(model, respCode, validResult.getMapErrors());
        debug(" return error, view: {}", errorView);
        return successView;
    }

    /**
     * 修改
     * @param menu 菜单数据
     * @return update
     */
    @PostMapping("/update")
    public String update(Model model, @RequestBody Menu menu){

        debug("step into update(), menu: {}", JsonUtil.toStringNoRelation(menu));

        String successView = "/admin/menu/list", errorView = "/admin/menu/updateMenu";
        String view;
        String respCode = RespCode.error;

        if (menu != null){
            Menu oldMenu = menuService.findById(menu.getId());
            if (oldMenu != null){
                if (StringUtils.isNotBlank(menu.getTitle()) || StringUtils.isNotBlank(menu.getUrlPath())){
                    // 要修改标题和url
                    Menu oldMenuByTitleOrUrl = menuService.findByTitleOrUrl(menu.getTitle(), menu.getUrlPath());
                    if (null != oldMenuByTitleOrUrl){
                        debug("menu exist, title: {}, url: {}", oldMenu.getTitle(), menu.getUrlPath());
                        setDataToModelView(model, errorView, MenuRespCode.MENU_TITLE_OR_URL_REPEAT, oldMenu);
                        return errorView;
                    }
                    // 设置标题
                    if (StringUtils.isNotBlank(menu.getTitle())){
                        oldMenu.setTitle(menu.getTitle());
                    }
                    // 设置url
                    if (StringUtils.isNotBlank(menu.getUrlPath())){
                        oldMenu.setUrlPath(menu.getUrlPath());
                    }
                }

                // 设置父目录
                if (null != menu.getParentId()){
                    oldMenu.setParentId(menu.getParentId());
                }
                // 设置权限
                if (null != menu.getRole()){
                    oldMenu.setRole(menu.getRole());
                }
                // 设置描述信息
                if (StringUtils.isNotBlank(menu.getDescription())){
                    oldMenu.setDescription(menu.getDescription());
                }
                menu = menuService.add(menu);
                debug(" return success, view: {}", successView);
                setDataToModelView(model, menu);
                view = successView;
                return view;
            }
            respCode = MenuRespCode.MENU_NOT_EXIST;
        }
        view = errorView;
        debug(" return error, view: {}", errorView);
        setDataToModelView(model, respCode);
        return view;
    }

    /**
     * 查询菜单
     * @param parentId 父节点
     * @return menuList
     */
    @GetMapping("/findAllMenu")
    public String findAllMenu(
            Model model,
            @RequestParam(required = false, defaultValue = "-1") Integer parentId
    ){
        debug("step into update(), menu: {}", JsonUtil.toStringNoRelation(parentId));
        String successView = "/admin/index";
        String respCode = MenuRespCode.error;
        List<Menu> menuList = null;
        if (parentId < -1){
            respCode = MenuRespCode.MENU_PARENT_ID_ERROR;
            debug("parent id Illegal");
        }else {
            debug("get all menu ");
            menuList = menuService.findAllMenu(parentId);
            respCode = MenuRespCode.success;
        }
        setDataToModelView(model, respCode, menuList);
        debug("step out findAllMenu");
        return successView;
    }

    /**
     * 查询菜单
     * @param parentId 父节点
     * @return menuList
     */
    @GetMapping("/findAllMenuJson")
    public RespEntity findAllMenuJson(
            @RequestParam(required = false, defaultValue = "-1") Integer parentId
    ){
        debug("step into findAllMenuJson(), menu: {}", JsonUtil.toStringNoRelation(parentId));
        String successView = "/admin/index";
        RespEntity respEntity = new RespEntity();
        String respCode;
        List<Menu> menuList = null;
        if (parentId < -1){
            respCode = MenuRespCode.MENU_PARENT_ID_ERROR;
            debug("parent id Illegal");
        }else {
            debug("get all menu ");
            menuList = menuService.findAllMenu(parentId);
            respCode = MenuRespCode.success;
        }
        respEntity.setRespCode(respCode);
        respEntity.setData(menuList);
        debug("step out findAllMenu");
        return respEntity;
    }


}
