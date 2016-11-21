package com.dlut.justeda.classnote.share.adapter;
/**
 * Created by chaomaer on 2016/11/7.
 */

public class Sharecontent {
    public String myimgurl;
    public String myname;
    public String sharecontent;
    public String shareimgurl;
    public int praisetime;
    public int commenttime;

    public Sharecontent(String myimgurl, String myname, String sharecontent, String shareimgurl) {
        this.myimgurl = myimgurl;
        this.myname = myname;
        this.sharecontent = sharecontent;
        this.shareimgurl = shareimgurl;
    }
}
