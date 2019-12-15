package com.xblog.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xblog.common.PageResult;
import com.xblog.commons.utils.SnowflakeUUIDUtil;
import com.xblog.entity.sys.Navigate;
import com.xblog.repository.DaoHelperRepository;
import com.xblog.repository.sys.NavigateRepository;
import com.xblog.service.NavigateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Description: 导航service
 * Created by Administrator
 * Date 2019/11/24 16:06
 */
@Service
@Transactional
@Slf4j
public class NavigateServiceImpl implements NavigateService {

    @Resource
    private NavigateRepository navigateRepository;
    @Resource
    private DaoHelperRepository daoHelperRepository;

    @Override
    public Navigate add(Navigate navigate) {
        navigate.setId(SnowflakeUUIDUtil.getUuid());
        return navigateRepository.save(navigate);
    }

    @Override
    public Navigate findById(String id) {
        return navigateRepository.findById(id).orElse(null);
    }

    @Override
    public Navigate findByName(String name) {
        return navigateRepository.findByName(name);
    }

    @Override
    public PageResult<Navigate> findAll(String name, int pageNum, int pageSize) {
        log.debug("findAll, name:{}, pageNum:{}, pageSize:{}", name, pageNum, pageSize);
        if (pageNum <= 0) {
            pageNum = 1;
        }
        // 查询范围为：1 - 20，超过范围默认20条
        if (pageSize <= 0 || pageSize > 100) {
            pageSize = 20;
        }
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append("SELECT a.*, b.name parent_name FROM navigate a LEFT JOIN navigate b ON b.id = a.parent_id where 1=1 ");
        if (StringUtils.isNotBlank(name)) {
            sql.append(" and ( a.name like :name or b.name like :name ) ");
            params.put("name", "%" + name + "%");
        }
        String countSql = sql.toString();
        int count = daoHelperRepository.getCountBy(countSql, params);
        sql.append(" ORDER BY sort asc");
        sql.append(" limit :pageNum, :pageSize");
        params.put("pageNum", (pageNum - 1) * pageSize);
        params.put("pageSize", pageSize);
        List navigateList = daoHelperRepository.queryListEntity(sql.toString(), params, Navigate.class);
        return new PageResult<>(navigateList, count);
    }

    @Override
    public void deleteById(String id) {
        Navigate navigate = navigateRepository.findById(id).orElse(null);
        if (navigate != null)
            navigateRepository.deleteById(id);
    }

    /**
     * 获取导航层级目录
     *
     * @return
     */
    @Override
    public List<Navigate> findAllNavigate() {
        Map<String, Navigate> map = Maps.newHashMap();
        List<Navigate> all = navigateRepository.findAll();
        if (null == all || all.isEmpty())
            return null;
        // 第一遍找出所有顶级菜单
        all.stream().filter(navigate -> StringUtils.isBlank(navigate.getParentId()))
                .forEach(navigate -> {
                    map.put(navigate.getId(), navigate);
                });
        // 第二遍找出子菜单
        all.stream().filter(navigate -> StringUtils.isNotBlank(navigate.getParentId()))
                .forEach(navigate -> {
                    Navigate navigatePatent = map.get(navigate.getParentId());
                    // 添加子菜单
                    if (null != navigatePatent) {
                        List<Navigate> navigateList = navigatePatent.getNavigateList();
                        if (null == navigateList) {
                            navigateList = Lists.newArrayList();
                        }
                        navigateList.add(navigate);
                        navigatePatent.setNavigateList(navigateList);
                    }
                });
        List<Navigate> navigates = Lists.newArrayList(map.values());
        navigates.sort(Comparator.comparingInt(Navigate::getSort));
        return navigates;
    }
}
