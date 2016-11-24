package com.dlut.justeda.classnote.justpublic.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.dlut.justeda.classnote.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public void createTextFile(Context context,String className, String date) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ClassNote/"+className+"/small/"+"TEXT"+date+".png";
            //用默认图片填充这个文件
            drawableTofile(context.getResources().getDrawable(R.drawable.note_list_text),path);
    }

    public void drawableTofile(Drawable drawable, String path){
        //Log.i(TAG, "drawableToFile:"+path);
        Log.e("toFile", path);
        File file=new File(path);
        Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            fos.write(bitmapdata);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
