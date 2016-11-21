package com.dlut.justeda.classnote.note.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * 刚拍的照片由于用了依赖引入，照片名字和我想要的不一样
     * 用于修改名字为NOTEyyyyMMddHHmm 格式
     * 压缩照片到small文件夹
     */
    public void renamePictures(String name,String dirPath){
        Log.e("renamePictures", "start");
        File Dirs = new File(dirPath);
        if (Dirs.exists()) {
            File[] files = Dirs.listFiles();
            for (File listFile : files) {
                /*
                遍历所有照片，找出有IMG_的，然后改名字，再压缩
                 */
                String fromPath = listFile.getAbsolutePath();
                if (fromPath.contains("IMG_")) {
                    String mill = fromPath.substring(fromPath.indexOf("IMG_")+4, fromPath.length() - 4);
                    Log.e("renamePictures", mill);
                    Date date = new Date(Long.valueOf(mill));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                    Log.e("renamePictures", sdf.format(date));
                    String note = fromPath.substring(0, fromPath.indexOf("IMG_")) + "NOTE"+sdf.format(date) + ".jpg";
                    Log.e("renamePictures", note);
                    File newFile = new File(note);
                    listFile.renameTo(newFile);
                    PicCompress picCompress = new PicCompress(name, note);
                    picCompress.compressPictures();
                }
                //PicCompress picCompress = new PicCompress("其他", fromPath);
            }
        }

    }


}
