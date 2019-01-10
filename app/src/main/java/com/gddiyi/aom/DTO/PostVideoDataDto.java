package com.gddiyi.aom.DTO;

public class PostVideoDataDto {

    String token;
    String machine;
    VideoSort sort;

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getMachine() {

        return machine;
    }

    public VideoSort getSort() {

        return sort;
    }

    public void setSort(VideoSort sort) {

        this.sort = sort;
    }

    String sn;


    public String getToken() {
        return token;
    }




    public String getSn() {
        return sn;
    }



    public void setToken(String token) {
        this.token = token;
    }





    public void setSn(String sn) {
        this.sn = sn;
    }

}
