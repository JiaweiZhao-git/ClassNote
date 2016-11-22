package com.dlut.justeda.classnote.justpublic.contralwidget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.note.activity.NoteListForChoose;
import com.dlut.justeda.classnote.note.db.ClassDatabaseHelper;

import java.util.HashMap;

/**
 * 用来显示课程名字的dialog
 * 在share和note中显示
 * requestcode==110
 * Created by 赵佳伟 on 2016/11/22.
 */
public class ClassNameDialog {

    public void showClassNameListDialog(View view, final Activity activity, final Context context) {

        ClassDatabaseHelper dbHelper = new ClassDatabaseHelper(context, "Courses.db", null, 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Courses", null, null, null, null, null, null);
        int sum = 0;
        HashMap<Integer, String> names = new HashMap<>();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                names.put(sum, name);
                sum++;
            } while (cursor.moveToNext());
        }
        cursor.close();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请选择你要选择的课程：");
        builder.setIcon(R.drawable.ic_launcher);
        final String[] items = new String[sum+1];
        items[0]="其他";
        for(int i=1;i<sum+1;i++) {
            items[i] = names.get(i-1);
        }
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                Intent intent = new Intent(context, NoteListForChoose.class);
                intent.putExtra("name", items[position]);
                activity.startActivityForResult(intent,110);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

}
