package com.xblog.service.impl;

import com.google.common.collect.Lists;
import com.xblog.common.PageResult;
import com.xblog.commons.utils.SnowflakeUUIDUtil;
import com.xblog.entity.blog.Article;
import com.xblog.entity.sys.Navigate;
import com.xblog.entity.sys.Tag;
import com.xblog.repository.DaoHelperRepository;
import com.xblog.repository.blog.ArticleRepository;
import com.xblog.service.ArticleService;
import com.xblog.service.NavigateService;
import com.xblog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * desc:
 * author: xuebin3765@163.com
 * date: 2019/08/04
 */
@Service
@Transactional
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleRepository articleRepository;
    @Resource
    private DaoHelperRepository daoHelperRepository;
    @Resource
    private NavigateService navigateService;
    @Resource
    private TagService tagService;

    /**
     * 假删除，将状态设置为 -1
     * @param id
     * @return
     */
    @Override
    public void delete(String id) {
        log.info("step delete, id:{}", id);
        Article article = articleRepository.findById(id).orElse(null);
        if (article != null)
            articleRepository.delete(article);
    }

    @Override
    public Article add(Article article) {
        if (article == null) return null;
        if (StringUtils.isBlank(article.getId())){
            article.setId(SnowflakeUUIDUtil.getUuid());
            article.setCreated(System.currentTimeMillis());
        }
        article.setModify(System.currentTimeMillis());
        return articleRepository.save(article);
    }

    @Override
    public Article findById(String id) {
        if (StringUtils.isBlank(id)) return null;
        log.info("step findById, id:{}", id);
        return articleRepository.findById(id).orElse(null);
    }

    /**
     * 修改文章
     * @param article 文章
     * @return 文章
     */
    @Override
    public Article update(Article article) {
        if (null == article) return null;
        article.setModify(System.currentTimeMillis());
        return articleRepository.save(article);
    }

    @Override
    public PageResult<Article> findAll(String key, int page, int limit) {
        log.debug("findAll, name:{}, pageNum:{}, pageSize:{}", key, page, limit);
        if (page <= 0){
            page = 1;
        }
        // 查询范围为：1 - 20，超过范围默认20条
        if (limit <= 0 || limit > 100){
            limit = 20;
        }
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append("SELECT * FROM article where 1=1 ");
        if (StringUtils.isNotBlank(key)){
            sql.append(" and ( title like :key or content like :key ) ");
            params.put("key", "%"+key+"%");
        }
        String countSql = sql.toString().replace("*", "count(*)");
        int count = daoHelperRepository.getCountBy(countSql, params);
        sql.append(" ORDER BY id desc");
        sql.append(" limit :pageNum, :pageSize");
        params.put("pageNum", (page-1)*limit);
        params.put("pageSize", limit);
        List<Article> articleList = (List<Article>)daoHelperRepository.queryListEntity(sql.toString(), params, Article.class);

        Optional.of(articleList).orElse(Lists.newArrayList())
                .forEach(article -> {

//                    // 设置标题
//                    List<Navigate> navigateList = navigateService.findallByArticleId(article.getId());
//                    if (navigateList != null){
//                        article.setNavigateList(navigateList);
//                    }
//                    // 设置标签
//                    List<Tag> tagList = tagService.findallByArticleId(article.getId());
//                    if (tagList != null){
//                        article.setTagList(tagList);
//                    }
                });
        return new PageResult<>(articleList, count);
    }
}
