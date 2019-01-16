package com.gddiyi.aomwify.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseJsonVideo {

    /**
     * code : 1
     * message : 操作成功
     * data : {"count":18,"list":[{"id":31,"title":"手动阀手动阀2","path":"ad/9e810023b8e689be648c657cf50d5c43.mp4","type":"video/mp4","prov":440000,"city":441300,"zone":441303,"shop_id":62,"terminal_type":1,"sort":222,"update_time":"1970-01-01 08:32:50","delete_time":0,"create_time":"1970-01-01 08:32:50","start_time":1546843624,"end_time":1546930209,"status":1}]}
     */

    private int code;
    private String message;
    private DataBean data;


    @Setter
    @Getter
    public static class DataBean {
        /**
         * count : 18
         */

        private int count;
        private List<ListBean> list;
        @Setter
        @Getter
        public static class ListBean {
            /**
             * id : 31
             * title : 手动阀手动阀2
             * path : ad/9e810023b8e689be648c657cf50d5c43.mp4
             * type : video/mp4
             * prov : 440000
             * city : 441300
             * zone : 441303
             * shop_id : 62
             * terminal_type : 1
             * sort : 222
             * update_time : 1970-01-01 08:32:50
             * delete_time : 0
             * create_time : 1970-01-01 08:32:50
             * start_time : 1546843624
             * end_time : 1546930209
             * status : 1
             */

            private int id;
            private String title;
            private String path;
            private String type;
            private int prov;
            private int city;
            private int zone;
            private int shop_id;
            private int terminal_type;
            private int sort;
            private String update_time;
            private int delete_time;
            private String create_time;
            private int start_time;
            private int end_time;
            private int status;

        }
    }
}
