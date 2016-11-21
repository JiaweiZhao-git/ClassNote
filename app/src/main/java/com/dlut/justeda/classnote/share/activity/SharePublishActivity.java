package com.dlut.justeda.classnote.share.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.justpublic.fragment.ShareFragment;
import com.dlut.justeda.classnote.share.data.Data;
import com.dlut.justeda.classnote.share.message.TopicMeg;
import com.dlut.justeda.classnote.share.util.Network;

import java.io.File;

/**
 * Created by chaomaer on 2016/11/8.
 */

public class SharePublishActivity extends Activity implements View.OnClickListener{
    private Button button_back,button_publish;
    private EditText publish_content;
    private ImageView publish_img;
    private String topcontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_publish_layout);
        initUI();
    }

    private void initUI() {
        button_back= (Button) findViewById(R.id.share_publish_back);
        button_publish= (Button) findViewById(R.id.share_publish_publish);
        publish_content= (EditText) findViewById(R.id.share_publish_content);
        publish_img= (ImageView) findViewById(R.id.share_public_img);
        button_back.setOnClickListener(this);
        button_publish.setOnClickListener(this);
        publish_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_publish_back:
                this.finish();
                break;

            case R.id.share_publish_publish:
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
        }
    }
}
