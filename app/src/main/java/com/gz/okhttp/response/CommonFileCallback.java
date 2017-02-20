package com.gz.okhttp.response;

import android.os.Handler;

import com.gz.okhttp.exception.OkHttpException;
import com.gz.okhttp.listener.DisposeDataHandler;
import com.gz.okhttp.listener.DisposeFileListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Guo on 2017/2/20 0020.
 */

public class CommonFileCallback implements Callback {
    private Handler mHandler;
    private DisposeFileListener mListener;
    private Class<?> mClazz;
    private String filePath;

    private final String SUCCESS_CODE = "200"; //请求成功
    private final String ERROR_MSG = "ERROR_MSG";
    private final String EMPTY_MSG = "EMPTY_MSG";
    private final String COOKIE_STORE = "Set_Cookie";

    private final int NETWORK_ERROR = -1;//网络异常
    private final int JSON_ERROR = -2;//JSON异常
    private final int OTHER_ERROR = -3;//其他异常
    private int currentLength;
    private long sumLength;

    public CommonFileCallback(DisposeDataHandler disposeDataHandler) {
        this.mListener = (DisposeFileListener) disposeDataHandler.listener;
        this.mHandler = new Handler();
        this.filePath = disposeDataHandler.mSource;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        final IOException exception = e;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onDisposeFailure(exception);
            }
        });

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final File file = handleResponse(response);
        if (file != null && file.exists()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onDisposeSuccess(file);
                }
            });
        } else {
            mListener.onDisposeFailure(new OkHttpException(OTHER_ERROR, "下载文件为空或文件不存在"));
        }
    }

    /**
     * 此时还在子线程中，不则调用回调接口
     *
     * @param response
     * @return
     */
    private File handleResponse(Response response) {
        if (response == null) {
            return null;
        }

        InputStream inputStream = null;
        File file = null;
        FileOutputStream fos = null;
        byte[] buffer = new byte[2048];
        int length = -1;
        currentLength = 0;
        sumLength = 0;
        try {
            file = new File(filePath);
            fos = new FileOutputStream(file);
            inputStream = response.body().byteStream();
            sumLength = response.body().contentLength();
            //在下载之前在UI线程回调该方法
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.downloadFileBefore();

                }
            });
            while ((length = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
                currentLength += length;
                //在UI线程返回进度
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mListener.downloadFileProgress(currentLength, sumLength);
                    }
                });
            }
            fos.flush();
        } catch (Exception e) {
            file = null;
        } finally {
            try {
                fos.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
