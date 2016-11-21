package com.dlut.justeda.classnote.note.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 用于解析学生课表
 * Created by 赵佳伟 on 2016/11/18.
 */
public class HttpUtil {

    private String STUDENT_NUMBER = "http://139.199.157.33:5000/?student_number=";
    private String STUDENT_PWD = "&student_pwd=";

    private HttpURLConnection connection = null;

    private boolean canLogin;

    public void login(final String number, final String pwd, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                canLogin = false;
                try {
                    URL url = new URL(STUDENT_NUMBER + number + STUDENT_PWD + pwd);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(3000);
                    connection.setReadTimeout(3000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        canLogin=true;
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder reponse = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            reponse.append(line);
                        }
                        if (listener != null) {
                            listener.onFinish(reponse.toString());
                        }
                    }else{
                        //学号或者密码填错
                        if (listener != null) {
                            listener.onError("学号或者密码填错，请检查");
                        }
                    }
                }catch (IOException e) {
                    if (listener != null) {
                        listener.onError(e.toString());
                    }
                }finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }

}
