package com.xblog.repository.sys;


import com.xblog.entity.sys.Menu;
import com.xblog.entity.sys.Navigate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 角色表
 * Created by lovebin on 2017/4/19.
 */
public interface NavigateRepository extends JpaRepository<Navigate, Integer> {

    Navigate findByName(String name);
}
