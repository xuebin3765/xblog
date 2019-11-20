package com.xblog.commons.validator.annotation;

import java.lang.annotation.*;

/**
 * desc: 正则校验参数
 * author: xuebin3765@163.com
 * date: 2019/09/28
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Pattern {

    /**
     *
     * @return 正则表达式
     */
    public String regexp();

    public String message() default "";

}
