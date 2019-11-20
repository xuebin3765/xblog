package com.xblog.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 滚动图
 * Created by Administrator on 2017/10/10.
 */
@Controller
@RequestMapping("/api/banner")
public class BannerController {

    private Logger logger = LoggerFactory.getLogger(BannerController.class);

    @Resource
    private HttpServletRequest request;

//    @Resource
//    private BannerService bannerService;
//    /**
//     *
//     * @param type
//     * @return
//     */
//    @RequestMapping(value = {"/public/v1/banners"}, method = {RequestMethod.POST, RequestMethod.GET} , produces = {MediaType.APPLICATION_JSON_VALUE , MediaType.TEXT_PLAIN_VALUE})
//    @ResponseBody
//    public String banners(
//            @RequestParam(value = "appId") int appId,
//            @RequestParam(value = "type") int type
//    ){
//        logger.info(String.format("request_log_param=%s", RequestUtil.getRequestBodyParam(request)));
//        JsonResult result = new JsonResult();
//        result.setStatus(StatusCode.CODE_SUCCESS);
//        List<Banner> bannerList = bannerService.banners(appId , type);
//        result.setMessage("获取布成功");
//        result.setStatus(StatusCode.CODE_SUCCESS);
//        result.setData(bannerList);
//        return result.toJson();
//    }

}
