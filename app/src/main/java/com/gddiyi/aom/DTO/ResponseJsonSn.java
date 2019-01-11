package com.gddiyi.aom.DTO;

public class ResponseJsonSn {
    /**
     * code : 1
     * message : 操作成功
     * data : {"id":"93","sn":"sn88888888","type":"1","shop_id":"62","agent_id":"0","status":"1","remark":"请不要修改这个SN号","update_time":"2018-12-20 20:44:29","delete_time":"0","shop_name":"迪溢科技","shop_prov":"440000","shop_city":"441300","shop_zone":"441302","table_id":"140","table_title":"A038","table_status":"0","subarea_id":"17","subarea_title":"大厅","token":"9858a4e31b4b27048babea93992061b4"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 93
         * sn : sn88888888
         * type : 1
         * shop_id : 62
         * agent_id : 0
         * status : 1
         * remark : 请不要修改这个SN号
         * update_time : 2018-12-20 20:44:29
         * delete_time : 0
         * shop_name : 迪溢科技
         * shop_prov : 440000
         * shop_city : 441300
         * shop_zone : 441302
         * table_id : 140
         * table_title : A038
         * table_status : 0
         * subarea_id : 17
         * subarea_title : 大厅
         * token : 9858a4e31b4b27048babea93992061b4
         */

        private String id;
        private String sn;
        private String type;
        private String shop_id;
        private String agent_id;
        private String status;
        private String remark;
        private String update_time;
        private String delete_time;
        private String shop_name;
        private String shop_prov;
        private String shop_city;
        private String shop_zone;
        private String table_id;
        private String table_title;
        private String table_status;
        private String subarea_id;
        private String subarea_title;
        private String token;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(String agent_id) {
            this.agent_id = agent_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(String delete_time) {
            this.delete_time = delete_time;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_prov() {
            return shop_prov;
        }

        public void setShop_prov(String shop_prov) {
            this.shop_prov = shop_prov;
        }

        public String getShop_city() {
            return shop_city;
        }

        public void setShop_city(String shop_city) {
            this.shop_city = shop_city;
        }

        public String getShop_zone() {
            return shop_zone;
        }

        public void setShop_zone(String shop_zone) {
            this.shop_zone = shop_zone;
        }

        public String getTable_id() {
            return table_id;
        }

        public void setTable_id(String table_id) {
            this.table_id = table_id;
        }

        public String getTable_title() {
            return table_title;
        }

        public void setTable_title(String table_title) {
            this.table_title = table_title;
        }

        public String getTable_status() {
            return table_status;
        }

        public void setTable_status(String table_status) {
            this.table_status = table_status;
        }

        public String getSubarea_id() {
            return subarea_id;
        }

        public void setSubarea_id(String subarea_id) {
            this.subarea_id = subarea_id;
        }

        public String getSubarea_title() {
            return subarea_title;
        }

        public void setSubarea_title(String subarea_title) {
            this.subarea_title = subarea_title;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
//    int code;
//    String  message;
//    VideoData data;
//
//    public int getCode() {
//        return code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public VideoData getData() {
//        return data;
//    }
//
//
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public void setData(VideoData data) {
//        this.data = data;
//    }
//}
// class VideoSort {
//
//    String id;
//    String shop_id;
//    String sort;
//
//
//
//    public VideoSort(String id, String shop_id, String sort) {
//        this.id = id;
//        this.shop_id = shop_id;
//
//        this.sort = sort;
//    }
//
//    public void setSort(String sort) {
//
//        this.sort = sort;
//    }
//
//    public String getSort() {
//
//        return sort;
//    }
//
//    public VideoSort() {
//
//    }
//
//
//
//    public String getId() {
//        return id;
//    }
//
//    public String getShop_id() {
//        return shop_id;
//    }
//
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public void setShop_id(String shop_id) {
//        this.shop_id = shop_id;
//    }

}
