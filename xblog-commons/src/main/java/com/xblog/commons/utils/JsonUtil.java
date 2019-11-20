package com.xblog.commons.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * 将对象转换成String，json不适用引用字符
     * @param object 源对象
     * @return 目标对象
     */
    public static String toString(Object object){
        return JSONObject.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 验证字符串是否为json
     * @return 结果 true： 是json； false : 不是json
     */
    public static boolean isJson(String str){
        try {
            logger.debug("validate json, str: {}", str);
            JSONObject.parseObject(str);
            return true;
        }catch (Exception e){
            logger.error("validate json, error: {}", e.getMessage());
            return false;
        }
    }
}
