package com.xblog.controller.admin;

import com.xblog.commons.controller.BaseController;
import com.xblog.commons.response.MenuRespCode;
import com.xblog.commons.response.RespEntity;
import com.xblog.commons.utils.JsonUtil;
import com.xblog.commons.validator.ValidResult;
import com.xblog.commons.validator.ValidatorFactory;
import com.xblog.entity.sys.Menu;
import com.xblog.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Resource
    private MenuService menuService;

    /**
     * 新增菜单数据
     * @param menu 菜单对象
     * @return view
     */
    @PostMapping("/add")
    public RespEntity add(@RequestBody Menu menu){

        logger.debug("step into add(), menu: {}", JsonUtil.toString(menu));
        ValidResult validResult = ValidatorFactory.validBean(menu);
        if (!validResult.isHasErrors()){

            Menu oldMenu = menuService.findByTitleOrUrl(menu.getTitle(), menu.getUrlPath());

            if (null != oldMenu){
                logger.debug("menu exist, title: {}, url: {}", oldMenu.getTitle(), menu.getUrlPath());
                return RespEntity.error(MenuRespCode.MENU_TITLE_OR_URL_REPEAT);
            }

            menu = menuService.add(menu);

            if (null != menu){
                logger.debug(" return success, menu: {}", JsonUtil.toString(menu));
                return RespEntity.success(menu);
            }
        }
        return RespEntity.error();
    }


    /**
     * 修改
     * @param menu 菜单数据
     * @return update
     */
    @PostMapping("/update")
    public RespEntity update(@RequestBody Menu menu){

        logger.debug("step into update(), menu: {}", JsonUtil.toString(menu));


        if (menu != null){
            Menu oldMenu = menuService.findById(menu.getId());
            if (oldMenu != null){
                if (StringUtils.isNotBlank(menu.getTitle()) || StringUtils.isNotBlank(menu.getUrlPath())){
                    // 要修改标题和url
                    Menu oldMenuByTitleOrUrl = menuService.findByTitleOrUrl(menu.getTitle(), menu.getUrlPath());
                    if (null != oldMenuByTitleOrUrl){
                        logger.debug("menu exist, title: {}, url: {}", oldMenu.getTitle(), menu.getUrlPath());
                        return RespEntity.error(MenuRespCode.MENU_TITLE_OR_URL_REPEAT);
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
                logger.debug(" return success, menu: {}", JsonUtil.toString(menu));
                return RespEntity.success(menu);
            }
        }
        return RespEntity.error(MenuRespCode.MENU_NOT_EXIST);
    }

    /**
     * 查询菜单
     * @param parentId 父节点
     * @return menuList
     */
    @GetMapping("/findAllMenu")
    public RespEntity findAllMenu(
            @RequestParam(required = false, defaultValue = "-1") Integer parentId
    ){
        logger.debug("step into update(), menu: {}", parentId);
        if (parentId < -1){
            logger.debug("parent id Illegal");
            return RespEntity.error(MenuRespCode.MENU_PARENT_ID_ERROR);
        }else {
            logger.debug("get all menu ");
            List<Menu> menuList = menuService.findAllMenu(parentId);
            return RespEntity.success(menuList);
        }

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
        logger.debug("step into findAllMenuJson(), menu: {}", parentId);
        RespEntity respEntity = new RespEntity();
        String respCode;
        List<Menu> menuList = null;
        if (parentId < -1){
            respCode = MenuRespCode.MENU_PARENT_ID_ERROR;
            logger.debug("parent id Illegal");
        }else {
            logger.debug("get all menu ");
            menuList = menuService.findAllMenu(parentId);
            respCode = MenuRespCode.success;
        }
        respEntity.setRespCode(respCode);
        respEntity.setData(menuList);
        logger.debug("step out findAllMenu");
        return respEntity;
    }


}
