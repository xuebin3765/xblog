package com.xblog.commons.validator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author guodandan
 * @Date 2019/10/1 19:54
 */
@Data
public class ValidResult implements Serializable {
    /**
     * 是否有错误
     */
    private boolean hasErrors;
    /**
     * 错误信息列表
     */
    private List<ErrorMsg> errorMsgs;

    public ValidResult() {
        this.errorMsgs = Lists.newArrayList();
    }

    /**
     * 添加错误
     * @param propertyName 参数名
     * @param message 错误描述
     */
    public void addError(String propertyName, String message){
        this.errorMsgs.add(new ErrorMsg(propertyName, message));
    }

    /**
     * 获取错误的字段名称，用逗号隔开
     * 去重错误字段
     * @return 错误字段信息
     */
    public String getProperties(){
        return errorMsgs
                .stream()
                .map(ErrorMsg::getPropertyPath)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.joining(", "));
    }

    /**
     * 返回所有错误信息
     * @return str
     */
    public String getErrors(){
        List<String> stringList = Lists.newArrayList();
        for (ErrorMsg msg :errorMsgs) {
            stringList.add(msg.getPropertyPath()+":"+msg.getMessage());
        }
        return String.join(", ", stringList);
    }

    public Map<String, Object> getMapErrors(){
        Map<String, Object> map = Maps.newHashMap();

        for (ErrorMsg msg :errorMsgs) {
            map.put(msg.getPropertyPath(), msg.getMessage());
        }
        return map;
    }

    public String getSimpleErrors(){
        List<String> errors = Optional.ofNullable(errorMsgs).orElse(Lists.newArrayList())
                .stream()
                .map(ErrorMsg::getMessage)
                .collect(Collectors.toList());
        return String.join(", ", errors);
    }

    public boolean isHasErrors() {
        return this.errorMsgs != null && this.errorMsgs.size() > 0;
    }

}
