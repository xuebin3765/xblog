package com.xblog.service.impl;

import com.xblog.common.PageResult;
import com.xblog.commons.utils.JsonUtil;
import com.xblog.commons.utils.SnowflakeUUIDUtil;
import com.xblog.entity.blog.Image;
import com.xblog.repository.blog.ImageRepository;
import com.xblog.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;

/**
 * desc:
 * author: xuebin3765@163.com
 * date: 2019/12/03
 */
@Service
@Transactional
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Resource
    private ImageRepository imageRepository;


    @Override
    public Image add(Image image) {
        log.info("step into add image, image: {}", JsonUtil.toString(image));
        if (image == null){
            return null;
        }
        Image imageOld = findAllByUrl(image.getUrl());
        if ( imageOld != null) return imageOld;
        image.setId(SnowflakeUUIDUtil.getUuid());
        return imageRepository.save(image);
    }

    @Override
    public Image findById(String id) {
        return null;
    }

    @Override
    public PageResult<Image> findAll(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Image findAllByUrl(String url) {
        List<Image> imageList = imageRepository.findAllByUrl(url);
        if (imageList != null && imageList.size() > 0)
            return imageList.get(0);
        return null;
    }
}
