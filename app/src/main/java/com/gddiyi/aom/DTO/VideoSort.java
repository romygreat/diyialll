package com.gddiyi.aom.DTO;

public class VideoSort {

    String id;
    String shop_id;
    String sort;



    public VideoSort(String id, String shop_id, String sort) {
        this.id = id;
        this.shop_id = shop_id;

        this.sort = sort;
    }

    public void setSort(String sort) {

        this.sort = sort;
    }

    public String getSort() {

        return sort;
    }

    public VideoSort() {

    }



    public String getId() {
        return id;
    }

    public String getShop_id() {
        return shop_id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

}
