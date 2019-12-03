package com.xblog.service.impl;

import com.google.common.collect.Lists;
import com.xblog.commons.response.RespEntity;
import com.xblog.commons.utils.DataUtil;
import com.xblog.commons.utils.SnowflakeUUIDUtil;
import com.xblog.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.File;
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

    @Value("${upload.picture.path}")
    private String uploadPicturePath;

    @Resource
    private Environment env;


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
    public String uploadFile(MultipartFile file, String suffixName) {
        // 文件类型合法
        String nfName = SnowflakeUUIDUtil.getUuid() + suffixName; // 新文件名
        File dest = new File(getFilePath(FileServiceImpl.TYPE_PHOTO) + nfName);
        try {
            if (!dest.getParentFile().mkdirs())
                file.transferTo(dest);
            return nfName;
        } catch (IOException e) {
            log.error("upload image file, error: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 生成文件目录地址
     * @param fileType 文件类型
     * @return 文件目录地址
     */
    @Override
    public String getFilePath(int fileType) {
        if (StringUtils.isBlank(uploadPicturePath)){
            uploadPicturePath = "/open/tmp";
        }
        // 构建文件路径
        StringBuilder filePath = new StringBuilder(uploadPicturePath);
        if (!uploadPicturePath.endsWith("/"))
            filePath.append(File.separator);
        switch (fileType){
            case 0:
                filePath.append("all");
                break;
            case 1:
                filePath.append("photo");
                break;
            case 2:
                filePath.append("office");
                break;
            case 3:
                filePath.append("pdf");
                break;
            case 4:
                filePath.append("rar");
                break;
            case 5:
                filePath.append("shell");
                break;
        }
        filePath.append(File.separator).append(DataUtil.gitToDayStr()).append(File.separator);
        return filePath.toString();
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
