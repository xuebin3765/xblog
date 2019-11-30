package com.xblog.controller;

import com.xblog.common.enums.ArticleEnum;
import com.xblog.commons.response.RespEntity;
import com.xblog.commons.utils.JsonUtil;
import com.xblog.entity.blog.Article;
import com.xblog.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xuebin@si-tech.com.cn
 * @Description
 * @date 2019/08/04
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Resource
    private ArticleService articleService;

    @ResponseBody
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    public RespEntity addOrUpdate(@RequestBody Article article){
        logger.info("step into addOrUpdate, article: {}", JsonUtil.toString(article));
        if (article == null){
            return RespEntity.error("提交数据不能为空");
        }

        Article articleOld = articleService.findById(article.getImgUrl());
        if (null == article){
            return RespEntity.error("add article failed !");
        }
        return RespEntity.success(article,"success");
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE , MediaType.TEXT_PLAIN_VALUE})
    public RespEntity delete(@RequestParam("id") String id){
        logger.info("step into delete, id: {}", id);

        Article article = articleService.findById(id);
        if (article == null){
            return RespEntity.error("文章不存在");
        }
        // 设置状态为删除状态，即：status = -1
        article.setStatus(ArticleEnum.DELETE.getValue());

        article = articleService.update(article);
        if (null == article){
            return RespEntity.error("删除失败");
        }
        return RespEntity.success(article, "success");
    }
}
