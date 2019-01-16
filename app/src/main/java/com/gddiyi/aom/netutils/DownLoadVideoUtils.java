package com.gddiyi.aom.netutils;

import android.util.Log;

public class DownLoadVideoUtils implements Runnable {
    String videoPath;
    DownloadUtil.OnDownloadListener downloadListener;
    static final String TAG="DownLoadVideoThread";

    public void setDownloadListener(DownloadUtil.OnDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    public DownLoadVideoUtils(String videoPath, DownloadUtil.OnDownloadListener downloadListener) {
        this.videoPath = videoPath;
        this.downloadListener = downloadListener;
    }

    public DownloadUtil.OnDownloadListener getDownloadListener() {

        return downloadListener;

    }

    @Override
    public void run() {
        if (downloadListener==null){
            downloadListener=new DownloadUtil.OnDownloadListener() {
                @Override
                public void onDownloadSuccess() {
                    Log.i(TAG, "onDownloadSuccess: ");
                }

                @Override
                public void onDownloading(int progress) {
                    Log.i(TAG, "onDownloading: "+progress);
                }

                @Override
                public void onDownloadFailed() {
                    Log.i(TAG, "onDownloadFailed: ");

                }
            };
        }
        DownloadUtil.get().download(videoPath,"ad",downloadListener);
    }

    public DownLoadVideoUtils(String videoPath) {
        this.videoPath = videoPath;

    }
    public DownLoadVideoUtils() {

    }
    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoPath() {

        return videoPath;
    }
}
