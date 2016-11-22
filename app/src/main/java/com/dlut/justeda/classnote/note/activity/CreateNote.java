package com.dlut.justeda.classnote.note.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dlut.justeda.classnote.App;
import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.justpublic.util.FileUtil;
import com.dlut.justeda.classnote.note.db.TextDatebaseHelper;
import com.dlut.justeda.classnote.note.util.ClassTime;

/**
 * 用于创建一个纯文本笔记
 * date是进来时创建的
 * 所以不具有更新性
 *update其实没有用到
 *
 * Created by 赵佳伟 on 2016/11/18.
 */
public class CreateNote extends Activity implements View.OnClickListener{

    private EditText title;
    private EditText content;

    private ImageButton title_back;
    private ImageButton title_save;

    private String className;
    private String date;
    private int flag = 0;

    private TextDatebaseHelper textDatebaseHelper = new TextDatebaseHelper(App.getInstance(), "Texts.db", null, 2);
    private SQLiteDatabase textDB = textDatebaseHelper.getWritableDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_create_note);

        initViews();

        initDate();
    }

    private void initViews() {
        Intent intent = getIntent();
        className = intent.getStringExtra("name");

        title = (EditText) findViewById(R.id.createnote_title);
        content = (EditText) findViewById(R.id.createnote_content);

        title_back = (ImageButton) findViewById(R.id.createnote_titlebar_back);
        title_save = (ImageButton) findViewById(R.id.createnote_titlebar_save);
    }

    private void initDate() {
        title_back.setOnClickListener(this);
        title_save.setOnClickListener(this);
        ClassTime classTime = new ClassTime();
        date = classTime.getDate();

        String[] selectionArgs = {date};
        Cursor cursor = textDB.query("Texts", null, "text_date=?", selectionArgs, null, null, null);
        if(cursor.getCount()==0){
            flag = 1;
            cursor.close();
        }else{
            if(cursor.moveToFirst()){
                do{
                    String textTitle = cursor.getString(cursor.getColumnIndex("text_title"));
                    title.setText(textTitle);
                    String textContent = cursor.getString(cursor.getColumnIndex("text_content"));
                    content.setText(textContent);
                    if(textTitle!=""&&textContent!="")
                        flag = 2;
                }while(cursor.moveToNext());

                cursor.close();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createnote_titlebar_back:
                finish();
                break;
            case R.id.createnote_titlebar_save:
                FileUtil fileUtil = new FileUtil();
                fileUtil.createTextFile(CreateNote.this,className,date);

                if(flag==1){
                    Log.e(getLocalClassName(), "insert");
                    insert();
                }
                else if(flag == 2){
                    update();
                }
                Intent intent = new Intent(CreateNote.this, NoteListActivity.class);
                intent.putExtra("name", className);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void insert() {
        ContentValues values = new ContentValues();
        values.put("text_class", className);
        values.put("text_date", date);
        values.put("text_title",title.getText().toString().trim());
        values.put("text_content",content.getText().toString().trim());
        textDB.insert("Texts", null, values);
        values.clear();
    }

    private void update() {
        ContentValues values = new ContentValues();
        values.put("text_title", title.getText().toString().trim());
        values.put("text_content",content.getText().toString().trim());
        if(textDB.update("Texts", values, "text_date=?", new String[] {date})>0){
            textDatebaseHelper.close();
        }else{
            textDB.close();
        }
        values.clear();
    }
}
