package com.dlut.justeda.classnote.justpublic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dlut.justeda.classnote.R;

/**
 * 设置界面
 * wifi下更新
 * 查看用户协议
 * 退出登陆
 * Created by 赵佳伟 on 2016/11/24.
 */
public class SettingsActivity extends Activity implements View.OnClickListener{

    private TextView user_read;
    private Button quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_left_setting_layout);

        initViews();
    }

    private void initViews() {

        user_read = (TextView) findViewById(R.id.setting_user_read);
        quit = (Button) findViewById(R.id.setting_quit_login);
        user_read.setOnClickListener(this);
        quit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_user_read:
                startActivity(new Intent(SettingsActivity.this,AgreeMentActivity.class));
                break;

            case R.id.setting_quit_login:
                startActivity(new Intent(SettingsActivity.this,FailLoadingActivity.class));
                break;
        }
    }
}
