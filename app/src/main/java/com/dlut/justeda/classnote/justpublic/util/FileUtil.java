package com.dlut.justeda.classnote.justpublic.util;

import android.os.Environment;

import java.io.File;

/**
 *
 * Created by 赵佳伟 on 2016/11/21.
 */
public class FileUtil {

    public void createInitFiles(){
        String otherPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ClassNote/其他";
        String cameraPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ClassNote/相册管理";
        String qqPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ClassNote/QQ文件管理";
        File dir = new File(otherPath);
        File cameraDir = new File(cameraPath);
        File qqDir = new File(qqPath);
        File smalldir = new File(otherPath+"/small");//建立缩略图的文件夹
        if (!dir.exists()) {
            dir.mkdirs();
            cameraDir.mkdirs();
            qqDir.mkdirs();
            smalldir.mkdirs();
        }
    }

    public void createClassFile(String name) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ClassNote/"+name;
        File dir = new File(path);
        File smallDir = new File(path + "/small");
        if (!dir.exists()) {
            dir.mkdirs();
            smallDir.mkdirs();
        }
    }


}
