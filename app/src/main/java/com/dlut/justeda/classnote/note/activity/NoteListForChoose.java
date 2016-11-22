package com.dlut.justeda.classnote.note.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.note.noteadapter.NoteItem;
import com.dlut.justeda.classnote.note.noteadapter.NoteListAdapter;
import com.dlut.justeda.classnote.note.util.BitmapUtil;
import com.dlut.justeda.classnote.note.util.ClassTime;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用于讓選擇的界面
 * 可以返回url
 * resultcode==100
 * Created by 赵佳伟 on 2016/11/22.
 */
public class NoteListForChoose extends Activity {

    private ImageButton back;
    private ListView listView;
    private HashMap<Integer, String> imagePath=new HashMap<>();
    private ClassTime currentTime;
    private List<NoteItem> noteItemsList = new ArrayList<>();
    private NoteListAdapter noteListAdapter;
    private String className = "其他";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_list_for_choose);

        initViews();

        initEvents();

        initBack();
    }

    private void initViews() {
        back = (ImageButton) findViewById(R.id.note_list_for_choose_back);
        listView = (ListView) findViewById(R.id.note_list_for_choose_list);

        Intent intent = getIntent();
        className = intent.getStringExtra("name");
    }

    private void initEvents() {
        Bitmap bitmap=null;
        BitmapUtil bitmapUtil = new BitmapUtil();
        String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/ClassNote/"+className+"/small";
        File smallDirs = new File(path);
        int i=0;
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
        noteListAdapter = new NoteListAdapter(NoteListForChoose.this, noteItemsList);
        listView.setAdapter(noteListAdapter);
    }

    private void initBack() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String smallPath = imagePath.get(position);
                String path = smallPath.substring(0, smallPath.length() - 26);
                String result = path + smallPath.substring(smallPath.length()-20, smallPath.length() - 4)+".jpg";
                Intent intent = new Intent();
                intent.putExtra("result", result);
                setResult(100,intent);
                finish();
            }
        });
    }
}
