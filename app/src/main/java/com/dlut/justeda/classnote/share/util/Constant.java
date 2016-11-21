package com.dlut.justeda.classnote.share.util;

import com.dlut.justeda.classnote.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by chaomaer on 2016/11/8.
 */

public class Constant {
    //public static final String URL="http://172.6.2.39:4040/";
    public static final String URL="http://119.29.55.243:4040/";
    //默认图片路径
    public static final String DEFAULTAVATAR=URL+"avatar.jpg";
    //注册
    public static final String REGISTER_URL=URL+"user-register/";
    //登录
    public static final String LOGIN_URL=URL+"user-login/";
    //发表说说
    //public static final String UPLOADIMG_URL=URL+"/user/upload/image/";
    public static final String UPLOADIMG_URL=URL+"forum/addTopic/";
    //获取说说列表
    public static final String GETTOPICLIST=URL+"forum/index/";
    //评论说说
    public static final String COMMENTTOPIC=URL+"forum/addComment";
    //获取评论列表
    public static final String GETCOMMENTLIST=URL+"forum/getComment";
    //点赞
    public static final String PRAISETOPIC=URL+"forum/getComment";
    //图片选项的设置
    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
             //  .showImageOnLoading(R.drawable.share_upload)// 在ImageView加载过程中显示图片
            .cacheInMemory(true) // 加载图片时会在内存中加载缓存
            .cacheOnDisk(true) // 加载图片时会在磁盘中加载缓存
            .displayer(new FadeInBitmapDisplayer(200)) // 设置用户加载图片task(这里是圆角图片显示)
            .build();
    //图片加载的Imageloader
    public static ImageLoader imageLoader = ImageLoader.getInstance();
}
