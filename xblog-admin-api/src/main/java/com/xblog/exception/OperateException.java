package com.xblog.exception;

/**
 * @author xuebin3765@163.com
 * @Date 2018/9/9 0009 23:39
 */
public class OperateException extends Exception {
    private int code;
    private String message;

    public OperateException() {
    }

    public OperateException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperateException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
