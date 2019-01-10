package com.gddiyi.aom.com.javaBean;

import com.google.gson.Gson;

public class DownLoadVideoBean {
//    	"token":"7dc8e1cd6557cd92ee5d4797b047e2bc",
//                "machine":"machine",
//                "sort":{
//        "sort":"desc",
//                "id":"asc",
//                "shop_id":"desc"
    String token;
    String machmine;
    Sort sort;
    String sn;
    static Gson instanceGson;

    public String getToken() {
        return token;
    }

    public String getMachmine() {
        return machmine;
    }

    public Sort getSort() {
        return sort;
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

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
    public static Gson getInstanceGson() {
        if (instanceGson==null){
            instanceGson=new Gson();
        }
        return instanceGson;
    }
    public  String  postJsonString(String src){
      String jsonString=  getInstanceGson().toJson(src);
      return jsonString;

    }
}
class Sort{
    String desc;
    String id;
    String shop_id;

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

}
