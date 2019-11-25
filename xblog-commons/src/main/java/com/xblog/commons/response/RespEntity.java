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
@Builder
public class RespEntity {

    private int code;
    private String msg;
    private int count;
    private Object data;

    public RespEntity() {
        this.code = 0;
        this.msg = "success";
        this.count = 0;
    }

    public static RespEntity success(){
        return new RespEntity.RespEntityBuilder()
                .build();
    }

    public static RespEntity success(Object object){
        return new RespEntityBuilder()
                .data(object)
                .code(0)
                .msg("success")
                .build();
    }

    public static RespEntity success(Object object, int count){
        return new RespEntityBuilder()
                .data(object)
                .code(0)
                .count(count)
                .msg("success")
                .build();
    }

    public static RespEntity success(Object object, String message){
        return new RespEntityBuilder()
                .data(object)
                .code(0)
                .msg(message)
                .build();
    }

    public static RespEntity success(String message){
        return new RespEntityBuilder()
                .msg(message)
                .code(0)
                .build();
    }

    public static RespEntity error(int respCode, String respMsg){
        return new RespEntity.RespEntityBuilder()
                .code(respCode)
                .msg(respMsg)
                .build();
    }

    public static RespEntity error(String message){
        return new RespEntityBuilder()
                .msg(message)
                .code(99999)
                .build();
    }

    public static RespEntity error(){
        return new RespEntity.RespEntityBuilder()
                .msg("未知错误")
                .code(99999)
                .build();
    }

    @Override
    public String toString() {
        // 打印结果不转义
        return JsonUtil.toString(this);
    }
}
