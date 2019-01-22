package com.gddiyi.aom.model.dto;


import lombok.ToString;

@ToString
public class ResponseJsonSn {


    /**
     * code : 1
     * message : 操作成功
     * data : {"id":"93","sn":"sn88888888","type":"1","shop_id":"62","agent_id":"0","status":"1","remark":"请不要修改这个SN号","update_time":"2018-12-20 20:44:29","delete_time":"0","shop_name":"迪溢科技","shop_prov":"440000","shop_city":"441300","shop_zone":"441302","table_id":"140","table_title":"A038","table_status":"0","subarea_id":"17","subarea_title":"大厅","shop_set":{"shop_id":"62","is_voice":"0","is_invoice":"0","charge_time":"1","charge_price":0.01,"ticket_head":"0","ticket_tail":"0"},"token":"b28aea6a042b5249bb9db12068ca78d4"}
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
         * shop_set : {"shop_id":"62","is_voice":"0","is_invoice":"0","charge_time":"1","charge_price":0.01,"ticket_head":"0","ticket_tail":"0"}
         * token : b28aea6a042b5249bb9db12068ca78d4
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
        private ShopSetBean shop_set;
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

        public ShopSetBean getShop_set() {
            return shop_set;
        }

        public void setShop_set(ShopSetBean shop_set) {
            this.shop_set = shop_set;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class ShopSetBean {
            /**
             * shop_id : 62
             * is_voice : 0
             * is_invoice : 0
             * charge_time : 1
             * charge_price : 0.01
             * ticket_head : 0
             * ticket_tail : 0
             */

            private String shop_id;
            private String is_voice;
            private String is_invoice;
            private String charge_time;
            private double charge_price;
            private String ticket_head;
            private String ticket_tail;

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public String getIs_voice() {
                return is_voice;
            }

            public void setIs_voice(String is_voice) {
                this.is_voice = is_voice;
            }

            public String getIs_invoice() {
                return is_invoice;
            }

            public void setIs_invoice(String is_invoice) {
                this.is_invoice = is_invoice;
            }

            public String getCharge_time() {
                return charge_time;
            }

            public void setCharge_time(String charge_time) {
                this.charge_time = charge_time;
            }

            public double getCharge_price() {
                return charge_price;
            }

            public void setCharge_price(double charge_price) {
                this.charge_price = charge_price;
            }

            public String getTicket_head() {
                return ticket_head;
            }

            public void setTicket_head(String ticket_head) {
                this.ticket_head = ticket_head;
            }

            public String getTicket_tail() {
                return ticket_tail;
            }

            public void setTicket_tail(String ticket_tail) {
                this.ticket_tail = ticket_tail;
            }
        }
    }
}
