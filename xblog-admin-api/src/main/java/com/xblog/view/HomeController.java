package com.xblog.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * desc:
 * author: xuebin3765@163.com
 * date: 2019/08/07
 */
@Controller
@RequestMapping("/view/home")
public class HomeController {

    private String prefix = "portal/";
    @RequestMapping("home")
    public String home(){
        return prefix.concat("home");
    }
}
