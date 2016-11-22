package com.dlut.justeda.classnote.note.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 构建笔记数据库实现笔记存储
 * 数据库名字是Notes
 * Created by 赵佳伟 on 2016/11/10.
 */
public class NoteDatabaseHelper extends SQLiteOpenHelper {

    public static final String NOTE="create table Notes("
            +"id integer primary key autoincrement,"
            +"note_name text,"
            +"classnote text)";

    private Context mcontext;

    public NoteDatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}
