package com.xblog.service;

import com.xblog.entity.sys.Navigate;
import com.xblog.entity.sys.Tag;

import java.util.List;

/**
 * Description:
 * Created by Administrator
 * Date 2019/11/24 16:05
 */
public interface NavigateService {

    public Navigate add(Navigate navigate);

    public Navigate findById(int id);

    public Navigate findByName(String name);

    public List<Navigate> findAll(int pageNum, int pageSize);

    public void deleteById(int id);
}
