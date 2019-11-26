package com.xblog.controller;

import com.google.common.collect.Lists;
import com.xblog.commons.response.RespEntity;
import com.xblog.entity.sys.Navigate;
import com.xblog.service.NavigateService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/navigate")
public class NavigateController {

    private Logger logger = LoggerFactory.getLogger(NavigateController.class);

    @Resource
    private NavigateService navigateService;

    /**
     * 增加标签
     * @return RespEntity
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RespEntity add(@RequestBody Navigate navigate){
        logger.debug("step into NavigateController add(), Navigate: {}", navigate);
        if (StringUtils.isBlank(navigate.getName())){
            return RespEntity.error("请输导航名称");
        }

        if (StringUtils.isBlank(navigate.getUrl())){
            return RespEntity.error("请输入导航地址");
        }
        navigate = navigateService.add(navigate);
        if (navigate != null){
            return RespEntity.success(navigate, "添加成功");
        }else {
            return RespEntity.error("添加标签失败");
        }
    }

    /**
     * 修改标签
     * @return RespEntity
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public RespEntity update(@RequestBody Navigate navigate){
        logger.debug("step into NavigateController update(), navigate: {}", navigate);
        Navigate navigateOld = navigateService.findById(navigate.getId());
        if (navigateOld == null){
            return RespEntity.error("导航不存在");
        }
        if (StringUtils.isNotBlank(navigate.getName())){
            Navigate navigate1 = navigateService.findByName(navigate.getName());
            // 可以重复修改当前导航的名称和当前导航一样
            if (navigate1 != null && navigate1.getId() != navigateOld.getId()){
                return RespEntity.error("导航名称已经存在");
            }
            navigateOld.setName(navigate.getName());
        }
        if (StringUtils.isNotBlank(navigate.getUrl())){
            navigateOld.setUrl(navigate.getUrl());
        }
        if (navigate.getParentId() > 0){
            navigateOld.setParentId(navigate.getParentId());
        }
        navigateOld = navigateService.add(navigateOld);
        if (navigateOld == null){
            return RespEntity.error("修改标签失败");
        }
        return RespEntity.success(navigateOld);
    }

    /**
     * 查询单个标签
     * @return RespEntity
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public RespEntity findById(int id){
        logger.debug("step into NavigateController findById(), id: {}", id);
        Navigate navigate = navigateService.findById(id);
        return RespEntity.success(navigate);
    }

    /**
     * 查询所有标签
     * @return RespEntity
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public RespEntity findAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit
    ){
        logger.debug("step into NavigateController findAll(), pageNum: {}, pageSize: {}", page, limit);
        List<Navigate> navigates = navigateService.findAll(page, limit);
        Optional.of(navigates).orElse(Lists.newArrayList())
                .stream()
                .forEach(navigate -> {
                    Navigate navigate1 = navigateService.findById(navigate.getParentId());
                    if (null != navigate1){
                        navigate.setParentName(navigate1.getName());
                    }
                });
        return RespEntity.success(navigates, navigates !=null ? navigates.size() : 0);
    }

    /**
     * 删除
     * @return RespEntity
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public RespEntity delete(int id){
        logger.debug("step into NavigateController delete(), id: {}", id);
        navigateService.deleteById(id);
        return RespEntity.success("删除成功");
    }

    /**
     * 删除
     * @return RespEntity
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.GET)
    public RespEntity deleteBatch(@RequestParam(value = "ids[]") int[] ids){
        logger.debug("step into NavigateController delete(), ids: {}", ids);
        if (ids != null && ids.length > 0){
            for (int id: ids) {
                navigateService.deleteById(id);
            }
        }
        return RespEntity.success("删除成功");
    }
}
