package com.xblog.commons.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.*;

/**
 * Description: 七牛云图片上传工具类
 * Created by Administrator
 * Date 2019/12/17 22:43
 */
public class QnFileUploadUtil {


    public static String upload(byte[] fileByte, String fileName){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = SnowflakeUUIDUtil.getUuid()+fileName.substring(fileName.lastIndexOf("."));
        //            byte[] uploadBytes = fileByte;
        ByteArrayInputStream byteInputStream=new ByteArrayInputStream(fileByte);
        String secretKey = "cRRkEHPfECk9fscnqSeDY0IZyHXFArTT2Cz0zspT";
        String accessKey = "zRzsT6OAk41gjs4QbDcTHKqGMifrITLcvCmwyZMT";
        Auth auth = Auth.create(accessKey, secretKey);
        String bucket = "cxyk";
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(byteInputStream, key, upToken,null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);

            return putRet.key;
        } catch (QiniuException ex) {
//            Response r = ex.response;
//            System.err.println(r.toString());
//            try {
//                System.err.println(r.bodyString());
//            } catch (QiniuException ex2) {
//                //ignore
//            }
        }
        return null;
    }
}
