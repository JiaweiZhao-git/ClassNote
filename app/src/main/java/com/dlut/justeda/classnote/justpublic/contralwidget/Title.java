package com.dlut.justeda.classnote.justpublic.contralwidget;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dlut.justeda.classnote.R;

/**
 * Created by 赵佳伟 on 2016/11/22.
 */
public class Title {

    private Activity mActivity;

    public void getTitleBar(final Activity activity, String title) {
        mActivity = activity;
        activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        //指定自定义标题栏的布局文件
        activity.setContentView(R.layout.note_title_bar);
        activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.note_title_bar);
//获取自定义标题栏的TextView控件并设置内容为传递过来的字符串
        TextView textView = (TextView) activity.findViewById(R.id.title_middle_text);
        textView.setText(title);
        //设置返回按钮的点击事件
        ImageButton titleBackBtn = (ImageButton) activity.findViewById(R.id.title_left_image);
        titleBackBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //调用系统的返回按键的点击事件
                Toast.makeText(activity.getBaseContext(),"back",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
