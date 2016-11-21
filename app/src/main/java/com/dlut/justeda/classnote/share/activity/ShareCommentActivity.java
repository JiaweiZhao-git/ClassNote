package com.dlut.justeda.classnote.share.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.share.adapter.ShareCommentAdapter;
import com.dlut.justeda.classnote.share.data.Data;
import com.dlut.justeda.classnote.share.message.CommentMeg;
import com.dlut.justeda.classnote.share.util.Network;

import java.util.List;

/**
 * Created by chaomaer on 2016/11/8.
 */

public class ShareCommentActivity extends Activity {
    private List<CommentMeg> commentlist;
    public  ShareCommentAdapter shareCommentAdapter;
    private ListView listView;
    private int postId;
    private EditText commentedit;
    private Button commentsend;
    private String comentcontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postId=getIntent().getIntExtra("postId",1);
        setContentView(R.layout.share_comment_layout);
        initUI();
        initData();
        shareCommentAdapter=new ShareCommentAdapter(ShareCommentActivity.this,commentlist);
        listView.setAdapter(shareCommentAdapter);
        commentsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comentcontent=commentedit.getText().toString();
                if(!TextUtils.isEmpty(comentcontent)){
                    Data.getcommentMegList().add(new CommentMeg(comentcontent,Data.avatarurl,Data.username));
                    shareCommentAdapter.notifyDataSetChanged();
                    Network.commentTopic(postId,comentcontent);
                    commentedit.setText("");
                }

            }
        });
    }

    private void initData() {
        commentlist= Data.getcommentMegList();
    }

    private void initUI() {
        listView= (ListView) findViewById(R.id.share_comment_listview);
        commentedit= (EditText) findViewById(R.id.share_content_comment);
        commentsend= (Button) findViewById(R.id.share_commet_send);
    }
}
