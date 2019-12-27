package com.xblog.repository.sys;

import com.xblog.entity.sys.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户
 * Created by lovebin on 2017/4/19.
 */

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUsernameAndPassword(String username, String password);

    User findUserByUsername(String username);
}
