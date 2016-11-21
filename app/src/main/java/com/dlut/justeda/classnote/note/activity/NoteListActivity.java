package com.dlut.justeda.classnote.note.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.note.noteadapter.NoteItem;
import com.dlut.justeda.classnote.note.noteadapter.NoteListAdapter;
import com.dlut.justeda.classnote.note.ui.NoteArcMenu;
import com.dlut.justeda.classnote.note.util.BitmapUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lib.homhomlib.design.SlidingLayout;

/**
 * note界面的具体笔记列表
 * arcmenu的旋转问题以及新增下拉效果有些冲突
 * 由于主界面拍照时有时候调用改名、压缩慢的bug
 * 需要在下拉刷新时在执行一下，防止有些没有加载出来
 * Created by 赵佳伟 on 2016/11/10.
 */
public class NoteListActivity extends Activity {

    private NoteArcMenu noteArcMenu;
    private ListView listView;
    private NoteListAdapter noteListAdapter;
    private List<NoteItem> noteItemsList = new ArrayList<>();
    private SlidingLayout slidingLayout;

    private HashMap<Integer, String> imagePath=new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_fragment_notelist);

        initViews();

        initEvents();
    }

    private void initViews() {
        slidingLayout = (SlidingLayout) findViewById(R.id.notelist_slidinglayout);
        noteArcMenu = (NoteArcMenu) findViewById(R.id.note_arcmenu);
        listView = (ListView) findViewById(R.id.note_fragment_pictures_list);

        noteItemsList.add(new NoteItem("...", R.drawable.note_item));
        Bitmap bitmap=null;
        BitmapUtil bitmapUtil = new BitmapUtil();
        String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/ClassNote/其他/small";
        File smallDirs = new File(path);
        int i=1;
        if (smallDirs.exists()) {
            Log.e("small","exists");
            File[] files = smallDirs.listFiles();
            for (File file2 : files) {
                String imageName=file2.getAbsolutePath();
                imagePath.put(i++, imageName);
                bitmap=bitmapUtil.getLoacalBitmap(imageName);
                noteItemsList.add(new NoteItem("aaaa","en",bitmap));
            }
        }
        noteListAdapter = new NoteListAdapter(NoteListActivity.this, noteItemsList);
        listView.setAdapter(noteListAdapter);
    }

    private void initEvents() {

        /**
         * 监听slidingLayout的滑动，如果滑动将菜单收起
         */

        slidingLayout.setSlidingListener(new SlidingLayout.SlidingListener() {
            @Override
            public void onSlidingOffset(View view, float delta) {

            }

            @Override
            public void onSlidingStateChange(View view, int state) {
                if (noteArcMenu.isOpen()) {
                    noteArcMenu.toggleMenu(600);
                }
            }

            @Override
            public void onSlidingChangePointer(View view, int pointerId) {

            }
        });

        /**
         * 监听listview的长按事件，长按菜单弹出，更符合用户选中操作的顺序
         *
         */
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                noteArcMenu.onLongClick(view);
                return true;
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
                    intent.putExtra("path", imagePath.get(position));
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
                Log.e("arcMenu", String.valueOf(pos));
            }
        });
        /**
         * 点击加号的话可以新增一个文本笔记
         */
        noteArcMenu.setOnCreateNoteListener(new NoteArcMenu.OnCreateNoteListener() {
            @Override
            public void onCreateNoteClick() {
                Intent intent = new Intent(NoteListActivity.this, CreateNote.class);
                startActivity(intent);
            }
        });
    }
}
