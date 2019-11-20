package com.xblog.commons.validator.annotation;

import java.lang.annotation.*;

/**
 * desc: 字段最大值注解
 * author: xuebin3765@163.com
 * date: 2019/09/28
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Max {

    public int value();

    public String message() default "";
}
