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
import android.widget.TextView;
import android.widget.Toast;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.justpublic.contralwidget.ClassNameDialog;
import com.dlut.justeda.classnote.note.noteadapter.NoteItem;
import com.dlut.justeda.classnote.note.noteadapter.NoteListAdapter;
import com.dlut.justeda.classnote.note.ui.NoteArcMenu;
import com.dlut.justeda.classnote.note.ui.RenameDialog;
import com.dlut.justeda.classnote.note.util.BitmapUtil;
import com.dlut.justeda.classnote.note.util.ClassTime;

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
    private RenameDialog renameDialog;
    private ClassTime currentTime;

    private TextView titleTextView;
    private String title_text;
    private int cuttentPos = 0;

    private HashMap<Integer, String> imagePath=new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_fragment_notelist);

        initViews();

        initEvents();
    }

    private void initViews() {
        titleTextView = (TextView) findViewById(R.id.note_list_title_bar_text);
        Intent intent = getIntent();
        title_text = intent.getStringExtra("name");
        titleTextView.setText(title_text);

        slidingLayout = (SlidingLayout) findViewById(R.id.notelist_slidinglayout);
        noteArcMenu = (NoteArcMenu) findViewById(R.id.note_arcmenu);
        listView = (ListView) findViewById(R.id.note_fragment_pictures_list);

        noteItemsList.add(new NoteItem("...", R.drawable.note_back));
        Bitmap bitmap=null;
        BitmapUtil bitmapUtil = new BitmapUtil();
        String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/ClassNote/"+title_text+"/small";
        File smallDirs = new File(path);
        int i=1;
        if (smallDirs.exists()) {
            Log.e("small","exists");
            File[] files = smallDirs.listFiles();
            for (File file2 : files) {
                String imageName=file2.getAbsolutePath();
                imagePath.put(i++, imageName);
                bitmap=bitmapUtil.getLoacalBitmap(imageName);
                currentTime = new ClassTime();
                String weekName = currentTime.getWeekName(imageName.substring(imageName.length() - 16, imageName.length() - 4));
                String weekdayName = currentTime.getWeekDayName(imageName.substring(imageName.length() - 16, imageName.length() - 4));
                noteItemsList.add(new NoteItem(weekName+weekdayName,"en",bitmap));
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
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                cuttentPos = position;
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
                    String index = imagePath.get(position)
                            .substring(imagePath.get(position).length()-20,imagePath.get(position).length()-16);
                    Log.e("noteList", index);
                    if (!index.contains("NOTE")) {
                        Intent intent = new Intent(NoteListActivity.this, CreateNote.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(NoteListActivity.this, ReadNoteActivity.class);
                        intent.putExtra("path", imagePath.get(position));
                        startActivity(intent);
                    }

                }

            }
        });

        /**
         * 根据选中的不同位置pos
         * pos==1 重命名
         * pos==2 剪切
         * pos==3 删除
         * pos==4 复制
         * pos==5 粘贴
         * 来进行相应的操作
         */
        noteArcMenu.setOnMenuItemClickListener(new NoteArcMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, final int pos) {
                Log.e("arcMenu", String.valueOf(pos));
                if (pos == 1) {
                    renameDialog = new RenameDialog(NoteListActivity.this, "重命名", new RenameDialog.OnOKListener() {
                        @Override
                        public void getDialogValue(String str) {
                            NoteItem noteItem = noteItemsList.get(cuttentPos);
                            noteItem.setName(str);
                            noteItemsList.set(cuttentPos, noteItem);
                            noteListAdapter.notifyDataSetChanged();
                        }
                    });
                    renameDialog.show();
                } else if (pos == 2) {
                    //剪切有个接口
                    ClassNameDialog classNameDialog = new ClassNameDialog();
                    classNameDialog.showClassNameListToShear(
                            imagePath.get(cuttentPos),"剪切到：",NoteListActivity.this,NoteListActivity.this);
                    noteItemsList.remove(cuttentPos);
                    noteListAdapter.notifyDataSetChanged();
                    Toast.makeText(NoteListActivity.this,"剪切成功",Toast.LENGTH_SHORT).show();
                } else if (pos == 3) {
                    noteItemsList.remove(cuttentPos);
                    noteListAdapter.notifyDataSetChanged();
                } else if (pos == 4) {
                    //复制有个接口
                    ClassNameDialog classNameDialog = new ClassNameDialog();
                    classNameDialog.showClassNameListToCopy(
                            imagePath.get(cuttentPos),"复制到：",NoteListActivity.this,NoteListActivity.this);
                    Toast.makeText(NoteListActivity.this,"复制成功",Toast.LENGTH_SHORT).show();
                } else if (pos == 5) {
                    //粘贴还是得长按一点才行
                    Toast.makeText(NoteListActivity.this,"剪切板裏爲空~",Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**
         * 点击加号的话可以新增一个文本笔记
         */
        noteArcMenu.setOnCreateNoteListener(new NoteArcMenu.OnCreateNoteListener() {
            @Override
            public void onCreateNoteClick() {
                Intent intent = new Intent(NoteListActivity.this, CreateNote.class);
                intent.putExtra("name", title_text);
                startActivity(intent);
            }
        });
    }
}
