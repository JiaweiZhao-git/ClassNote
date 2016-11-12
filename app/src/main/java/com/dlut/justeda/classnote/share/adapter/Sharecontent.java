package com.dlut.justeda.classnote.share.adapter;

import android.graphics.Bitmap;

/**
 * Created by chaomaer on 2016/11/7.
 */

public class Sharecontent {
    private String content;
    private Bitmap imageView;

    public Sharecontent(String content, Bitmap imageView) {
        this.content = content;
        this.imageView = imageView;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Bitmap getImageView() {
        return imageView;
    }

    public void setImageView(Bitmap imageView) {
        this.imageView = imageView;
    }
}
