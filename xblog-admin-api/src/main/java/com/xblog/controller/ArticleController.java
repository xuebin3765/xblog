package com.xblog.controller;

import com.xblog.common.PageResult;
import com.xblog.common.enums.ArticleEnum;
import com.xblog.commons.response.RespEntity;
import com.xblog.commons.utils.JsonUtil;
import com.xblog.commons.validator.ValidResult;
import com.xblog.commons.validator.ValidatorFactory;
import com.xblog.entity.blog.Article;
import com.xblog.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * author xuebin@si-tech.com.cn
 * Description 文章增加控制类
 * date 2019/08/04
 */
@RestController
@RequestMapping("/admin/article")
@Slf4j
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private ArticleNavigateRelService navigateRelService;
    @Resource
    private ArticleTagRelService tagRelService;

    /**
     * 查询所有标签
     * @return RespEntity
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public RespEntity findAll(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit
    ){
        log.info("step into findAll(), pageNum: {}, pageSize: {}, key: {}", page, limit, key);
        PageResult<Article> pageResult = articleService.findAll(key, page, limit);
        return RespEntity.success(pageResult.getRows(), pageResult.getCount());
    }

    @ResponseBody
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    public RespEntity addOrUpdate(@RequestBody Article article){
        log.info("step into addOrUpdate, article: {}", JsonUtil.toString(article));
        if (article == null){
            return RespEntity.error("提交数据不能为空");
        }
        Article articleOld = articleService.findById(article.getImgUrl());

        if (null == articleOld){
            // 新增，关键参数验证
//            ValidResult validResult = ValidatorFactory.validBean(article);
//            if (validResult.isHasErrors()){
//                return RespEntity.error("参数不合法！", validResult.getErrorMsgs());
//            }
            articleOld = articleService.add(article);
            if (null == articleOld){
                return RespEntity.error("添加文章失败");
            }
        }else {
            // 修改，对某些字段进行验证修改
            // 文章标题
            if (StringUtils.isNotBlank(article.getTitle())){
                articleOld.setTitle(article.getTitle());
            }
            // 文章摘要
            if (StringUtils.isNotBlank(article.getDecoration())){
                articleOld.setDecoration(article.getDecoration());
            }
            // 文章内容
            if (StringUtils.isNotBlank(article.getContext())){
                articleOld.setContext(article.getContext());
            }
            articleOld = articleService.add(article);
        }
        if (articleOld != null){
            // 文章分类
            if (article.getNavigateIds() != null && article.getNavigateIds().size() > 0){
                log.info("保存相关关联的分类目录");
                navigateRelService.addAll(article.getNavigateIds(), article.getId());

            }
            // 文章标签
            if (article.getTagIds() != null && article.getTagIds().size() > 0){
                log.info("保存文章相关的标签");
                tagRelService.addAll(article.getTagIds(), article.getId());
            }
            return RespEntity.success(article,"success");
        }else {
            return RespEntity.error("添加文章失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE , MediaType.TEXT_PLAIN_VALUE})
    public RespEntity delete(@RequestParam("id") String id){
        log.info("step into delete, id: {}", id);

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

    /**
     * 删除
     * @return RespEntity
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.GET)
    public RespEntity deleteBatch(@RequestParam(value = "ids[]") String[] ids){
        log.info("step into NavigateController delete(), ids: {}", ids);
        if (ids != null && ids.length > 0){
            for (String id: ids) {
                Article article = articleService.findById(id);
                if (article != null){
                    // 设置状态为删除状态，即：status = -1
                    article.setStatus(ArticleEnum.DELETE.getValue());
                    articleService.update(article);
                }
            }
        }
        return RespEntity.success("删除成功");
    }
}
