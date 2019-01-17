package com.gddiyi.aom.model;

public class PlayData  {
    String videoName;
    int sort;
    String netVideoPath;
    String localPath;

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getLocalPath() {

        return localPath;
    }

    public PlayData() {

    }

    public PlayData(String videoName,  String netVideoPath) {
        this.videoName = videoName;
        this.netVideoPath = netVideoPath;
    }


    public void setDoMainName(String doMainName) {
        this.doMainName = doMainName;
    }

    public String getDoMainName() {

        return doMainName;
    }

    String doMainName;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {

        return count;
    }

    int count;



    public String getVideoName() {
        return videoName;
    }

    public int getSort() {
        return sort;
    }



    public String getNetVideoPath() {
        return netVideoPath;
    }



    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }



    public void setNetVideoPath(String netVideoPath) {
        this.netVideoPath = netVideoPath;
    }
}
