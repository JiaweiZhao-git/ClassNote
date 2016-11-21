package com.dlut.justeda.classnote.share.message;

/**
 * Created by chaomaer on 2016/11/14.
 */

public class TopicMeg {
    public String avatarurl;
    public String username;
    public String imageUrl;
    public String topicContent;
    public int id;

    public TopicMeg(String avatarurl, String imageUrl, String topicContent, String username) {
        this.avatarurl = avatarurl;
        this.imageUrl = imageUrl;
        this.topicContent = topicContent;
        this.username = username;
    }
    public TopicMeg(int id,String avatarurl, String imageUrl, String topicContent, String username) {
        this.avatarurl = avatarurl;
        this.imageUrl = imageUrl;
        this.id=id;
        this.topicContent = topicContent;
        this.username = username;
    }
}
