package com.xblog.service.impl;

import com.xblog.entity.blog.ArticleNavigateRel;
import com.xblog.repository.blog.ArticleNavigateRelRepository;
import com.xblog.service.ArticleNavigateRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Description: 文章分类关联关系service
 * Created by Administrator
 * Date 2019/12/1 20:47
 */
@Service
@Transactional
public class ArticleNavigateRelServiceImpl implements ArticleNavigateRelService {

    private Logger logger = LoggerFactory.getLogger(ArticleNavigateRelServiceImpl.class);

    @Resource
    private ArticleNavigateRelRepository anrRepository;

    @Override
    public List<ArticleNavigateRel> findAllByArticleId(String articleId) {
        return anrRepository.findAllByArticleId(articleId);
    }

    @Override
    public List<ArticleNavigateRel> findAllByNavigateId(String navigateId) {
        return anrRepository.findAllByNavigateId(navigateId);
    }

    @Override
    public void deleteAllByArticleId(String articleId) {

    }

    @Override
    public void deleteAllByNavigateId(String navigateId) {

    }

    @Override
    public ArticleNavigateRel findByNavAndAriId(String navigateId, String articleId) {
        return null;
    }

    @Override
    public List<ArticleNavigateRel> addAll(List<String> navigateIds, String articleId) {
        return null;
    }

    @Override
    public ArticleNavigateRel addOne(String navigateId, String articleId) {
        return null;
    }
}
