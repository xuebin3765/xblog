package com.xblog.commons.utils;

/**
 * Description:
 * Created by Administrator
 * Date 2019/11/28 22:52
 */
public class SnowflakeUUIDUtil {

    private final static SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1,1);

    private SnowflakeUUIDUtil (){}

    public static String getUuid(){
        return String.valueOf(snowflakeIdWorker.nextId());
    }
}
