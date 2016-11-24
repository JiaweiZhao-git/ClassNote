package com.dlut.justeda.classnote.justpublic.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dlut.justeda.classnote.R;

/**
 * 加载失败返回的界面
 * Created by 赵佳伟 on 2016/11/24.
 */
public class FailLoadingActivity extends Activity{

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_fail_loading);
        imageView = (ImageView) findViewById(R.id.fail_loading_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
