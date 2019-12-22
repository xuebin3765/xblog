package com.xblog.constant;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 常量
 * Created by Administrator on 2017/9/27.
 */
@Component
@PropertySource("")
public class Constant {
    public final static String SESSION_USER = "session_users_";
}
