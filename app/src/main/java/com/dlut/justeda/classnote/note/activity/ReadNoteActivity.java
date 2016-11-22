package com.dlut.justeda.classnote.note.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.dlut.justeda.classnote.App;
import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.note.db.NoteDatabaseHelper;
import com.dlut.justeda.classnote.note.util.BitmapUtil;

/**
 * 笔记界面，由imageview和edittext组成
 * 寻找一下好看的笔记界面
 * 同时具有数据库存储功能
 * 应该有一个默认的笔记照片，运行点击刷新，防止出现照片没找到
 * Created by 赵佳伟 on 2016/11/10.
 */
public class ReadNoteActivity extends Activity {

    private PhotoView photoView;
    private EditText noteEditText;
    private Button saveNoteButton;

    private BitmapUtil bitmapUtil;
    private Bitmap bitmap = null;

    private NoteDatabaseHelper noteDBHelper = new NoteDatabaseHelper(App.getInstance(), "Notes.db", null, 2);
    private SQLiteDatabase noteDB = noteDBHelper.getWritableDatabase();
    private String note_name;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_read_note);

        initViews();

        initEvents();

        initDB();
    }

    private void initViews() {
        photoView = (PhotoView) findViewById(R.id.note_sourcePicture);
        noteEditText = (EditText) findViewById(R.id.note_textOfPictures);
        saveNoteButton = (Button) findViewById(R.id.note_save_textOfPictures);

    }

    private void initDB(){
        String[] selectionArgs = {note_name};
        Cursor cursor = noteDB.query("Notes", null, "note_name=?", selectionArgs, null, null, null);
        if(cursor.getCount()==0){
            flag = 1;
            cursor.close();
        }else{
            if(cursor.moveToFirst()){
                do{
                    String note1 = cursor.getString(cursor.getColumnIndex("classnote"));
                    noteEditText.setText(note1);
                    if(note1!="")
                        flag = 2;
                }while(cursor.moveToNext());

                cursor.close();
            }
        }
    }

    private void initEvents() {
        photoView.enable();
        Info info = photoView.getInfo();
        photoView.animaFrom(info);
        Intent intent = getIntent();
        String smallPath = intent.getStringExtra("path");
        String path = smallPath.substring(0, smallPath.length() - 26);
        new BitmapTask().execute(path + smallPath.substring(smallPath.length()-20, smallPath.length() - 4)+".jpg");
        note_name = smallPath.substring(smallPath.length() - 20, smallPath.length() - 4);
        Log.e(getLocalClassName(), note_name);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1){
                    insert();
                }
                else if(flag == 2){
                    update();
                }
            }
        });
    }

    private void insert() {
        ContentValues values = new ContentValues();
        values.put("note_name", note_name);
        values.put("classnote", noteEditText.getText().toString().trim());
        noteDB.insert("Notes", null, values);
        values.clear();
    }

    private void update() {
        ContentValues values = new ContentValues();
        values.put("classnote", noteEditText.getText().toString().trim());
        if(noteDB.update("Notes", values, "note_name=?", new String[] {note_name})>0){
            noteDBHelper.close();
        }else{
            noteDB.close();
        }
        values.clear();
    }

    class BitmapTask extends AsyncTask<String,Void,Bitmap>{


        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            bitmapUtil = new BitmapUtil();
            bitmap = bitmapUtil.getLoacalBitmap(url);
            publishProgress();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            photoView.setImageBitmap(bitmap);
        }
    }
}
