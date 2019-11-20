package com.xblog.repository.sys;

import com.xblog.entity.sys.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户权限
 * Created by lovebin on 2017/4/19.
 */

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

}
