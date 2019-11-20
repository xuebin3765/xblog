package com.xblog.view.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/tag")
public class TagView {
    /**
     * 新增菜单数据
     * @return view
     */
    @GetMapping("/tagList")
    public String add(){
        return "/admin/tag/tagList";
    }
}
