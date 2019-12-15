package com.xblog.service;

import com.xblog.common.PageResult;
import com.xblog.entity.blog.Image;

/**
 * desc: 图片文件service
 * author: xuebin3765@163.com
 * date: 2019/12/03
 */
public interface ImageService {

    Image add(Image image);

    Image findById(String id);

    PageResult<Image> findAll(int pageNum, int pageSize);

    void deleteById(String id);

    Image findAllByUrl(String url);

}
