package com.xblog.common.base;

import com.xblog.open.common.response.RespCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * desc: log工具类
 * author: xuebin3765@163.com
 * date: 2019/09/27
 */
public class BaseLogs extends RespCode {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void debug(String format, Object... message){
        logger.debug(format, message);
    }

    public void debug(String message){
        logger.debug(message);
    }

    public void info(String message){
        logger.info(message);
    }

    public void info(String format, Object... message){
        logger.info(format, message);
    }


}
