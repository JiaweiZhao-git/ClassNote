package com.dlut.justeda.classnote.justpublic.contralwidget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.view.View;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.note.activity.NoteListForChoose;
import com.dlut.justeda.classnote.note.db.ClassDatabaseHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * 用来显示课程名字的dialog
 * 在share和note中显示
 * requestcode==110
 * Created by 赵佳伟 on 2016/11/22.
 */
public class ClassNameDialog {

    public void showClassNameListDialog(View view, String title,final Activity activity, final Context context) {

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
        builder.setTitle(title);
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

    public void showClassNameListToShear(final String path, String title, final Activity activity, final Context context){
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
        builder.setTitle(title);
        builder.setIcon(R.drawable.ic_launcher);
        final String[] items = new String[sum+1];
        items[0]="其他";
        for(int i=1;i<sum+1;i++) {
            items[i] = names.get(i-1);
        }
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                String imageFromPath = path.substring(0,path.length()-26)+path.substring(path.length()-20,path.length()-4)+".jpg";
                String imageToPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ClassNote/"+items[position]+"/"
                        +path.substring(path.length()-20,path.length()-4)+".jpg";
                String imageSmallFromPath = path;
                String imageSmallToPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ClassNote/"+items[position]+"/"
                        +"small/"+path.substring(path.length()-20,path.length()-4)+".png";

                shearFile(imageFromPath,imageToPath);
                shearFile(imageSmallFromPath,imageSmallToPath);

            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    public void showClassNameListToCopy(final String path, String title, final Activity activity, final Context context){
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
        builder.setTitle(title);
        builder.setIcon(R.drawable.ic_launcher);
        final String[] items = new String[sum+1];
        items[0]="其他";
        for(int i=1;i<sum+1;i++) {
            items[i] = names.get(i-1);
        }
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                String imageFromPath = path.substring(0,path.length()-26)+path.substring(path.length()-20,path.length()-4)+".jpg";
                String imageToPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ClassNote/"+items[position]+"/"
                        +path.substring(path.length()-20,path.length()-4)+".jpg";
                String imageSmallFromPath = path;
                String imageSmallToPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ClassNote/"+items[position]+"/"
                        +"small/"+path.substring(path.length()-20,path.length()-4)+".png";

                copyFile(imageFromPath,imageToPath);
                copyFile(imageSmallFromPath,imageSmallToPath);

            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    private void shearFile(final String imageFromPath, final String imageToPath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File src = new File(imageFromPath);
                File dest = new File(imageToPath);

                try {
                    InputStream is =new FileInputStream(src);

                    OutputStream os =new FileOutputStream(dest);

                    byte[] flush=new byte[1024];
                    int len=0;
                    while(-1!=(len=is.read(flush))){
                        os.write(flush,0,len);
                    }
                    os.flush();
                    os.close();
                    is.close();

                    src.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void copyFile(final String imageFromPath, final String imageToPath){
        new Thread(new Runnable() {
            @Override
            public void run() {
                File src = new File(imageFromPath);
                File dest = new File(imageToPath);

                try {
                    InputStream is =new FileInputStream(src);

                    OutputStream os =new FileOutputStream(dest);

                    byte[] flush=new byte[1024];
                    int len=0;
                    while(-1!=(len=is.read(flush))){
                        os.write(flush,0,len);
                    }
                    os.flush();
                    os.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
