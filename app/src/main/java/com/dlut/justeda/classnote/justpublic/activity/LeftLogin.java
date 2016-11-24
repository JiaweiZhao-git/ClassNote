package com.dlut.justeda.classnote.justpublic.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.justpublic.util.FileUtil;
import com.dlut.justeda.classnote.note.db.ClassDatabaseHelper;
import com.dlut.justeda.classnote.note.util.HttpCallbackListener;
import com.dlut.justeda.classnote.note.util.HttpUtil;
import com.dlut.justeda.classnote.note.util.Lesson;
import com.dlut.justeda.classnote.note.util.StudentBean;
import com.dlut.justeda.classnote.share.util.Network;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 点击“点击登陆时”登陆
 * 网络堵塞线程
 * 需要增加 加载界面
 * 需要处理加载后跳转
 * Created by 赵佳伟 on 2016/11/19.
 */
public class LeftLogin extends Activity {

    private EditText number;
    private EditText password;
    private String num;
    private String pwd;
    private Button submit;
    private HttpUtil httpUtil;
    private ClassDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private FileUtil fileUtil;
    private  AlertDialog alertDialog;
    private android.os.Handler handler;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.left_login);

        initViews();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog=new ProgressDialog(LeftLogin.this);
                alertDialog.setMessage("正在登陆，请稍等....");
                alertDialog.show();
                alertDialog.setCancelable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        num =number.getText().toString().trim();
                        pwd = password.getText().toString().trim();
                        httpUtil.login(num,pwd, new HttpCallbackListener() {
                            @Override
                            public void onFinish(String reponse) {
                                if (reponse.contains("none")) {
                                    handler.sendEmptyMessage(0);
                                }else{
                                    parseJsonWithGSON(reponse);
                                    handler.sendEmptyMessage(1);
                                    SharedPreferences.Editor editor=  getSharedPreferences("user",MODE_PRIVATE).edit();
                                    editor.putString("name",number.getText().toString());
                                    editor.putString("password",password.getText().toString());
                                    Network.register(number.getText().toString(),password.getText().toString());
                                    editor.commit();
                                }

                            }

                            @Override
                            public void onError(String e) {
                                handler.sendEmptyMessage(-1);
                            }
                        });
                    }
                }).start();
            }
        });


    }

    private void initViews() {
        number = (EditText) findViewById(R.id.studentNumber);
        password = (EditText) findViewById(R.id.studentPassword);
        submit = (Button) findViewById(R.id.loginButton);
        httpUtil = new HttpUtil();
        dbHelper = new ClassDatabaseHelper(this, "Courses.db", null, 2);
        db = dbHelper.getWritableDatabase();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case -1:
                        alertDialog.dismiss();
                        Toast.makeText(LeftLogin.this,"check network",Toast.LENGTH_SHORT).show();
                    case 0:
                        alertDialog.dismiss();
                        Toast.makeText(LeftLogin.this,"login failure",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        alertDialog.dismiss();
                        startActivity(new Intent(LeftLogin.this,MainActivity.class));
                }
                super.handleMessage(msg);
            }
        };
        fileUtil = new FileUtil();
    }
    private void parseJsonWithGSON(String reponse) {
        Gson gson = new Gson();
        List<StudentBean> beanList = gson.fromJson(reponse, new TypeToken<List<StudentBean>>(){}.getType());
        ContentValues values = new ContentValues();
        Cursor cursor = db.rawQuery("select * from Courses", null);
        if (cursor.getCount() == 0) {
            for (StudentBean bean : beanList) {
                Log.e("login", bean.getName());
                values.put("name",bean.getName());
                fileUtil.createClassFile(bean.getName());
                List<Lesson> lessonList = gson.fromJson(bean.getLessson(), new TypeToken<List<Lesson>>() {
                }.getType());
                Log.e("lesson", String.valueOf(lessonList.size()));
                values.put("point",lessonList.size());
                int indexNumber=0;
                for (Lesson lesson : lessonList) {
                    String index = "";
                    Log.e("lesson", lesson.getWeek());
                    values.put("week"+index,lesson.getWeek());

                    Log.e("lesson", String.valueOf(lesson.getWeekDay()));
                    values.put("weekday"+index,lesson.getWeekDay());

                    Log.e("lesson", String.valueOf(lesson.getSection()));
                    values.put("section"+index,lesson.getSection());

                    Log.e("lesson", String.valueOf(lesson.getDuration()));
                    values.put("duration"+index,lesson.getDuration());

                    Log.e("lesson", lesson.getLocation());
                    values.put("location" + index, lesson.getLocation());

                    index = String.valueOf(indexNumber+1);
                }
                db.insert("Courses", null, values);
                values.clear();
            }
        }
    }
}


