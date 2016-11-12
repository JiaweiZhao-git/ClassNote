package com.dlut.justeda.classnote.note.util;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
 * 用于打开相册并将选取图片放在ClassNote/相册管理 文件夹下
 * Created by 赵佳伟 on 2016/11/9.
 */
public class OpenPhotoAlbum {

    private Intent data;
    private Context context;

    public OpenPhotoAlbum(Context context,Intent data){
        this.context = context;
        this.data = data;
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
        String dest= Environment.getExternalStorageDirectory().getAbsolutePath()+ "/ClassNote/其它/"+"NoteIsFromCamera.jpg";

        try {
            copyFile(src,dest);
            //再添加一个压缩照片的
        } catch (IOException e) {

            e.printStackTrace();
        }
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
