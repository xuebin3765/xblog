package com.xblog.commons.validator.annotation;

import java.lang.annotation.*;

/**
 * desc: 邮箱地址验证
 * author: xuebin3765@163.com
 * date: 2019/09/29
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Email {
    public String value() default "";
}
