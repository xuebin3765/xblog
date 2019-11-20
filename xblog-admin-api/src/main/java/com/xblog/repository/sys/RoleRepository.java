package com.xblog.repository.sys;

import com.xblog.entity.sys.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 角色表
 * Created by lovebin on 2017/4/19.
 */

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
