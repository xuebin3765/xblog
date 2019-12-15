package com.xblog.controller.api;

import com.xblog.common.PageResult;
import com.xblog.commons.response.RespEntity;
import com.xblog.entity.sys.Navigate;
import com.xblog.service.NavigateService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/navigate")
public class NavigateApiController {

    private Logger logger = LoggerFactory.getLogger(NavigateApiController.class);

    @Resource
    private NavigateService navigateService;

    /**
     * 查询所有标签
     * @return RespEntity
     */
    @RequestMapping(value = "/findAllNavigate", method = RequestMethod.GET)
    public RespEntity findAllNavigate(){
        logger.info("step into NavigateController findAll()");
        List<Navigate> navigateList = navigateService.findAllNavigate();
        return RespEntity.success(navigateList);
    }

    /**
     * 查询所有标签
     * @return RespEntity
     */
    @RequestMapping(value = "/findNavigateById", method = RequestMethod.GET)
    public RespEntity findNavigateById(String id){
        logger.info("step into NavigateController findNavigateById(), id: {}", id);
        Navigate navigate = navigateService.findById(id);
        if (navigate != null){
            return RespEntity.success(navigate);
        }else {
            return RespEntity.success("未知");
        }
    }
}
