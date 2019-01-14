package com.gddiyi.aom.model;

import android.util.SparseArray;

/**
 * 该类是VideoPrensenter的辅助类
 */

public class VideoPlayAll<E> {
    int count;
    SparseArray<PlayData> saveData;
    static VideoPlayAll instance;

    public void setSaveData(SparseArray<PlayData> saveData) {
        this.saveData = saveData;
    }

    public SparseArray<PlayData> getSaveData() {

        return saveData;
    }

    public void setCount(int count) {
        this.count = count;
        saveData = new SparseArray<>(count);
    }

    public int getCount() {

        return count;
    }

    public PlayData get(int index) {
        PlayData playData = this.getSaveData().get(index);
        return playData;
    }

    public PlayData put(int index, PlayData playData) {
        this.getSaveData().put(index, playData);
        return playData;
    }
  //排序的本地视频路径
    public String getNetVideoPath(int sortIndex) {
        String videoPath = null;
        if (getSaveData().get(sortIndex) != null) {
            videoPath = this.getSaveData().get(sortIndex).getNetVideoPath();
        }

        return videoPath;
    }

    public String getLocalVideoPath(int sortIndex) {
        String videoPath = null;
        if (getSaveData().get(sortIndex) != null) {
            videoPath = this.getSaveData().get(sortIndex).getLocalPath();
        }
        return videoPath;
    }

    public String getVideoName(int sortIndex) {
        String videoPath = null;
        if (getSaveData().get(sortIndex) != null) {
            videoPath = this.getSaveData().get(sortIndex).getVideoName();
        }
        return videoPath;
    }

    private VideoPlayAll() {

    }
 //简单单例，无同步
    public static VideoPlayAll getInstance() {
        if (instance == null) {
            instance = new VideoPlayAll();
        }
        return instance;
    }
//获取所有网络视频路径，根据播放顺序的排序网址
    public String[] getAllNetvideoPath() {
        String[] netPathStringArray = new String[getCount()];
        for (int i = 0; i < getCount() - 1; i++) {
            netPathStringArray[i] = getNetVideoPath(i);
        }
        return netPathStringArray;
    }
  //所有本地那视频的具体路径
    public String[] getAllLocalVideoPath() {
        String[] localPathStringArray = new String[getCount()];
        for (int i = 0; i < getCount() - 1; i++) {
            localPathStringArray[i] = getNetVideoPath(i);
        }
        return localPathStringArray;
    }

}
