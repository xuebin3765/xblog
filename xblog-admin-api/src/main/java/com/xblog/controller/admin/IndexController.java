package com.xblog.controller.admin;

import com.xblog.commons.controller.BaseController;
import com.xblog.commons.response.RespEntity;
import com.xblog.entity.sys.Menu;
import com.xblog.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * 首页入口
 * Author guodandan
 * Date 2018/9/10 22:18
 */
@Controller
@RequestMapping("/admin/index")
public class IndexController extends BaseController {

    @Resource
    private MenuService menuService;

    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public RespEntity index(){
        List<Menu> menuList = menuService.findAllMenu(-1);
        return RespEntity.success(menuList);
    }

    @RequestMapping(value = "/home" , method = RequestMethod.GET)
    public String home(){
        return "left";
    }
}
