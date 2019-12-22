package com.xblog.anno;

/**
 * desc: 接口权限管理
 * author: xuebin3765@163.com
 * date: 2019/12/22
 */
public @interface AuthToken {
    /**
     * 访问接口需要的权限
     *  0：all
     *  1：login
     *  2：admin
     *  3：superman
     * @return int
     */
    int value() default 0;
}
