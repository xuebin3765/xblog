package com.xblog.service;

import com.xblog.common.PageResult;
import com.xblog.entity.sys.Navigate;

import java.util.List;

/**
 * Description:
 * Created by Administrator
 * Date 2019/11/24 16:05
 */
public interface NavigateService {

    public Navigate add(Navigate navigate);

    public Navigate findById(String id);

    public Navigate findByName(String name);

    public PageResult<Navigate> findAll(String name, int pageNum, int pageSize);

    public void deleteById(String id);

    List<Navigate> findAllNavigate();

    List<Navigate> findallByArticleId(String articleId);
}
