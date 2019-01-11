package com.gddiyi.aom.DTO;

import java.util.List;

public class ResponseJsonVideo {

    /**
     * code : 1
     * message : 操作成功
     * data : {"count":18,"list":[{"id":31,"title":"手动阀手动阀2","path":"ad/9e810023b8e689be648c657cf50d5c43.mp4","type":"video/mp4","prov":440000,"city":441300,"zone":441303,"shop_id":62,"terminal_type":1,"sort":222,"update_time":"1970-01-01 08:32:50","delete_time":0,"create_time":"1970-01-01 08:32:50","start_time":1546843624,"end_time":1546930209,"status":1}]}
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
         * count : 18
         * list : [{"id":31,"title":"手动阀手动阀2","path":"ad/9e810023b8e689be648c657cf50d5c43.mp4","type":"video/mp4","prov":440000,"city":441300,"zone":441303,"shop_id":62,"terminal_type":1,"sort":222,"update_time":"1970-01-01 08:32:50","delete_time":0,"create_time":"1970-01-01 08:32:50","start_time":1546843624,"end_time":1546930209,"status":1}]
         */

        private int count;
        private List<ListBean> list;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getProv() {
                return prov;
            }

            public void setProv(int prov) {
                this.prov = prov;
            }

            public int getCity() {
                return city;
            }

            public void setCity(int city) {
                this.city = city;
            }

            public int getZone() {
                return zone;
            }

            public void setZone(int zone) {
                this.zone = zone;
            }

            public int getShop_id() {
                return shop_id;
            }

            public void setShop_id(int shop_id) {
                this.shop_id = shop_id;
            }

            public int getTerminal_type() {
                return terminal_type;
            }

            public void setTerminal_type(int terminal_type) {
                this.terminal_type = terminal_type;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public int getDelete_time() {
                return delete_time;
            }

            public void setDelete_time(int delete_time) {
                this.delete_time = delete_time;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getStart_time() {
                return start_time;
            }

            public void setStart_time(int start_time) {
                this.start_time = start_time;
            }

            public int getEnd_time() {
                return end_time;
            }

            public void setEnd_time(int end_time) {
                this.end_time = end_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
