package com.dlut.justeda.classnote.note.util;

import android.app.Application;

/**
 *
 * Created by 赵佳伟 on 2016/11/21.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
    }

}
