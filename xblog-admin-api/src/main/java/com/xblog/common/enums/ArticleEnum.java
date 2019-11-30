package com.xblog.common.enums;

/**
 * Description:
 * Created by Administrator
 * Date 2019/11/30 13:27
 */
public enum ArticleEnum {

    DELETE(-1),
    DRAFT(0),
    PUBLISH(1);

    private final int value;

    ArticleEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
