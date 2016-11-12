package com.dlut.justeda.classnote.note.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片压缩类
 * 用于为note和share压缩图片
 * 暂时没有添加share的压缩方法（大小未知）
 * Created by 赵佳伟 on 2016/11/10.
 */
public class PicCompress {

    private String path;
    private String name;
    private String smallPath;
    private Bitmap bitmip;
    private Bitmap smallBitmap;
    private File smallFile;

    public PicCompress(String name,String path,Bitmap bitmap) {
        this.name = name;
        this.path = path;
        this.bitmip =bitmap;
    }

    void compressPictures(){

        bitmip = getDiskBitmap(path);

        smallPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ClassNote/"+name+"/small";
        File file = new File(smallPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        smallFile = new File(smallPath+"/"+name+path.substring(33, 49)+".png");
        Log.e("small",name);
        smallBitmap = zoomImage(bitmip,100,100);

        try {
            FileOutputStream out = new FileOutputStream(smallFile);
            if (smallBitmap.compress(Bitmap.CompressFormat.PNG, 10, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,double newHeight) {
        // »ñÈ¡Õâ¸öÍ¼Æ¬µÄ¿íºÍ¸ß
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();

        Matrix matrix = new Matrix();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    private Bitmap getDiskBitmap(String pathString){
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if(file.exists()){
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {

        }
        return bitmap;
    }
}
