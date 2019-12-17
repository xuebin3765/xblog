package com.xblog.service.impl;

import com.google.common.collect.Lists;
import com.xblog.commons.utils.QnFileUploadUtil;
import com.xblog.entity.blog.Image;
import com.xblog.service.FileService;
import com.xblog.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

/**
 * Description:
 * Created by Administrator
 * Date 2019/12/3 22:33
 */
@Service
@Transactional
@Slf4j
public class FileServiceImpl implements FileService {



    public final static int TYPE_ALL    = 0;   // 不限制类型
    public final static int TYPE_PHOTO  = 1;   // 图片类型
    public final static int TYPE_OFFICE = 2;   // office 类型
    public final static int TYPE_PDF    = 3;   // pdf 类型
    public final static int TYPE_RAR    = 4;   // 压缩包类型类型
    public final static int TYPE_SHELL  = 5;   // shell脚本文件

    private List<String> photoTypes = Lists.newArrayList(".jpg", "png");
    private List<String> officeTypes = Lists.newArrayList(".doc", ".ppt", ".xls");
    private List<String> pdfTypes = Lists.newArrayList(".pdf");
    private List<String> araTypes = Lists.newArrayList(".rar", ".zip");
    private List<String> shellTypes = Lists.newArrayList(".sh");

    @Resource
    private Environment env;
    @Resource
    private ImageService imageService;


    @PostConstruct
    public void initFileSuffix(){
        String photoTypeParams = env.getProperty("file.type.photo");
        if (StringUtils.isNotBlank(photoTypeParams)){
            photoTypes = Lists.newArrayList(photoTypeParams.split(","));
        }
        String officeTypeParams = env.getProperty("file.type.office");
        if (StringUtils.isNotBlank(officeTypeParams)){
            officeTypes = Lists.newArrayList(officeTypeParams.split(","));
        }
        String pdfTypeParams = env.getProperty("file.type.pdf");
        if (StringUtils.isNotBlank(pdfTypeParams)){
            pdfTypes = Lists.newArrayList(pdfTypeParams.split(","));
        }
        String araTTypeParams = env.getProperty("file.type.rar");
        if (StringUtils.isNotBlank(araTTypeParams)){
            araTypes = Lists.newArrayList(araTTypeParams.split(","));
        }
        String shellTypeParams = env.getProperty("file.type.shell");
        if (StringUtils.isNotBlank(shellTypeParams)){
            shellTypes = Lists.newArrayList(shellTypeParams.split(","));
        }
    }

    @Override
    public String uploadFileToTxCos(MultipartFile file) {

        try {
            if (null == file) return null;
            String originName = file.getOriginalFilename();
            if (StringUtils.isBlank(originName)) return null;
            String imagePath = QnFileUploadUtil.upload(file.getBytes(), originName);
            log.info("upload image success, step into add image url");
            Image image = new Image(imagePath);
            imageService.add(image);
            log.info("step out upload image. success!");
            String url = "http://q2nthlenq.bkt.clouddn.com/";
            return url+imagePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean validateFileType(String suffix, int... type){
        if (StringUtils.isBlank(suffix) || !suffix.startsWith(".")) return false;
        if (type.length > 0){
            for (int t: type) {
                // 不限制类型，所有类型都通过
                if (t == TYPE_ALL )
                    return true;
                // 验证图片
                if (t == TYPE_PHOTO && photoTypes.contains(suffix))
                    return true;
                if (t == TYPE_OFFICE && officeTypes.contains(suffix))
                    return true;
                if (t == TYPE_PDF && pdfTypes.contains(suffix))
                    return true;
                if (t == TYPE_RAR && araTypes.contains(suffix))
                    return true;
                if (t == TYPE_SHELL && shellTypes.contains(suffix))
                    return true;
            }
        }
        return false;
    }
}
