package com.dlut.justeda.classnote.share.data;

import com.dlut.justeda.classnote.share.message.CommentMeg;
import com.dlut.justeda.classnote.share.message.TopicMeg;
import com.dlut.justeda.classnote.share.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaomaer on 2016/11/14.
 */

public class Data {
    public static List<TopicMeg> list =new ArrayList<TopicMeg>();
    public static List<CommentMeg> commentMegList=new ArrayList<CommentMeg>();
    public static String username="helloworld";
    public static String avatarurl= Constant.DEFAULTAVATAR;
    public static List<TopicMeg> getTopicMeglist(){
        return list;
    }
    public static List<CommentMeg> getcommentMegList(){
        return commentMegList;
    }
    public static String getUsername(){
        return username;
    }
    public static String getAvatarurl(){
        return avatarurl;
    }
}
