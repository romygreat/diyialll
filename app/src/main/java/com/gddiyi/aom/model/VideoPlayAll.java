package com.gddiyi.aom.model;

import android.util.SparseArray;

public class VideoPlayAll<E> {
    int count;
    SparseArray<PlayData> saveData;

    public VideoPlayAll(int count) {
        this.count = count;
        saveData=new SparseArray<>(count);
    }

    public void setSaveData(SparseArray<PlayData> saveData) {
        this.saveData = saveData;
    }

    public SparseArray<PlayData> getSaveData() {

        return saveData;
    }



    public void setCount(int count) {

        this.count = count;
    }

    public int getCount() {

        return count;
    }
    public PlayData get(int index) {
      PlayData playData=  this.getSaveData().get(index);
        return playData;
    }
    public PlayData put(int index,PlayData playData) {
         this.getSaveData().put(index,playData);
        return playData;
    }
    public String getNetVideoPath(int sortIndex){
        String videoPath=null;
        if (getSaveData().get(sortIndex)!=null)
        { videoPath=  this.getSaveData().get(sortIndex).getNetVideoPath();}
        return videoPath;
    }
    public String getLocalVideoPath(int sortIndex){
        String videoPath=null;
        if (getSaveData().get(sortIndex)!=null)
        { videoPath=  this.getSaveData().get(sortIndex).getLocalPath();}
        return videoPath;
    }
    public String getVideoName(int sortIndex){
        String videoPath=null;
        if (getSaveData().get(sortIndex)!=null)
        { videoPath=  this.getSaveData().get(sortIndex).getVideoName();}
        return videoPath;
    }
}
