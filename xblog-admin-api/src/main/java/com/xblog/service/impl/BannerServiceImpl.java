package com.xblog.service.impl;

import com.google.common.collect.Lists;
import com.xblog.entity.blog.Banner;
import com.xblog.repository.blog.BannerRepository;
import com.xblog.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * desc: 滚动图
 * author: xuebin3765@163.com
 * date: 2019/12/16
 */
@Service
@Transactional
@Slf4j
public class BannerServiceImpl implements BannerService {

    @Resource
    private BannerRepository bannerRepository;
    @Override
    public List<Banner> banners() {
        return bannerRepository.findAllByStatusOrderBySortAsc(1);
    }
}
