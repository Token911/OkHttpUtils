package com.gz.okhttp.listener;

/**
 * Created by Guo on 2017/2/20 0020.
 */

public interface DisposeFileListener extends DisposeDataListener {
    /**下载之前执行*/
    public void downloadFileBefore();
    /**下载进度*/
    public void downloadFileProgress(int progress,long totalProgress);
}
