package com.xblog.commons.response;

import com.xblog.commons.annotation.RespMeg;

/**
 * 响应码常量
 * Author guodandan
 * Date 2019/9/24 23:09
 */
public class RespCode {

    /**
     * 响应码说明说明
     * 样例
     *      123456
     * length：6位数字
     * success：000000
     * error:  999999
     * 各位数组表示：
     *      首位：
     *          0：通用
     *          1：后台管理系统
     *          2：blog前端系统
     *      2~3位：表示模块
     *          01：菜单模块
     *      4~6位：错误分类，自定义
     *
     */

    @RespMeg("成功")
    public static final String success = "000000";
    @RespMeg("系统内部错误")
    public static final String error = "999999";

}
