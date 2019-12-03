package com.xblog.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xblog.commons.response.RespEntity;
import com.xblog.service.FileService;
import com.xblog.service.impl.FileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * desc: 文件上传工具类
 * author: xuebin3765@163.com
 * date: 2019/11/27
 */
@Controller
@RequestMapping("/admin/fileUpload")
@Slf4j
public class FileController {
    @Resource
    private FileService fileService;


    @RequestMapping(value = "/photo", method = RequestMethod.POST)
    @ResponseBody
    public RespEntity upload(@RequestParam("file") MultipartFile[] multipartFile){
        if (multipartFile == null || multipartFile.length == 0) {
            return RespEntity.error("文件为空");
        }

        List<Map<String, String>> filePathList = Lists.newArrayList();
        for (MultipartFile file: multipartFile) {
            Map<String, String> map = Maps.newHashMap();
            String ofName = file.getOriginalFilename();  // 文件名
            String suffixName = null;
            // 获取文件后缀名
            if (StringUtils.isNotBlank(ofName)){
                map.put("origin", ofName);
                suffixName = ofName.substring(ofName.lastIndexOf("."));  // 后缀名
            }
            // 验证文件类型是否合法，验证文件后缀是否在可接受列表中
            if (fileService.validateFileType(suffixName, FileServiceImpl.TYPE_PHOTO)){
                String nfFile = fileService.uploadFile(file, suffixName);
                if (StringUtils.isNotBlank(nfFile)){
                    map.put("newFile", nfFile);
                    map.put("status","0");
                }else {
                    map.put("status","1");
                    map.put("message","上传失败，未知错误！");
                }
            }else {
                map.put("status","1");
                map.put("message","文件类型不合法");
            }
            filePathList.add(map);
        }
        return RespEntity.success(filePathList);
    }
}
