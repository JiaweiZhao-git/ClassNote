package com.dlut.justeda.classnote.share.message;


import java.util.List;

/**
 * Created by chaomaer on 2016/11/13.
 */

public class JsonTopiclist {
    public List<Topic> list;
    public class Topic{
        public int id;
        public String text;
        public int replyCount;
        public int readCount;
        public int star;
        public String imageUrl;
        public String imageName;
        public String createdAt;
        public String updatedAt;
        public int UserId;
        public UserInfo User;
    }
    public  class UserInfo{
        public String avatar;
        public String username;
    }
}
