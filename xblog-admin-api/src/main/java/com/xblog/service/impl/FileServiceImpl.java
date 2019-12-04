package com.xblog.service.impl;

import com.google.common.collect.Lists;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
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
import java.io.*;
import java.time.LocalDateTime;
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
    @Value("${cos.bucket}")
    private String bucket;

    @Resource
    private Environment env;
    @Resource
    private COSClient cosClient;


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

    @Override
    public String uploadFileToTxCos(MultipartFile file) {

        String originName = file.getOriginalFilename();
        if (StringUtils.isBlank(originName))
            return null;
        // 指定要上传的文件
        File localFile = new File(originName);
        OutputStream out = null;
        try{
            //获取文件流，以文件流的方式输出到新文件
            //    InputStream in = multipartFile.getInputStream();
            out = new FileOutputStream(localFile);
            byte[] ss = file.getBytes();
            for(int i = 0; i < ss.length; i++){
                out.write(ss[i]);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 指定要上传到的存储桶

        String bucketName = bucket;
        // 指定要上传到 COS 上对象键
        String suffix = originName.substring(originName.lastIndexOf("."));
        String key = getFilePath(TYPE_PHOTO)+SnowflakeUUIDUtil.getUuid()+suffix;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        cosClient.shutdown();

        // 操作完上的文件 需要删除在根目录下生成的文件
        File f = new File(localFile.toURI());
        if (f.delete()){
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
        }
        String fix = "https://cxyk-blog-1257612703.cos.ap-chengdu.myqcloud.com/";
        return fix+key;
    }


    /**
     * 生成文件目录地址
     * @param fileType 文件类型
     * @return 文件目录地址
     */
    @Override
    public String getFilePath(int fileType) {
        // 构建文件路径
        StringBuilder filePath = new StringBuilder();
        switch (fileType){
            case 0:
                filePath.append("all");
                break;
            case 1:
                filePath.append("image");
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
        filePath.append("/").append(DataUtil.gitToDayStr()).append("/");
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

    @Override
    public void createFolder(String path) {
        if (StringUtils.isNotBlank(path) && path.endsWith("/")){
            log.error("创建文件夹失败，路径不合法, path: {}", path);
            return;
        }
        // 目录对象即是一个/结尾的空文件，上传一个长度为 0 的 byte 流
        InputStream input = new ByteArrayInputStream(new byte[0]);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(0);

        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucket, path, input, objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        System.err.println("创建文件: " + putObjectRequest.getFixedEndpointAddr());
    }
}
