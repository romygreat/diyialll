package com.gddiyi.aom.DTO;

public class PostSnResultDto {
    int code;
    String message;
    Data data;


    public PostSnResultDto(Data data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Data data) {
        this.data = data;
    }
    static PostSnResultDto getInstance(){
         PostSnResultDto sn=new PostSnResultDto(new Data());
        return sn ;

    }


    @Override
    public String toString() {
        String  snresult=null;
        snresult="from diyi=="+"code:"+code+"message:"+message+"data:"+this.getData().toString();

        return snresult;
    }


}




