package com.xblog.service.impl;

import com.xblog.commons.utils.JsonUtil;
import com.xblog.entity.sys.User;
import com.xblog.repository.sys.UserRepository;
import com.xblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private UserRepository userRepository;
    @Override
    public User login(User user) {
        log.info("step into login, user: {}", JsonUtil.toString(user));
        if (user == null)
            return null;
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()))
            return null;
        return userRepository.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public User register(User user) {
        if (user == null)
            return null;
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()))
            return null;
        return userRepository.save(user);
    }
}
