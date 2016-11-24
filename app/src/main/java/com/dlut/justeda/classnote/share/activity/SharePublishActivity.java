package com.dlut.justeda.classnote.share.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.justpublic.contralwidget.ClassNameDialog;
import com.dlut.justeda.classnote.justpublic.fragment.ShareFragment;
import com.dlut.justeda.classnote.share.data.Data;
import com.dlut.justeda.classnote.share.message.TopicMeg;
import com.dlut.justeda.classnote.share.util.Network;

import java.io.File;

/**
 * Created by chaomaer on 2016/11/8.
 */

public class SharePublishActivity extends Activity implements View.OnClickListener{
    private ImageButton button_back,button_publish;
    private EditText publish_content;
    private ImageView publish_img;
    private String topcontent;

    private ClassNameDialog classNameDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_publish_layout);
        initUI();
    }

    private void initUI() {
        button_back= (ImageButton) findViewById(R.id.share_publish_title_back);
        button_publish= (ImageButton) findViewById(R.id.share_publish_title_send);
        publish_content= (EditText) findViewById(R.id.share_publish_content);
        publish_img= (ImageView) findViewById(R.id.share_public_img);
        button_back.setOnClickListener(this);
        button_publish.setOnClickListener(this);
        publish_img.setOnClickListener(this);

        classNameDialog = new ClassNameDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_publish_title_back:
                this.finish();
                break;

            case R.id.share_publish_title_send:
                topcontent=publish_content.getText().toString();
                if(!TextUtils.isEmpty(topcontent)){
                    Data.getTopicMeglist().add(0,new TopicMeg(Data.avatarurl,"",topcontent,Data.username));
                    ShareFragment.shareImageAdapter.notifyDataSetChanged();
                    Network.publish(new File(""),topcontent);
                    this.finish();
                }
                break;
            case R.id.share_share_image:

                break;
            case R.id.share_public_img:
                classNameDialog.showClassNameListDialog(v,"請選擇課程：",SharePublishActivity.this,SharePublishActivity.this);
                break;
        }
    }

    /**
     * 返回選中的照片路徑，string
     * 添加到圖片中
     * 同時將dialog取消
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 110 && resultCode == 100) {
            String result = data.getStringExtra("result");
            Log.e(getLocalClassName(), result);
        }
    }
}
