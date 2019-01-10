package com.gddiyi.aom.DTO;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {
    String id;
    String sn;
    String type;
    String shop_id;
    String agent_id;
    String status;
    String remark;
    String update_time;
    String delete_time;
    String hop_name;
    String shop_prov;
    String hop_city;
    String shop_zone;
    String able_id;
    String table_title;
    String table_status;
    String subarea_id;
    String subarea_title;
    String token;

    protected Data(Parcel in) {
        id = in.readString();
        sn = in.readString();
        type = in.readString();
        shop_id = in.readString();
        agent_id = in.readString();
        status = in.readString();
        remark = in.readString();
        update_time = in.readString();
        delete_time = in.readString();
        hop_name = in.readString();
        shop_prov = in.readString();
        hop_city = in.readString();
        shop_zone = in.readString();
        able_id = in.readString();
        table_title = in.readString();
        table_status = in.readString();
        subarea_id = in.readString();
        subarea_title = in.readString();
        token = in.readString();
    }

    public Data() {

    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getSn() {
        return sn;
    }

    public String getType() {
        return type;
    }

    public String getShop_id() {
        return shop_id;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public String getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public String getDelete_time() {
        return delete_time;
    }

    public String getHop_name() {
        return hop_name;
    }

    public String getShop_prov() {
        return shop_prov;
    }

    public String getHop_city() {
        return hop_city;
    }

    public String getShop_zone() {
        return shop_zone;
    }

    public String getAble_id() {
        return able_id;
    }

    public String getTable_title() {
        return table_title;
    }

    public String getTable_status() {
        return table_status;
    }

    public String getSubarea_id() {
        return subarea_id;
    }

    public String getSubarea_title() {
        return subarea_title;
    }

    public String getToken() {
        return token;
    }



    public void setId(String id) {
        this.id = id;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    public void setHop_name(String hop_name) {
        this.hop_name = hop_name;
    }

    public void setShop_prov(String shop_prov) {
        this.shop_prov = shop_prov;
    }

    public void setHop_city(String hop_city) {
        this.hop_city = hop_city;
    }

    public void setShop_zone(String shop_zone) {
        this.shop_zone = shop_zone;
    }

    public void setAble_id(String able_id) {
        this.able_id = able_id;
    }

    public void setTable_title(String table_title) {
        this.table_title = table_title;
    }

    public void setTable_status(String table_status) {
        this.table_status = table_status;
    }

    public void setSubarea_id(String subarea_id) {
        this.subarea_id = subarea_id;
    }

    public void setSubarea_title(String subarea_title) {
        this.subarea_title = subarea_title;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(sn);
        dest.writeString(type);
        dest.writeString(shop_id);
        dest.writeString(agent_id);
        dest.writeString(status);
        dest.writeString(remark);
        dest.writeString(update_time);
        dest.writeString(delete_time);
        dest.writeString(hop_name);
        dest.writeString(shop_prov);
        dest.writeString(hop_city);
        dest.writeString(shop_zone);
        dest.writeString(able_id);
        dest.writeString(table_title);
        dest.writeString(table_status);
        dest.writeString(subarea_id);
        dest.writeString(subarea_title);
        dest.writeString(token);
    }


    @Override
    public String toString() {
        String dataMessa=null;
        dataMessa="this"+getClass().getSimpleName();
        return dataMessa;
    }
}
