package com.xblog.commons.controller;

import com.xblog.commons.response.RespEntity;

/**
 * desc:
 * author: xuebin3765@163.com
 * date: 2019/09/23
 */
public class BaseController {

    public String success(Object object){
        return RespEntity.success(object).toString();
    }

    public String success(){
        return RespEntity.success().toString();
    }

    public String error(){
        return RespEntity.error().toString();
    }

    public String error(String respMsg){
        return RespEntity.error(respMsg).toString();
    }

    protected String error(int respCode, String respMsg){
        return RespEntity.error(respCode, respMsg).toString();
    }
}
