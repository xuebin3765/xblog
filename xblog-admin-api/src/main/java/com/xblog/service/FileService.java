package com.xblog.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 * Created by Administrator
 * Date 2019/12/3 22:33
 */
public interface FileService {
    /**
     * 上传文件
     * @param file 文件
     * @return 保存后的文件名
     */
    public String uploadFile(MultipartFile file, String suffixName);

    public String getFilePath(int fileType);

    public boolean validateFileType(String suffix, int... type);
}
