package com.gz.okhttp.listener;

/**
 * Created by Guo on 2017/2/18 0018.
 */

public class DisposeDataHandler<T> {
    public DisposeDataListener listener;
    public Class<T> clazz;
    public String mSource = null;


    public DisposeDataHandler(DisposeDataListener listener){
        this.listener = listener;
    }

    public DisposeDataHandler(DisposeDataListener listener, Class clazz){
        this.listener = listener;
        this.clazz = clazz;
    }

    public DisposeDataHandler(DisposeDataListener listener, String mSource) {
        this.mSource = mSource;
        this.listener = listener;
    }
}
