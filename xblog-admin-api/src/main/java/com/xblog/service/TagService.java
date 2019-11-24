package com.xblog.service;

import com.xblog.entity.sys.Tag;

import java.util.List;

/**
 * Description:
 * Created by Administrator
 * Date 2019/11/24 16:05
 */
public interface TagService {

    public Tag add(Tag tag);

    public Tag findById(int id);

    public Tag findByName(String name);

    public List<Tag> findAll(int pageNum, int pageSize);

    public void deleteById(int id);
}
