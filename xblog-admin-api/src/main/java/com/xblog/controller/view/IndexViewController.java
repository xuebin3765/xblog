package com.xblog.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description: 首页
 * Created by Administrator
 * Date 2019/12/27 22:41
 */
@Slf4j
@Controller
@RequestMapping("/")
public class IndexViewController {

    @RequestMapping("index")
    public String index(){
        return "index.html";
    }

    @RequestMapping("sidebar")
    public String sidebar(){
        return "sidebar.html";
    }

    @RequestMapping("footer")
    public String footer(){
        return "footer.html";
    }
}
