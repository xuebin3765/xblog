package com.xblog.service;

import com.xblog.common.PageResult;
import com.xblog.entity.blog.Image;

/**
 * desc: 图片文件service
 * author: xuebin3765@163.com
 * date: 2019/12/03
 */
public interface ImageService {

    public Image add(Image image);

    public Image findById(String id);

    public PageResult<Image> findAll(int pageNum, int pageSize);

    public void deleteById(String id);

}
