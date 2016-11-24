package com.dlut.justeda.classnote.justpublic.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.justpublic.util.FeedBackObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 反饋界面
 * Created by 赵佳伟 on 2016/11/23.
 */
public class FeedBackActivity extends Activity{

    private EditText callbackText;
    private Button callbackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.left_main_feedback);

        initViews();

        callbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToBmob();
            }


        });
    }

    private void sendToBmob() {
        String someThings = callbackText.getText().toString();

        FeedBackObject feedBackObject = new FeedBackObject();
        feedBackObject.setFeedBack(someThings);

        feedBackObject.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Toast.makeText(FeedBackActivity.this,"感谢您的反馈，我们会努力做的更好~",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initViews() {
        Bmob.initialize(this,"e711db6efbb7e3731f67887483506686");

        callbackText = (EditText) findViewById(R.id.et_callBack);
        callbackButton = (Button) findViewById(R.id.btn_callBack);
    }
}
