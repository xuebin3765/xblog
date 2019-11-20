package com.xblog.view.admin;

import com.xblog.open.common.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/user")
public class UserView extends BaseController {

    /**
     * 新增菜单数据
     * @return view
     */
    @GetMapping("/userList")
    public String add(){
        return "/admin/user/userList";
    }
}
