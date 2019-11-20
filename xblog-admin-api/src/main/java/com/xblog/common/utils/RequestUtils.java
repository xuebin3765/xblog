package com.xblog.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuebin@si-tech.com.cn
 * @Description request 请求工具类
 * @date 2019/08/03
 */
public class RequestUtils {
    /**
     * 获取请求体中的参数
     * @param request request
     * @return params
     */
    public static Map<String, Object> getRequestBodyParam(HttpServletRequest request){
        // 参数Map
        Map<?, ?> properties = request.getParameterMap();
        // 返回值Map
        Map<String, Object> returnMap = new HashMap<>();
        for (Map.Entry entry: properties.entrySet()) {
            String name = (String) entry.getKey();
            String value = "";
            Object valueObj = entry.getValue();
            if (null != valueObj){
                if (valueObj instanceof String[]) {
                    String[] values = (String[]) valueObj;
                    value = String.join(",", values);
                } else {
                    value = valueObj.toString();
                }
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }
}
