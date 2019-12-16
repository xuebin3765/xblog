package com.xblog.repository.blog;


import com.xblog.entity.blog.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * banner图
 * Created by lovebin on 2017/4/19.
 */

public interface BannerRepository extends JpaRepository<Banner, String> , JpaSpecificationExecutor<Banner>{
    List<Banner> findAllByStatusOrderBySortAsc(int status);
}
