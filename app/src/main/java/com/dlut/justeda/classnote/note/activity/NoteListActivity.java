package com.dlut.justeda.classnote.note.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.note.noteadapter.NoteItem;
import com.dlut.justeda.classnote.note.noteadapter.NoteListAdapter;
import com.dlut.justeda.classnote.note.ui.NoteArcMenu;
import com.dlut.justeda.classnote.note.util.BitmapUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * note界面的具体笔记列表
 * arcmenu的旋转问题以及新增下拉效果有些冲突
 * Created by 赵佳伟 on 2016/11/10.
 */
public class NoteListActivity extends Activity {

    private NoteArcMenu noteArcMenu;
    private ListView listView;
    private NoteListAdapter noteListAdapter;
    private List<NoteItem> noteItemsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_fragment_notelist);

        initViews();

        initEvents();
    }

    private void initViews() {
        noteArcMenu = (NoteArcMenu) findViewById(R.id.note_arcmenu);
        listView = (ListView) findViewById(R.id.note_fragment_pictures_list);

        noteItemsList.add(new NoteItem("...", R.drawable.note_item));
        //需要添加文件夹下的所有笔记界面——没有接口暂时模拟一下
        Bitmap bitmap=null;
        BitmapUtil bitmapUtil = new BitmapUtil();
        String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/ClassNote/其他/small";
        Log.e("path", path);
        File smallDirs = new File(path);
        if (smallDirs.exists()) {
            Log.e("small","exists");
            File[] files = smallDirs.listFiles();
            for (File file2 : files) {
                String imageName=file2.getAbsolutePath();
                bitmap=bitmapUtil.getLoacalBitmap(imageName);
                noteItemsList.add(new NoteItem("aaaa","en",bitmap));
            }
        }
        noteListAdapter = new NoteListAdapter(NoteListActivity.this, noteItemsList);
        listView.setAdapter(noteListAdapter);
    }

    private void initEvents() {

        /**
         * 监听listview的滑动，如果滑动将菜单收起
         */
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (noteArcMenu.isOpen()) {
                    noteArcMenu.toggleMenu(600);
                }

            }
        });

        /**
         * 监听listview的长按事件，长按菜单弹出，更符合用户选中操作的顺序
         * bug:view值传错、导致选中item的内容会转（应该修改view的值）
         * 状态：待修复
         */
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                noteArcMenu.onClick(view);
                return false;
            }
        });

        /**
         * 1、...代表返回键
         * 2、其它的点击进入笔记界面
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    finish();
                }else{
                    Intent intent = new Intent(NoteListActivity.this, ReadNoteActivity.class);
                    startActivity(intent);
                }

            }
        });

        /**
         * 根据选中的不同位置pos
         * 来进行相应的操作
         */
        noteArcMenu.setOnMenuItemClickListener(new NoteArcMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {

            }
        });
    }
}
