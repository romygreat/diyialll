package com.gddiyi.aom.DTO;

public class PostVideoDataDto {

    String token;
    String machmine;
    VideoSort videoSort;
    String sn;


    public String getToken() {
        return token;
    }

    public String getMachmine() {
        return machmine;
    }

    public VideoSort getVideoSort() {
        return videoSort;
    }

    public String getSn() {
        return sn;
    }



    public void setToken(String token) {
        this.token = token;
    }

    public void setMachmine(String machmine) {
        this.machmine = machmine;
    }

    public void setVideoSort(VideoSort videoSort) {
        this.videoSort = videoSort;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

}
