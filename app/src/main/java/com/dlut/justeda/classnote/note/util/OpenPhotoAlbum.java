package com.dlut.justeda.classnote.note.util;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 用于打开相册并将选取图片放在ClassNote/相册管理 文件夹下——放在接口下的课程文件夹
 * 1、将相册管理的放在响应的课程下面——用dialog选课程——name
 * 根据操作的当前时间去命名——不要from
 * 2、将头像放在一个单独的文件夹default下——同时执行压缩操作
 * Created by 赵佳伟 on 2016/11/9.
 */
public class OpenPhotoAlbum {

    private Intent data;
    private Context context;
    private String path = "其他";

    public OpenPhotoAlbum(Context context,Intent data){
        this.context = context;
        this.data = data;
    }


    /**
     * 修改头像
     * 放在default文件夹下
     */
    public Bitmap handleIamgeOnKitKatForUserImage(){
        String imagePath=null;
        path = "default";
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(context, uri)){

            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(
                    uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection= MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri, null);
        }
       return displayUserImage(imagePath);
    }

    /**
     * API>=19使用的一个函数
     * 用于处理图片
     */
    public void handleImageOnKitKat(){
        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(context, uri)){

            String docId=DocumentsContract.getDocumentId(uri);

            if("com.android.providers.media.documents".equals(
                    uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection= MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri, null);
        }
        displayImage(imagePath);
    }

    /**
     * 获取图库照片的路径
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path=null;

        Cursor cursor= null;
        cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }

        return path;
    }

    /**
     * 把相册照片暂时放在其它文件夹
     * @param imagePath 从图库中选择的图片的路径
     */
    private void displayImage(String imagePath) {
        String src=imagePath;
        ClassTime classTime = new ClassTime();
        String dest= Environment.getExternalStorageDirectory().getAbsolutePath()+ "/ClassNote/"+path+"/IMG_"+classTime.getDate()+".jpg";
        BitmapUtil bitmapUtil = new BitmapUtil();
        bitmapUtil.renamePictures("其他",Environment.getExternalStorageDirectory().getAbsolutePath()+ "/ClassNote/"+path+"/");
        try {
            copyFile(src,dest);
            //再添加一个压缩照片的
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private Bitmap displayUserImage(String imagePath) {
        String src = imagePath;
        String dest = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ClassNote/default/default.png";
        Bitmap bitmap = getDiskBitmap(imagePath);
        File file = new File(dest);
        Bitmap defaultBitmap = zoomImage(bitmap, 100, 100);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (defaultBitmap.compress(Bitmap.CompressFormat.PNG, 80, out)) {
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defaultBitmap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }


    private Bitmap getDiskBitmap(String pathString){
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if(file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    /**
     * 将选中图片复制操作到ClassNote/相册管理 文件夹下
     * 应修改为复制操作到对应课程的文件夹下
     * 正在考虑是不是应该用一个dialog
     * 同时应该加上压缩操作
     * @param srcPath 目标路径
     * @param destPath 要移动到的路径
     * @throws IOException
     */
    private void copyFile(String srcPath, String destPath) throws IOException {

        File src=new File(srcPath);
        File dest=new File(destPath);
        try {
            InputStream is =new FileInputStream(src);
            OutputStream os =new FileOutputStream(dest);
            byte[] flush=new byte[1024];
            int len=0;
            while(-1!=(len=is.read(flush))){
                os.write(flush,0,len);
            }
            os.flush();
            os.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}
