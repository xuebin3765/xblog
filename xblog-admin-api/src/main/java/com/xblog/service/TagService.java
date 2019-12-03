package com.xblog.service;

import com.xblog.common.PageResult;
import com.xblog.entity.sys.Tag;

/**
 * Description:
 * Created by Administrator
 * Date 2019/11/24 16:05
 */
public interface TagService {

    public Tag add(Tag tag);

    public Tag findById(String id);

    public Tag findByName(String name);

    public PageResult<Tag> findAll(String name, int pageNum, int pageSize);

    public void deleteById(String id);
}
