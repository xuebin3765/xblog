package com.xblog.service.impl;

import com.xblog.common.PageResult;
import com.xblog.entity.sys.Navigate;
import com.xblog.repository.DaoHelperRepository;
import com.xblog.repository.sys.NavigateRepository;
import com.xblog.service.NavigateService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 导航service
 * Created by Administrator
 * Date 2019/11/24 16:06
 */
@Service
@Transactional
public class NavigateServiceImpl implements NavigateService {


    private Logger logger = LoggerFactory.getLogger(NavigateServiceImpl.class);

    @Resource
    private NavigateRepository navigateRepository;
    @Resource
    private DaoHelperRepository daoHelperRepository;

    @Override
    public Navigate add(Navigate navigate) {
        return navigateRepository.save(navigate);
    }

    @Override
    public Navigate findById(int id) {
        return navigateRepository.findById(id).orElse(null);
    }

    @Override
    public Navigate findByName(String name) {
        return navigateRepository.findByName(name);
    }

    @Override
    public PageResult<Navigate> findAll(String name, int pageNum, int pageSize) {
        logger.debug("findAll, name:{}, pageNum:{}, pageSize:{}", name, pageNum, pageSize);
        if (pageNum <= 0){
            pageNum = 1;
        }
        // 查询范围为：1 - 20，超过范围默认20条
        if (pageSize <= 0 || pageSize > 100){
            pageSize = 20;
        }
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append("SELECT a.*, b.name parent_name FROM navigate a LEFT JOIN navigate b ON b.id = a.parent_id where 1=1 ");
        if (StringUtils.isNotBlank(name)){
            sql.append(" and ( a.name like :name or b.name like :name ) ");
            params.put("name", "%"+name+"%");
        }
        String countSql = sql.toString();
        int count = daoHelperRepository.getCountBy(countSql, params);
        sql.append(" ORDER BY sort asc");
        sql.append(" limit :pageNum, :pageSize");
        params.put("pageNum", (pageNum-1)*pageSize);
        params.put("pageSize", pageSize);
        List navigateList = daoHelperRepository.queryListEntity(sql.toString(), params, Navigate.class);
        return new PageResult<>(navigateList, count);
    }

    @Override
    public void deleteById(int id) {
        navigateRepository.deleteById(id);
    }
}
