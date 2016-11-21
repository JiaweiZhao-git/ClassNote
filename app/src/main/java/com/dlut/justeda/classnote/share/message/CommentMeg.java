package com.dlut.justeda.classnote.share.message;

/**
 * Created by chaomaer on 2016/11/14.
 */

public class CommentMeg {
    public String commentcontent;
    public String commentername;
    public String commenteravatarurl;

    public CommentMeg(String commentcontent, String commenteravatarurl, String commentername) {
        this.commentcontent = commentcontent;
        this.commenteravatarurl = commenteravatarurl;
        this.commentername = commentername;
    }
}
