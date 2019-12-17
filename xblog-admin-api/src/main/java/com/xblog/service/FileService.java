package com.xblog.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 * Created by Administrator
 * Date 2019/12/3 22:33
 */
public interface FileService {

    /**
     * 上传文件到腾讯对象存储
     * @param file 文件
     * @return 文件访问路径
     */
    public String uploadFileToTxCos(MultipartFile file);

    public boolean validateFileType(String suffix, int... type);
}
