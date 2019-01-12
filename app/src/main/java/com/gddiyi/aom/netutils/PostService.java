package com.gddiyi.aom.netutils;

import com.gddiyi.aom.model.dto.ResponseJsonSn;
import com.gddiyi.aom.model.dto.ResponseJsonVideo;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostService {

    @POST("device/Verify/checkDevice")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<ResponseJsonSn>getSnResult(@Body RequestBody rb);

    @POST("advert/Ad/playAd")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<ResponseJsonVideo>getVideoResult(@Body RequestBody rb);

    @POST("advert/Ad/playAd")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<ResponseBody>getVideoTest(@Body RequestBody rb);

}
