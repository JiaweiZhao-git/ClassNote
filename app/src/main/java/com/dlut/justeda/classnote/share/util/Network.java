package com.dlut.justeda.classnote.share.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.dlut.justeda.classnote.share.data.Data;
import com.dlut.justeda.classnote.share.message.CommentMeg;
import com.dlut.justeda.classnote.share.message.JsonCommentlist;
import com.dlut.justeda.classnote.share.message.JsonTopiclist;
import com.dlut.justeda.classnote.share.message.JsonuserInfo;
import com.dlut.justeda.classnote.share.message.TopicMeg;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;

/**
 * Created by chaomaer on 2016/11/8.
 */

public class Network {
    /**
     * 根据url从网络端获取图片
     * @param imgurl
     * @return
     */
    public static Bitmap getBitmapFromUrl(String imgurl){
        InputStream is=null;
        Bitmap bitmap=null;
        try {
            URL url=new URL(imgurl);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            is=httpURLConnection.getInputStream();
            bitmap=BitmapFactory.decodeStream(is);
            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
    //判断是否登录成功
    public static void register(String username,String password){
        OkHttpUtils
                .post()
                .url(Constant.REGISTER_URL)
                .addParams("name", username)
                .addParams("pwd", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("ErrorInfo", e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("response",response);
                    }
                });
    }
    public static void login(String username,String password){
        OkHttpUtils
                .post()
                .url(Constant.LOGIN_URL)
                .addParams("name", username)
                .addParams("pwd", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("loginErrorInfo", e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //Log.e("loginresponse",response);
                        JsonuserInfo jsonuserInfo=new Gson().fromJson(response,JsonuserInfo.class);
                        Data.username=jsonuserInfo.username;
                        Data.avatarurl=jsonuserInfo.avatar;
//                        Log.e(" Data.username",Data.username);
//                        Log.e(" Data.username",Data.avatarurl);
                    }
                });
    }

    /**
     * 发表一个说说，包括一个文本，和一张图片
     * @param file
     * @param content
     */
    public static void publish(File file, String content ){
        OkHttpUtils.post()
                .url(Constant.UPLOADIMG_URL)
                //.addFile("file",file.getName(),file)
                .addParams("text",content)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("publishErrorInfo",e.toString());

                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("publishresponse",response);
                    }
                });
    }
    /**
     * 获取说说的列表
     */
    public static  void getTopcilist(int page,int count){
        OkHttpUtils.get()
                .url(Constant.GETTOPICLIST)
                .addParams("page",String.valueOf(page))
                .addParams("count",String.valueOf(count))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("getTopcilistErrorInfo",e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("getTopcilistreponse",response);
                        List<JsonTopiclist.Topic> templist;
                        TopicMeg topicMeg;
                        JsonTopiclist topiclist=new Gson().fromJson(response,JsonTopiclist.class);
                        templist=topiclist.list;
                        Data.getTopicMeglist().clear();
                                for(int i=0;i<templist.size();i++){
                                    topicMeg=new TopicMeg(templist.get(i).id,templist.get(i).User.avatar,templist.get(i).imageUrl,
                                            templist.get(i).text,templist.get(i).User.username);
                                    Data.getTopicMeglist().add(topicMeg);
                                }
                    }
                });
    }
    /**
     *添加评论
     */
    public static void  commentTopic(int id,String commentcontent){
        OkHttpUtils.get()
                .url(Constant.COMMENTTOPIC)
                .addParams("PostId",String.valueOf(id))
                .addParams("text",commentcontent)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("commentTopicError",e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("commentTopicResponse",response);
                    }
                });
    }
    /**
     * 获取评论列表
     */
    public static void getcommentlist(int id){
        OkHttpUtils.get()
                .url(Constant.GETCOMMENTLIST)
                .addParams("id",String.valueOf(id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("getcommentlistError",e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("getcommentlist",response);
                        List<JsonCommentlist.JsonComment> templist;
                        CommentMeg commentMeg;
                        JsonCommentlist jsonCommentlist=new Gson().fromJson(response,JsonCommentlist.class);
                        templist=jsonCommentlist.list;
                        Data.getcommentMegList().clear();
                        for(int i=0;i<templist.size();i++){
                            commentMeg=new CommentMeg(templist.get(i).text,
                                    templist.get(i).User.avatar,templist.get(i).User.username);
                            Data.getcommentMegList().add(commentMeg);
                        }

                    }
                });
    }
}
