package com.xblog.controller.admin;

import com.xblog.open.common.base.BaseController;
import com.xblog.open.entity.sys.Menu;
import com.xblog.open.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * 首页入口
 * @Author guodandan
 * @Date 2018/9/10 22:18
 */
@Controller
@RequestMapping("/admin/index")
public class IndexController extends BaseController {

    @Resource
    private MenuService menuService;

    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public String index(Model model){
        String successView = "admin/home";
        List<Menu> menuList = menuService.findAllMenu(-1);
        setDataToModelView(model, menuList);
        return successView;
    }

    @RequestMapping(value = "/home" , method = RequestMethod.GET)
    public String home(){
        return "left";
    }
}
