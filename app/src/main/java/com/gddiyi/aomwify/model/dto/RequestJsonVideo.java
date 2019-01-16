package com.gddiyi.aomwify.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestJsonVideo {

    /**
     * token : edab7728496448e32a6e3a20a26a1c6c
     * machine : machine
     * sort : {"sort":"desc","id":"asc","shop_id":"desc"}
     */

    private String token;
    private String machine;
    private SortBean sort;


    @Setter
    @Getter
    public static class SortBean {
        /**
         * sort : desc
         * id : asc
         * shop_id : desc
         */
        private String sort;
        private String id;
        private String shop_id;


    }
}
