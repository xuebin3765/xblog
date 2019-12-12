package com.xblog.controller.api;

import com.xblog.common.PageResult;
import com.xblog.commons.response.RespEntity;
import com.xblog.entity.sys.Navigate;
import com.xblog.service.NavigateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/navigate")
public class NavigateController {

    private Logger logger = LoggerFactory.getLogger(NavigateController.class);

    @Resource
    private NavigateService navigateService;

    /**
     * 查询所有标签
     * @return RespEntity
     */
    @RequestMapping(value = "/findAllNavigate", method = RequestMethod.GET)
    public RespEntity findAllNavigate(){
        logger.info("step into NavigateController findAll()");
        PageResult<Navigate> pageResult = navigateService.findAll(null, 1, 200);
        return RespEntity.success(pageResult.getRows(), pageResult.getCount());
    }
}