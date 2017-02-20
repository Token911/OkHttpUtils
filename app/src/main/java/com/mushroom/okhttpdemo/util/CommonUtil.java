package com.mushroom.okhttpdemo.util;

import android.content.Context;

import com.mushroom.okhttpdemo.App;

/**
 * Created by Administrator on 2017/2/3 0003.
 */

public class CommonUtil {

    public static Context getApplicationContext(){
        return App.getmContext();
    }

    /**
     * 在主线程运行
     * @param runnable 实现的Runnable代码在主线程运行
     * */
    public static void runOnUIThread(Runnable runnable){
        App.getmHandler().post(runnable);
    }
}
