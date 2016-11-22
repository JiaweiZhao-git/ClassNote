package com.dlut.justeda.classnote.note.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 赵佳伟 on 2016/11/22.
 */
public class TextDatebaseHelper extends SQLiteOpenHelper {

    public static final String TEXT="create table Texts("
            +"id integer primary key autoincrement,"
            +"text_class text,"
            +"text_date text,"
            +"text_title text,"
            +"text_content text)";

    private Context mcontext;

    public TextDatebaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TEXT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}
