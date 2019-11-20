package com.xblog.repository.sys;


import com.xblog.open.entity.sys.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户详细信息
 * Created by lovebin on 2017/4/19.
 */

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

}
