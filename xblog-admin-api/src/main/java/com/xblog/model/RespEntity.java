package com.xblog.model;

import com.google.common.collect.Maps;
import com.xblog.open.common.annotation.RespMeg;
import com.xblog.open.common.response.RespCode;
import com.xblog.open.common.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * desc:
 * author: xuebin3765@163.com
 * date: 2019/09/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RespEntity {
    private String respCode = RespCode.success;
    private String respMsg = RespEntity.getFileMsg(RespCode.success);
    private Object data;

    private final static String CODE = "code";
    private final static String DATA = "data";
    private final static String MESSAGE = "message";

//    public static String success(){
//        return new RespEntity.RespEntityBuilder()
//                .respCode(RespCode.success)
//                .respMsg(getFileMsg(RespCode.success))
//                .build()
//                .toString();
//    }
//
//    public static String success(Object object){
//        return new RespEntity.RespEntityBuilder()
//                .respCode(RespCode.success)
//                .respMsg(getFileMsg(RespCode.success))
//                .data(JSON.toJSONString(object))
//                .build()
//                .toString();
//    }
//
//    public static String success(Object object, String respMsg){
//        return new RespEntity.RespEntityBuilder()
//                .respCode(RespCode.success)
//                .respMsg(respMsg)
//                .data(JSON.toJSONString(object))
//                .build()
//                .toString();
//    }
//
//    public static String success(String respMsg){
//        return new RespEntity.RespEntityBuilder()
//                .respCode(RespCode.success)
//                .respMsg(respMsg)
//                .build()
//                .toString();
//    }
//
//    public static String error(String respCode, String respMsg){
//
//        return new RespEntity.RespEntityBuilder()
//                .respCode(respCode)
//                .respMsg(respMsg)
//                .build()
//                .toString();
//    }
//
//    public static String error(String respMsg){
//        return new RespEntity.RespEntityBuilder()
//                .respMsg(respMsg)
//                .respCode(RespCode.error)
//                .build()
//                .toString();
//    }
//
//    public static String error(){
//        return new RespEntity.RespEntityBuilder()
//                .respMsg(getFileMsg(RespCode.error))
//                .respCode(RespCode.error)
//                .build()
//                .toString();
//    }

    /**
     * 获取返回码上边的注解描述信息
     * @param respCode 返回码
     * @return 错误描述信息
     */
    public static String getFileMsg(String respCode){
        if (null == respCode || "".equals(respCode.trim()))
            return respCode;
        RespMeg respMeg = respCode.getClass().getAnnotation(RespMeg.class);
        if (null != respMeg){
            return respMeg.value();
        }
        return null;
    }

    @Override
    public String toString() {
        // 打印结果不转义
        return JsonUtil.toStringNoRelation(this);
    }

    /**
     * 返回map结果集，用来给页面返回数据
     * @return map
     */
    public Map<String, Object> toMap(){
        Map<String, Object> map = Maps.newHashMap();
        map.put(CODE, this.respCode);
        map.put(DATA, this.data);
        map.put(MESSAGE, getFileMsg(this.respCode));
        return map;
    }
}
