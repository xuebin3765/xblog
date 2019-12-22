package com.xblog.service.impl;

import com.xblog.entity.sys.User;
import com.xblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * desc: 用户service impl
 * author: xuebin3765@163.com
 * date: 2019/12/22
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Override
    public User login(String username, String password) {
        if ("xuebin".equals(username) && "123456".equals(password)){
            User user = new User();
            user.setUsername("xuebin");
            user.setPassword("123456");
            return user;
        }
        return null;
    }

    @Override
    public User register(User user) {
        return null;
    }
}
