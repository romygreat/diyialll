package com.gddiyi.aom.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseJsonSn {
    /**
     * code : 1
     * message : 操作成功
     * data : {"id":"93","sn":"sn88888888","type":"1","shop_id":"62","agent_id":"0","status":"1","remark":"请不要修改这个SN号","update_time":"2018-12-20 20:44:29","delete_time":"0","shop_name":"迪溢科技","shop_prov":"440000","shop_city":"441300","shop_zone":"441302","table_id":"140","table_title":"A038","table_status":"0","subarea_id":"17","subarea_title":"大厅","token":"9858a4e31b4b27048babea93992061b4"}
     */

    private int code;
    private String message;
    private DataBean data;


    @Setter
    @Getter
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


    }

}
