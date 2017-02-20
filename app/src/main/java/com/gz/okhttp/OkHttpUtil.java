package com.gz.okhttp;

import com.gz.okhttp.request.RequestMethodUtil;

import okhttp3.OkHttpClient;

/**
 * Created by Guo on 2017/2/19 0019.
 */

public class OkHttpUtil {
    private static OkHttpClient mOkHttpClient;
    /**建议在Application中初始化*/
    public static void initClient(OkHttpClient okHttpClient){
        mOkHttpClient = okHttpClient;
    }

    /**
     *
     **/
    public static RequestMethodUtil get(){
        return new RequestMethodUtil(RequestMethodUtil.METHOD_GET);
    }

    public static RequestMethodUtil post(){
        return new RequestMethodUtil(RequestMethodUtil.METHOD_POST);
    }

    public static RequestMethodUtil upload(){
        return new RequestMethodUtil(RequestMethodUtil.METHOD_UPLOAD);
    }
    public static RequestMethodUtil download(){
        return new RequestMethodUtil(RequestMethodUtil.METHOD_DOWNLOAD);
    }


    public static OkHttpClient getmOkHttpClient() {
        return mOkHttpClient;
    }
}
