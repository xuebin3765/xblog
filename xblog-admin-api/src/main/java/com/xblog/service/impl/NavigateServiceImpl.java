package com.xblog.service.impl;

import com.xblog.controller.TagController;
import com.xblog.entity.sys.Navigate;
import com.xblog.entity.sys.Tag;
import com.xblog.repository.sys.NavigateRepository;
import com.xblog.repository.sys.TagRepository;
import com.xblog.service.NavigateService;
import com.xblog.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Description: 导航service
 * Created by Administrator
 * Date 2019/11/24 16:06
 */
@Service
@Transactional
public class NavigateServiceImpl implements NavigateService {
    private Logger logger = LoggerFactory.getLogger(NavigateServiceImpl.class);

    @Resource
    private NavigateRepository navigateRepository;

    @Override
    public Navigate add(Navigate navigate) {
        return navigateRepository.save(navigate);
    }

    @Override
    public Navigate findById(int id) {
        return navigateRepository.findById(id).orElse(null);
    }

    @Override
    public Navigate findByName(String name) {
        return navigateRepository.findByName(name);
    }

    @Override
    public List<Navigate> findAll(int pageNum, int pageSize) {
        return navigateRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        navigateRepository.deleteById(id);
    }
}
