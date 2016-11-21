package com.dlut.justeda.classnote.justpublic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.share.activity.SharePublishActivity;
import com.dlut.justeda.classnote.share.adapter.ShareImageAdapter;
import com.dlut.justeda.classnote.share.data.Data;
import com.dlut.justeda.classnote.share.message.TopicMeg;
import com.dlut.justeda.classnote.share.util.Network;

import java.util.List;

/**
 * 用于显示share主界面——之后可以跳转到其它activity
 * Created by 赵佳伟 on 2016/11/9.
 * chaomaer is creating
 */
public class ShareFragment extends Fragment implements View.OnClickListener ,View.OnTouchListener{
    private static final String TAG = "ShareFragment";
    private Button button_back,button_add;
    private View view;
    public static ShareImageAdapter shareImageAdapter;
    private List<TopicMeg> list;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.share_main_layout, container, false);
        Log.e(TAG, "onCreateView: ====>is called");
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.e(TAG, "onTouch: ---->down" );
                       // view.getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //view.getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        //view.getParent().getParent().getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return true;
            }
        });
        //        Network.register("123","123");
        Network.login("123","123");
        Network.getTopcilist(1,10);
        initUI();
        initData();
        shareImageAdapter=new ShareImageAdapter(getContext(),list);
        listView.setAdapter(shareImageAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list= Data.getTopicMeglist();
                shareImageAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }
    private void initData() {
        list= Data.getTopicMeglist();
    }

    private void initUI() {
        listView= (ListView)view.findViewById(R.id.share_listview);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.share_swiperefresh);
        button_add= (Button) view.findViewById(R.id.share_main_back);
        button_back= (Button) view.findViewById(R.id.share_main_add);
        button_add.setOnClickListener(this);
        button_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_main_back:
                break;
            case R.id.share_main_add:
                startActivity(new Intent(getContext(),SharePublishActivity.class));
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouch: ---->down" );
               // view.getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
               // view.getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_CANCEL:
              //  view.getParent().getParent().getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return true;
    }
    
}
