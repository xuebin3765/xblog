package com.xblog.controller;

import com.google.common.collect.Lists;
import com.xblog.commons.response.RespEntity;
import com.xblog.commons.utils.SnowflakeUUIDUtil;
import com.xblog.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * desc: 文件上传工具类
 * author: xuebin3765@163.com
 * date: 2019/11/27
 */
@Controller
@RequestMapping("/admin/fileUpload")
public class FileController {
    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${upload.picture.path}")
    private String uploadPicturePath;

    @Resource
    private ImageService imageService;


    @RequestMapping(value = "/photo", method = RequestMethod.POST)
    public RespEntity upload(@RequestParam("file") MultipartFile[] multipartFile){
        if (multipartFile == null || multipartFile.length == 0) {
            return RespEntity.error("文件为空");
        }
        List<String> filePathList = Lists.newArrayList();
        for (MultipartFile file: multipartFile) {
            String fileName = file.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            String uuid = SnowflakeUUIDUtil.getUuid();

            fileName = uuid + suffixName; // 新文件名

            File dest = new File(uploadPicturePath + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                logger.error("upload image file, error: {}", e.getMessage());
                return RespEntity.error("上传文件失败");
            }
            filePathList.add(fileName);

        }
        return RespEntity.success(filePathList);
    }
}
