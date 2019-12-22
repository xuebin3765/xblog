package com.xblog.repository.sys;

import com.xblog.entity.sys.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户
 * Created by lovebin on 2017/4/19.
 */

public interface UsersRepository extends JpaRepository<User, Integer> {

    User findUsersByUsername(String username);
}
