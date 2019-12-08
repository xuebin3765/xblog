package com.xblog.service.impl;

import com.google.common.collect.Lists;
import com.xblog.commons.utils.JsonUtil;
import com.xblog.commons.utils.SnowflakeUUIDUtil;
import com.xblog.entity.blog.ArticleNavigateRel;
import com.xblog.repository.blog.ArticleNavigateRelRepository;
import com.xblog.service.ArticleNavigateRelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Description: 文章分类关联关系service
 * Created by Administrator
 * Date 2019/12/1 20:47
 */
@Service
@Transactional
@Slf4j
public class ArticleNavigateRelServiceImpl implements ArticleNavigateRelService {

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
        log.info("step into addAll, articleId: {}", articleId);
        if (StringUtils.isBlank(articleId)){
            log.error("addAll error, article illegality. articleId: {}", articleId);
            return null;
        }

        if (navigateIds == null || navigateIds.isEmpty()){
            log.error("addAll error, navigateIds illegality. navigateIds: {}", JsonUtil.toString(navigateIds));
            return null;
        }

        // 构造新的分类标签和文章关联对象
        List<ArticleNavigateRel> newRelList = Lists.newArrayList();
        navigateIds.forEach(s -> {
            ArticleNavigateRel navigateRel = new ArticleNavigateRel(SnowflakeUUIDUtil.getUuid(), articleId, s);
            if (!newRelList.contains(navigateRel)) newRelList.add(navigateRel);
        });

        List<ArticleNavigateRel> oldRelList = findAllByArticleId(articleId);

        if (oldRelList != null && oldRelList.size() > 0){
            log.info("step into update ArticleNavigateRel");
            // 新增的分类标签，将已经存在的删除，已经存在的不做更新
            newRelList.removeAll(oldRelList);
            // newRelList 剩下的是需要全部新增的。
        }

        return anrRepository.saveAll(newRelList);
    }

    @Override
    public ArticleNavigateRel addOne(String navigateId, String articleId) {
        return null;
    }
}
