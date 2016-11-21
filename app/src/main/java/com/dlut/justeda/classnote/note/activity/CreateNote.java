package com.dlut.justeda.classnote.note.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.dlut.justeda.classnote.R;

/**
 * 用于创建一个纯文本笔记
 * Created by 赵佳伟 on 2016/11/18.
 */
public class CreateNote extends Activity {

    private EditText title;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_create_note);

        initViews();

        initDate();

    }

    private void initViews() {

        title = (EditText) findViewById(R.id.createnote_title);
        content = (EditText) findViewById(R.id.createnote_content);

    }

    private void initDate() {

    }
}
