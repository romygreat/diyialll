package com.gddiyi.aom.DTO;

public class PostVideoResult {
    int code;
    String  message;
    VideoData data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public VideoData getData() {
        return data;
    }



    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(VideoData data) {
        this.data = data;
    }
}
