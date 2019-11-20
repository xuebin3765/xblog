package com.xblog.commons.validator;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guodandan
 * @Date 2019/10/1 19:56
 */
@Data
public class ErrorMsg implements Serializable{
    private String propertyPath;
    private String message;

    public ErrorMsg(String propertyPath, String message) {
        this.propertyPath = propertyPath;
        this.message = message;
    }
}
