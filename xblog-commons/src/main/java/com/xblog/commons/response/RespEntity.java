package com.xblog.commons.response;

import com.google.common.collect.Maps;
import com.xblog.commons.annotation.RespMeg;
import com.xblog.commons.utils.JsonUtil;
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

    private String respCode = "00000";
    private String respMsg = "success";
    private Object data;

    public static RespEntity success(){
        return new RespEntity.RespEntityBuilder()
                .build();
    }

    public static RespEntity success(Object object){
        return new RespEntity.RespEntityBuilder()
                .data(object)
                .build();
    }

    public static RespEntity success(String message){
        return new RespEntity.RespEntityBuilder()
                .respMsg(message)
                .build();
    }

    public static RespEntity error(String respCode, String respMsg){
        return new RespEntity.RespEntityBuilder()
                .respCode(respCode)
                .respMsg(respMsg)
                .build();
    }

    public static RespEntity error(String message){
        return new RespEntity.RespEntityBuilder()
                .respMsg(message)
                .respCode("99999")
                .build();
    }

    public static RespEntity error(){
        return new RespEntity.RespEntityBuilder()
                .respMsg("未知错误")
                .respCode("99999")
                .build();
    }

    @Override
    public String toString() {
        // 打印结果不转义
        return JsonUtil.toString(this);
    }
}
