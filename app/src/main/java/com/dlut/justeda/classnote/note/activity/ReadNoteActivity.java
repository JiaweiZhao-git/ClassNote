package com.dlut.justeda.classnote.note.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.dlut.justeda.classnote.R;

/**
 * 笔记界面，由imageview和edittext组成
 * 同时具有数据库存储功能
 * Created by 赵佳伟 on 2016/11/10.
 */
public class ReadNoteActivity extends Activity {

    private PhotoView photoView;
    private EditText noteEditText;
    private Button saveNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_read_note);

        initViews();

        initEvents();
    }

    private void initViews() {
        photoView = (PhotoView) findViewById(R.id.note_sourcePicture);
        noteEditText = (EditText) findViewById(R.id.note_textOfPictures);
        saveNoteButton = (Button) findViewById(R.id.note_save_textOfPictures);
    }

    private void initEvents() {
        photoView.enable();
        Info info = photoView.getInfo();
        photoView.animaFrom(info);

        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), getLocalClassName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
