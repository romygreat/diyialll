package com.gddiyi.aom.dto;

public class RequestJsonVideo {

    /**
     * token : edab7728496448e32a6e3a20a26a1c6c
     * machine : machine
     * sort : {"sort":"desc","id":"asc","shop_id":"desc"}
     */

    private String token;
    private String machine;
    private SortBean sort;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public SortBean getSort() {
        return sort;
    }

    public void setSort(SortBean sort) {
        this.sort = sort;
    }

    public static class SortBean {
        /**
         * sort : desc
         * id : asc
         * shop_id : desc
         */

        private String sort;
        private String id;
        private String shop_id;

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }
    }
}
