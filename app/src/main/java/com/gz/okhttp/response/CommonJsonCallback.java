package com.gz.okhttp.response;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.gz.okhttp.exception.OkHttpException;
import com.gz.okhttp.listener.DisposeDataHandler;
import com.gz.okhttp.listener.DisposeDataListener;
import com.gz.okhttp.util.JsonUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Guo on 2017/2/18 0018.
 */

public class CommonJsonCallback<T> implements Callback{
    private Handler mHandler;
    private DisposeDataListener mListener;
    private Class<T> mClazz;

    private final String SUCCESS_CODE = "200"; //请求成功
    private final String ERROR_MSG = "ERROR_MSG";
    private final String EMPTY_MSG = "EMPTY_MSG";
    private final String COOKIE_STORE = "Set_Cookie";

    private final int NETWORK_ERROR = -1;//网络异常
    private final int JSON_ERROR = -2;//JSON异常
    private final int OTHER_ERROR = -3;//其他异常

    public CommonJsonCallback(DisposeDataHandler disposeDataHandler) {
        this.mListener = disposeDataHandler.listener;
        this.mClazz = disposeDataHandler.clazz;
        this.mHandler = new Handler();
    }
    @Override
    public void onFailure(Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onDisposeFailure(new OkHttpException(NETWORK_ERROR,e));
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                handlerRespose(result);
            }
        });
    }
    /**处理返回信息*/
    private void handlerRespose(String result) {
        Log.e("TAG",result);
        if(null == result && TextUtils.isEmpty(result)){
           mListener.onDisposeFailure(new OkHttpException(OTHER_ERROR,EMPTY_MSG));
            return;
        }
        try {
            if (mClazz == null) {
                mListener.onDisposeSuccess(result);
            } else {
                T t = JsonUtil.parseJsonToBean(result,mClazz);

                if(t == null){
                    mListener.onDisposeFailure(new OkHttpException(JSON_ERROR,EMPTY_MSG));
                }else{
                    mListener.onDisposeSuccess(t);
                }
            }
        }catch (Exception e){
            mListener.onDisposeFailure(new OkHttpException(OTHER_ERROR,e));
            e.printStackTrace();
        }

    }

    public DisposeDataListener getmListener() {
        return mListener;
    }
}
