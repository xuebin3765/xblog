package com.xblog.service.impl;

import com.xblog.common.PageResult;
import com.xblog.commons.utils.SnowflakeUUIDUtil;
import com.xblog.entity.sys.Tag;
import com.xblog.repository.DaoHelperRepository;
import com.xblog.repository.sys.TagRepository;
import com.xblog.service.ArticleTagRelService;
import com.xblog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by Administrator
 * Date 2019/11/24 16:06
 */
@Service
@Transactional
@Slf4j
public class TagServiceImpl implements TagService {

    @Resource
    private TagRepository tagRepository;

    @Resource
    private ArticleTagRelService articleTagRelService;
    @Resource
    private DaoHelperRepository daoHelperRepository;

    @Override
    public Tag add(Tag tag) {
        tag.setId(SnowflakeUUIDUtil.getUuid());
        return tagRepository.save(tag);
    }

    @Override
    public Tag findById(String id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public PageResult<Tag> findAll(String name, int pageNum, int pageSize) {

        log.debug("findAll, name:{}, pageNum:{}, pageSize:{}", name, pageNum, pageSize);
        if (pageNum <= 0){
            pageNum = 1;
        }
        // 查询范围为：1 - 20，超过范围默认20条
        if (pageSize <= 0 || pageSize > 100){
            pageSize = 20;
        }
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append("SELECT * FROM tag where 1=1 ");
        if (StringUtils.isNotBlank(name)){
            sql.append(" and name like :name ");
            params.put("name", "%"+name+"%");
        }
        String countSql = sql.toString();
        int count = daoHelperRepository.getCountBy(countSql, params);
        sql.append(" limit :pageNum, :pageSize");
        params.put("pageNum", (pageNum-1)*pageSize);
        params.put("pageSize", pageSize);
        List list = daoHelperRepository.queryListEntity(sql.toString(), params, Tag.class);
        return new PageResult<>(list, count);
    }

    @Override
    public List<Tag> findAll() {
        String sql = "SELECT tag.*, count(*) article_num from tag tag inner JOIN article_tag_rel atr on tag.id = atr.tag_id GROUP BY name";
        return (List<Tag>) daoHelperRepository.queryListEntity(sql, null, Tag.class);
    }

    @Override
    public void deleteById(String id) {
        tagRepository.deleteById(id);
    }
}
