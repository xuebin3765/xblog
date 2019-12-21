package com.xblog.service.impl;

import com.google.common.collect.Lists;
import com.xblog.commons.utils.JsonUtil;
import com.xblog.commons.utils.SnowflakeUUIDUtil;
import com.xblog.entity.blog.ArticleNavigateRel;
import com.xblog.entity.blog.ArticleTagRel;
import com.xblog.repository.sys.ArticleTagRelRepository;
import com.xblog.service.ArticleTagRelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Description:
 * Created by Administrator
 * Date 2019/12/8 21:16
 */
@Service
@Transactional
@Slf4j
public class ArticleTagRelServiceImpl implements ArticleTagRelService {
    @Resource
    private ArticleTagRelRepository relRepository;

    @Override
    public List<ArticleTagRel> findAllByArticleId(String articleId) {
        return relRepository.findAllByArticleId(articleId);
    }

    @Override
    public List<ArticleTagRel> addAll(List<String> articleTagRelList, String articleId) {
        log.info("step into addAll, articleId: {}， articleTagRelList： {}", articleId, JsonUtil.toString(articleTagRelList));
        if (StringUtils.isBlank(articleId)){
            log.error("addAll error, article illegality. articleId: {}", articleId);
            return null;
        }

        if (articleTagRelList == null || articleTagRelList.isEmpty()){
            log.error("addAll error, navigateIds illegality. navigateIds: {}", JsonUtil.toString(articleTagRelList));
            return null;
        }

        // 构造新的分类标签和文章关联对象
        List<ArticleTagRel> newRelList = Lists.newArrayList();
        articleTagRelList.forEach(s -> {
            ArticleTagRel tagRel = new ArticleTagRel(SnowflakeUUIDUtil.getUuid(), s, articleId);
            if (!newRelList.contains(tagRel)) newRelList.add(tagRel);
        });

        List<ArticleTagRel> oldRelList = findAllByArticleId(articleId);

        if (oldRelList != null && oldRelList.size() > 0){
            log.info("step into update ArticleNavigateRel");
            // 新增的分类标签，将已经存在的删除，已经存在的不做更新
            newRelList.removeAll(oldRelList);
            // newRelList 剩下的是需要全部新增的。
        }
        return relRepository.saveAll(newRelList);
    }
}
