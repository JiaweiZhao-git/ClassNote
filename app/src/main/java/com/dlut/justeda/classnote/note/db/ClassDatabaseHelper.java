package com.dlut.justeda.classnote.note.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 用于存储学生课程数据
 * point——每周几节课
 * week——以字符串形式存哪几周上课
 * Created by 赵佳伟 on 2016/11/20.
 */
public class ClassDatabaseHelper extends SQLiteOpenHelper {

    private String STDUENT_COURSES ="create table Courses ("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"point integer,"
            +"week text,"
            +"weekday integer,"
            +"section integer,"
            +"duration integer,"
            +"location text,"
            +"week2 text,"
            +"weekday2 integer,"
            +"section2 integer,"
            +"duration2 integer,"
            +"location2 text,"
            +"week3 text,"
            +"weekday3 integer,"
    +"section3 integer,"
            +"duration3 integer,"
            +"location3 text)";

    private Context mContext;

    public ClassDatabaseHelper(Context context, String name,
                               SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(STDUENT_COURSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
