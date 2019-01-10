package com.gddiyi.aom.NetUtils;

import com.gddiyi.aom.com.javaBean.PostResultJavaBean;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostService {
    @POST("device/Verify/checkDevice")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<ResponseBody> getSn(@Body RequestBody info);

    @POST("device/Verify/checkDevice")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<PostResultJavaBean>getSnResult(@Body RequestBody rb);


}
