package com.xblog.controller.api;

import com.google.common.collect.Lists;
import com.xblog.commons.response.RespEntity;
import com.xblog.entity.blog.Banner;
import com.xblog.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * 滚动图
 * Created by Administrator on 2017/10/10.
 */
@RestController
@RequestMapping("/api/banner")
@Slf4j
public class BannerController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private BannerService bannerService;
    /**
     * 获取所有banner图信息
     * @return
     */
    @RequestMapping(value = {"/banners"}, method = {RequestMethod.POST, RequestMethod.GET} , produces = {MediaType.APPLICATION_JSON_VALUE , MediaType.TEXT_PLAIN_VALUE})
    public RespEntity banners(){
        log.info(" step into get banners, method: banners");
        List<Banner> bannerList = bannerService.banners();
        String url = "http://q2nthlenq.bkt.clouddn.com/";
        Optional.ofNullable(bannerList).orElse(Lists.newArrayList())
                .forEach(banner -> {
                    banner.setImageUrl(url + banner.getImageUrl());
                });
        return RespEntity.success(bannerList);
    }

}
