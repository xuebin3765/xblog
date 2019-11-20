package com.xblog.repository.sys;

import com.xblog.entity.sys.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户
 * Created by lovebin on 2017/4/19.
 */

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Users findUsersByUsername(String username);
}
