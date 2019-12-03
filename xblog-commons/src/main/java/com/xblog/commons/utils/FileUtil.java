package com.xblog.commons.utils;

import java.io.File;

/**
 * Description:
 * Created by Administrator
 * Date 2019/12/3 21:40
 */
public class FileUtil {
    public static String getImageFilePath(){
        File file = new File("D:\\springboot\\upload\\651536464957542400.jpg");
        File file1 = file.getParentFile();
        File file2 = file1.getAbsoluteFile();
        return file.getParent();
    }

    public static void main(String[] args) {
        System.out.println(getImageFilePath());
    }
}
