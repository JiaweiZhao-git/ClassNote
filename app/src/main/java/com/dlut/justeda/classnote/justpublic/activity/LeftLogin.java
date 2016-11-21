package com.dlut.justeda.classnote.justpublic.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dlut.justeda.classnote.R;
import com.dlut.justeda.classnote.justpublic.util.FileUtil;
import com.dlut.justeda.classnote.note.db.ClassDatabaseHelper;
import com.dlut.justeda.classnote.note.util.HttpCallbackListener;
import com.dlut.justeda.classnote.note.util.HttpUtil;
import com.dlut.justeda.classnote.note.util.Lesson;
import com.dlut.justeda.classnote.note.util.StudentBean;
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
    private ProgressBar mProgressBar;
    private ClassDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private FileUtil fileUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.left_login);

        initViews();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpTask httpTask = new HttpTask();
                httpTask.execute();
            }
        });


    }

    private void initViews() {
        number = (EditText) findViewById(R.id.studentNumber);
        password = (EditText) findViewById(R.id.studentPassword);
        submit = (Button) findViewById(R.id.loginButton);
        httpUtil = new HttpUtil();
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        dbHelper = new ClassDatabaseHelper(this, "Courses.db", null, 2);
        db = dbHelper.getWritableDatabase();

        fileUtil = new FileUtil();
    }

    class HttpTask extends AsyncTask<String,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            num =number.getText().toString().trim();
            pwd = password.getText().toString().trim();
            Log.e(getLocalClassName(), "pre");
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... strings) {
            httpUtil.login(num,pwd, new HttpCallbackListener() {
                @Override
                public void onFinish(String reponse) {
                    parseJsonWithGSON(reponse);
                }

                @Override
                public void onError(String e) {
                    Toast.makeText(LeftLogin.this,e,Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e(getLocalClassName(), "post");
            mProgressBar.setVisibility(View.GONE);
        }

    }

    private void parseJsonWithGSON(String reponse) {
        Gson gson = new Gson();
        List<StudentBean> beanList = gson.fromJson(reponse, new TypeToken<List<StudentBean>>() {
        }.getType());

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


