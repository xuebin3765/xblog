package com.xblog.service.impl;

import com.google.common.base.Predicate;
import com.xblog.controller.TagController;
import com.xblog.entity.sys.Tag;
import com.xblog.repository.sys.TagRepository;
import com.xblog.service.TagService;
import org.apache.commons.lang3.StringUtils;
import org.omg.IOP.TAG_CODE_SETS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by Administrator
 * Date 2019/11/24 16:06
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {
    private Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

    @Resource
    private TagRepository tagRepository;

    @Override
    public Tag add(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag findById(int id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public List<Tag> findAll(int pageNum, int pageSize) {
        return tagRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        tagRepository.deleteById(id);
    }
}
