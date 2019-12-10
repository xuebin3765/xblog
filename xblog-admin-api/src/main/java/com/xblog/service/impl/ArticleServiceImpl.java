package com.xblog.service.impl;

import com.xblog.commons.utils.SnowflakeUUIDUtil;
import com.xblog.entity.blog.Article;
import com.xblog.repository.blog.ArticleRepository;
import com.xblog.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * desc:
 * author: xuebin3765@163.com
 * date: 2019/08/04
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Resource
    private ArticleRepository articleRepository;

    /**
     * 假删除，将状态设置为 -1
     * @param id
     * @return
     */
    @Override
    public void delete(String id) {
        logger.info("step delete, id:{}", id);
        Article article = articleRepository.findById(id).orElse(null);
        if (article != null)
            articleRepository.delete(article);
    }

    @Override
    public Article add(Article article) {
        if (article == null) return null;
        if (StringUtils.isBlank(article.getId())){
            article.setId(SnowflakeUUIDUtil.getUuid());
        }
        return articleRepository.save(article);
    }

    @Override
    public Article findById(String id) {
        if (StringUtils.isBlank(id)) return null;
        logger.info("step findById, id:{}", id);
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
}
