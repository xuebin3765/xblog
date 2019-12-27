package com.xblog.service;

import com.xblog.entity.sys.User;

/**
 * desc: 用户service
 * author: xuebin3765@163.com
 * date: 2019/12/22
 */
public interface UserService {

    public User login(User user);

    public User register(User user);
}
