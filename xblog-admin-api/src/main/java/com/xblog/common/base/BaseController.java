package com.xblog.common.base;

import com.xblog.open.common.response.RespCode;
import com.xblog.open.common.utils.JsonUtil;
import com.xblog.open.model.RespEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;

/**
 * desc:
 * author: xuebin3765@163.com
 * date: 2019/09/23
 */
public class BaseController extends BaseLogs {

//    public String success(Object object){
//        return RespEntity.success(object).toString();
//    }
//
//    public String success(){
//        return RespEntity.success().toString();
//    }
//
//    public String success(Object object, String respCode, String respMsg){
//        return RespEntity.success(object).toString();
//    }
//
//    public String error(){
//        return RespEntity.error().toString();
//    }
//
//    public String error(String respMsg){
//        return RespEntity.error(respMsg).toString();
//    }
//
//    protected String error(String respCode, String respMsg){
//        return RespEntity.error(respCode, respMsg).toString();
//    }

    protected void setDataToModelView(Model model,Object data){
        debug("step into setDataToModelView");
        setDataToModelView(model, RespCode.success, data);
        debug("step out setDataToModelView");
    }

    protected void setDataToModelView(Model model,String respCode, Object data){
        debug("step into setDataToModelView");
        if (StringUtils.isBlank(respCode)){
            respCode = RespCode.success;
        }
        setDataToModelView(model, respCode, null, data);
        debug("step out setDataToModelView");
    }

    protected void setDataToModelView(Model model,String respCode){
        debug("step into setDataToModelView");
        if (StringUtils.isBlank(respCode)){
            respCode = RespCode.success;
        }
        setDataToModelView(model, respCode, null, null);
        debug("step out setDataToModelView");
    }

    protected void setDataToModelView(Model model,String respCode, String respMessage, Object data){
        debug("step into setDataToModelView");
        RespEntity respEntity = new RespEntity();
        respEntity.setRespCode(respCode);
        respEntity.setRespMsg(StringUtils.isNotBlank(respMessage)? respMessage: RespEntity.getFileMsg(respCode));
        respEntity.setData(data);
        debug("data: {}", JsonUtil.toStringNoRelation(respEntity));
        model.addAllAttributes(respEntity.toMap());
        debug("step out setDataToModelView");
    }
}
