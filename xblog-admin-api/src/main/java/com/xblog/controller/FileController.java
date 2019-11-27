package com.xblog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * desc: 文件上传工具类
 * author: xuebin3765@163.com
 * date: 2019/11/27
 */
@Controller
@RequestMapping("/admin/file")
public class FileController {
    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${upload.picture.path}")
    private String uploadPicturePath;
    @javax.annotation.Resource
    private ResourceLoader resourceLoader;

    /**
     * @Description //上传
     * @Param [multipartFile]
     * @Author oneTi
     * @Date 15:10 2018/8/17
     * @Return java.lang.String
     **/
    @RequestMapping("/photo/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile[] multipartFile){
//        try{
//            //multipartFile.getOriginalFilename() 获取文件原始名称
//            //new Image(multipartFile.getOriginalFilename()) 根据获取到的原始文件名创建目标文件
//            //multipartFile.transferTo() 将收到的文件传输到目标文件中
////            multipartFile.transferTo(new File(multipartFile.getOriginalFilename()));
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
        return "false";
    }
}
