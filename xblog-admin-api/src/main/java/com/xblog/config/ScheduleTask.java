package com.xblog.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.xblog.service.FileService;
import com.xblog.service.impl.FileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;

/**
 * Description: 定时任务
 * Created by Administrator
 * Date 2019/12/4 23:11
 */
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class ScheduleTask {

    @Resource
    private COSClient cosClient;

    @Resource
    private FileService fileService;

    @Value("${cos.bucket}")
    private String bucket;

    //3.添加定时任务
//    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        // 创建图片文件夹
        fileService.createFolder(fileService.getFilePath(FileServiceImpl.TYPE_PHOTO));
    }
}
