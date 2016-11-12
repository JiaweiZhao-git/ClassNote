package com.dlut.justeda.classnote.share.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.share.adapter.ShareImageAdapter;
import com.dlut.justeda.classnote.share.adapter.Sharecontent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaomaer on 2016/11/7.
 */

public class ShareMainActivity extends Activity {
    private ShareImageAdapter shareImageAdapter;
    private List<Sharecontent> list;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_main_layout);
        initUI();
        initData();
        shareImageAdapter=new ShareImageAdapter(ShareMainActivity.this,list);
        listView.setAdapter(shareImageAdapter);

    }

    private void initData() {
        list=new ArrayList<Sharecontent>();
        Bitmap bm= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        list.add(new Sharecontent("hahahhahahaahah",bm));
        list.add(new Sharecontent("naianainaianainainaanainai",bm));
        list.add(new Sharecontent("helloworld",bm));
//        list.add(new Sharecontent("zhaojisha",bm));
//        list.add(new Sharecontent("lalalalalalal",bm));
//        list.add(new Sharecontent("naianainaianainainaanainai",bm));
//        list.add(new Sharecontent("hahahhahahaahah",bm));
//        list.add(new Sharecontent("naianainaianainainaanainai",bm));
//        list.add(new Sharecontent("hahahhahahaahah",bm));
//        list.add(new Sharecontent("naianainaianainainaanainai",bm));
    }

    private void initUI() {
        listView= (ListView) findViewById(R.id.share_listview);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.share_swiperefresh);
    }
}
