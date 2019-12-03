package com.xblog.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 * Created by Administrator
 * Date 2019/12/3 21:35
 */
public class DataUtil {
    /**
     * 获取今天时间的字符串形式，
     *      例如：20191101
     * @return
     */
    public static String gitToDayStr(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(new Date());
    }
}
