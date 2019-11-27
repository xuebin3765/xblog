package com.xblog.common;

import lombok.Data;

import java.util.List;

/**
 * desc: 分页返回结果
 * author: xuebin3765@163.com
 * date: 2019/11/27
 */
@Data
public class PageResult<T> {
    private int count;
    private List<?> rows;

    public PageResult(List<?> rows, int count) {
        this.count = count;
        this.rows = rows;
    }
}
