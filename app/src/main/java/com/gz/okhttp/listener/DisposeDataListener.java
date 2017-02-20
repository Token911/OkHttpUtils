package com.gz.okhttp.listener;

import okhttp3.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Guo on 2017/2/18 0018.
 */

public interface DisposeDataListener<T> {
    /** UI线程执行*/
    void onDisposeFailure(Exception e);
    /**
     * UI线程执行
     */
    void onDisposeSuccess(T t);
}
