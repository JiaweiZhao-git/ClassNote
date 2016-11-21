package com.dlut.justeda.classnote.note.noteadapter;

import android.graphics.Bitmap;

/**
 * 课表界面
 * Created by 赵佳伟 on 2016/11/9.
 */
public class NoteItem {
    //item的小标题
    private String littleName;
    private String name;
    private int imageID=0;
    //item加载本地笔记时使用bitmap
    private Bitmap bitmap;

    public NoteItem(String name,int imageID){
        this.name = name;
        this.imageID = imageID;
    }

    public NoteItem(String name, String littleName, int imageID) {
        this.name = name;
        this.littleName = littleName;
        this.imageID = imageID;
    }

    public NoteItem(String name, String littleName, Bitmap bitmap) {
        this.name = name;
        this.littleName = littleName;
        this.bitmap = bitmap;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String getLittleName(){
        return littleName;
    }

    public int getImageID() {
        return imageID;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

}
