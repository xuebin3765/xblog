package com.xblog.controller;

import com.xblog.commons.response.RespEntity;
import com.xblog.constant.Constant;
import com.xblog.entity.sys.User;
import com.xblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * desc: 登录、登出、注册、忘记密码的接口
 * author: xuebin3765@163.com
 * date: 2019/12/22
 */
@Slf4j
@RestController
@RequestMapping("/")
public class LoginController {

    @Resource
    private HttpSession httpSession;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RespEntity login(@RequestBody User user){
        User users = userService.login(null, null);
        if (users != null){
            httpSession.setAttribute(Constant.SESSION_USER, users);
            return RespEntity.success(user, "登录成功");
        }else {
            return RespEntity.error("登录失败");
        }
    }
}
