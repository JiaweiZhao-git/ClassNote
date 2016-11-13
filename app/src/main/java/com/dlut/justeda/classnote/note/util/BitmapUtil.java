package com.dlut.justeda.classnote.note.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 用于处理bitmap的所有操作
 * Created by 赵佳伟 on 2016/11/12.
 */
public class BitmapUtil {



    public BitmapUtil(){

    }

    /**
     * 用于将本地指定的url指向的图片转成bitmap
     * @param url
     * @return bitmap
     */
    public Bitmap getLoacalBitmap(String url){
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //这里应该返回一个默认的logo，更人性化一点
            return null;
        }

    }


}
