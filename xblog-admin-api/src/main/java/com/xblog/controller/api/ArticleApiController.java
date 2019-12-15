package com.xblog.controller.api;

import com.xblog.common.PageResult;
import com.xblog.commons.response.RespEntity;
import com.xblog.entity.blog.Article;
import com.xblog.service.ArticleNavigateRelService;
import com.xblog.service.ArticleService;
import com.xblog.service.ArticleTagRelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * author xuebin@si-tech.com.cn
 * Description 文章增加控制类
 * date 2019/08/04
 */
@RestController
@RequestMapping("/api/article")
@Slf4j
public class ArticleApiController {
    @Resource
    private ArticleService articleService;

    /**
     * 查询所有文章
     * @return RespEntity
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public RespEntity findAll(
            @RequestParam(value = "navId", required = false) String navId,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit
    ){
        log.info("step into findAll(), pageNum: {}, pageSize: {}, key: {}", page, limit, key);
        PageResult<Article> pageResult = articleService.findAll(navId, key, page, limit);
        return RespEntity.success(pageResult.getRows(), pageResult.getCount());
    }

    /**
     * 查询所有文章
     * @return RespEntity
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public RespEntity findById( String id){
        log.info("step into findById(), id: {}", id);
        Article article = articleService.findById(id);
        if (article != null){
            return RespEntity.success(article);
        }else {
            return RespEntity.error("文章不存在或已删除");
        }
    }
}
