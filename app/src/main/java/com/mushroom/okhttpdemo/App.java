package com.mushroom.okhttpdemo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.gz.okhttp.OkHttpUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/2/3 0003.
 */

public class App extends Application {
    public static Handler mHandler;
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        if(mContext == null){
            mContext = this;
        }

        if(mHandler == null){
            mHandler = new Handler();
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(1000, TimeUnit.SECONDS);
        builder.writeTimeout(1000,TimeUnit.SECONDS);
        builder.readTimeout(1000,TimeUnit.SECONDS);
        OkHttpClient build = builder.build();
        OkHttpUtil.initClient(build);
    }

    public static Handler getmHandler() {
        return mHandler;
    }

    public static Context getmContext() {
        return mContext;
    }
}
