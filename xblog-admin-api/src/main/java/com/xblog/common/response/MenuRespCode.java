package com.xblog.common.response;


import com.xblog.commons.annotation.RespMeg;

/**
 * 菜单操作响应码
 */
public class MenuRespCode extends RespCode {
    @RespMeg("添加菜单失败")
    public static final String MENU_ADD_ERROR = "101001";
    @RespMeg("添加菜单校验失败")
    public static final String MENU_VALIDATE = "101002";
    @RespMeg("菜单标题或url不能重复")
    public static final String MENU_TITLE_OR_URL_REPEAT = "101003";
    @RespMeg("菜单不存在")
    public static final String MENU_NOT_EXIST = "101004";
    @RespMeg("parentId不能小于0")
    public static final String MENU_PARENT_ID_ERROR = "101005";
}
