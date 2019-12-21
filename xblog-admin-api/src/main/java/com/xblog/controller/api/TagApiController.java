package com.xblog.controller.api;

import com.google.common.collect.Lists;
import com.xblog.common.PageResult;
import com.xblog.commons.response.RespEntity;
import com.xblog.entity.blog.Article;
import com.xblog.entity.sys.Tag;
import com.xblog.service.ArticleService;
import com.xblog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * author xuebin@si-tech.com.cn
 * Description 标签
 * date 2019/08/04
 */
@RestController
@RequestMapping("/api/tag")
@Slf4j
public class TagApiController {
    @Resource
    private TagService tagService;

    /**
     * 查询所有标签
     * @return RespEntity
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public RespEntity findAll(){
        log.info("step into findAll()");
        List tagList = tagService.findAll();
        if (tagList == null)
            tagList = Lists.newArrayList();
        return RespEntity.success(tagList);
    }
}
