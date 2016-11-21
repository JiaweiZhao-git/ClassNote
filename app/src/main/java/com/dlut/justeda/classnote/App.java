package com.dlut.justeda.classnote;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import okhttp3.OkHttpClient;

/**
 * Created by chaomaer on 2016/11/12.
 */

public class App extends Application {
    public static Bitmap avatarBitmap;
    @Override
    public void onCreate() {
        super.onCreate();
        avatarBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.share_defaultavatar);
        final CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(configuration);
    }
}
