package com.xblog.model;

import lombok.Data;

/**
 * Description:
 * Created by Administrator
 * Date 2019/12/15 15:08
 */
@Data
public class WangEditor {
    private int errno; //错误代码，0 表示没有错误。
    private String[] data; //已上传的图片路径


    public WangEditor(String[] data) {
        super();
        this.errno = 0;
        this.data = data;
    }
}
