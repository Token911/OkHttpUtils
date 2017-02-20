package com.gz.okhttp.request;

import android.text.TextUtils;
import android.util.Log;

import com.gz.okhttp.OkHttpUtil;
import com.gz.okhttp.exception.OkHttpException;
import com.gz.okhttp.listener.DisposeDataListener;
import com.gz.okhttp.response.CommonJsonCallback;

import java.io.FileNotFoundException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by Guo on 2017/2/20 0020.
 */

public class RequestMethodUtil {
    private String REQUEST_METHOD = "";
    public final static String METHOD_GET = "GET";
    public final static String METHOD_POST = "POST";
    public final static String METHOD_DOWNLOAD = "DOWNLOAD";
    public final static String METHOD_UPLOAD = "UPLOAD";
    private String url = "";

    private RequestParams requestParams;


    public RequestMethodUtil() {
    }

    public RequestMethodUtil(String REQUEST_METHOD) {
        this.REQUEST_METHOD = REQUEST_METHOD;
    }


    public RequestMethodUtil url(String url) {
        this.url = url;
        return this;
    }

    public RequestMethodUtil addParams(String key, String value) {
        if (requestParams == null) {
            this.requestParams = RequestParams.builder();
        }
        this.requestParams.put(key, value);
        return this;
    }

    public RequestMethodUtil addParams(String key, Object value) throws FileNotFoundException {
        if (requestParams == null) {
            this.requestParams = RequestParams.builder();
        }
        this.requestParams.put(key, value);
        return this;
    }
    /**
     * 访问网络支持Post或者Get方式
     *@param callback extends okHttp.Callback override onFailure and onResponse
     */
    public void execute(Callback callback) {
        switch (REQUEST_METHOD) {
            case METHOD_GET:
                executeGet(callback);
                break;
            case METHOD_POST:
                executePost(callback);
                break;
            case METHOD_DOWNLOAD:
                executeDownload(callback);
                break;
            case METHOD_UPLOAD:

                executeUpload(callback);
                break;
            default:
                break;
        }
    }

    private void executeUpload(Callback callback) {
        if(url== null && TextUtils.isEmpty(url)){
            urlIsEmpty(callback);
            return;
        }
        Log.e("好吧:","我执行到这里了");
        Request request = CommonRequest.createMultiPostRequest(url, requestParams);
        Call call = OkHttpUtil.getmOkHttpClient().newCall(request);
        call.enqueue(callback);

    }

    /**下载文件*/
    private void executeDownload(Callback callback) {
        if(url== null && TextUtils.isEmpty(url)){
            urlIsEmpty(callback);
            return;
        }
        Request request = CommonRequest.createPostRequest(url, requestParams);
        Call call = OkHttpUtil.getmOkHttpClient().newCall(request);
        call.enqueue(callback);
    }

    /**通过Get方法请求网络*/
    private void executeGet(Callback callback) {
        if(url== null && TextUtils.isEmpty(url)){
            urlIsEmpty(callback);
            return;
        }
        Request request = CommonRequest.createPostRequest(url, requestParams);
        Call call = OkHttpUtil.getmOkHttpClient().newCall(request);
        call.enqueue(callback);
    }



    /**
     *通过Post方法请求网络
     */
    private void executePost(Callback callback){
        if(url== null && TextUtils.isEmpty(url)){
            urlIsEmpty(callback);
            return;
        }
        Request request = CommonRequest.createPostRequest(url, requestParams);
        Call call = OkHttpUtil.getmOkHttpClient().newCall(request);
        call.enqueue(callback);
    }
    /**
     *当前URL为空 抛出异常
     */
    private void urlIsEmpty(Callback callback) {
        CommonJsonCallback newCallback = (CommonJsonCallback)callback;
        DisposeDataListener disposeDataListener = newCallback.getmListener();
        if(disposeDataListener!= null){
            disposeDataListener.onDisposeFailure(new OkHttpException(0,"当前URL为空"));
        }
    }
}
