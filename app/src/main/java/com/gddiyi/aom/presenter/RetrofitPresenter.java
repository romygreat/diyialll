package com.gddiyi.aom.presenter;


import com.gddiyi.aom.DTO.PostVideoResult;
import com.gddiyi.aom.NetUtils.CallBackUtil;
import com.gddiyi.aom.NetUtils.PostService;
import com.gddiyi.aom.DTO.PostVideoDataDto;
import com.gddiyi.aom.DTO.PostSnResultDto;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitPresenter {
    String TAG="RetrofitPresenter";
    Callback<PostSnResultDto> callback;
    Callback<PostVideoResult> callbackVideo;
    Callback<ResponseBody> callBackResponsebody;

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public void setCallBackResponsebody(Callback<ResponseBody> callBackResponsebody) {
        this.callBackResponsebody = callBackResponsebody;
    }

    public String getTAG() {

        return TAG;
    }

    public Callback<ResponseBody> getCallBackResponsebody() {
        return callBackResponsebody;
    }

    public void setCallbackVideo(Callback<PostVideoResult> callbackVideo) {
        this.callbackVideo = callbackVideo;
    }

    public Callback<PostVideoResult> getCallbackVideo() {

        return callbackVideo;
    }

    public synchronized void retrofitPostVideo(String url,String content ){

        PostService postService = createRetrofit(url).create(PostService.class);
        RequestBody requestBody=createRequestBody(content);
        retrofit2.Call<PostVideoResult>  snResultJavaBean= postService.getVideoResult(requestBody);
        snResultJavaBean.enqueue(getCallbackVideo());

    }

    static Gson instanceGson;

    public Retrofit createRetrofit(String url){
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        return retrofit2;
    }
    public RequestBody createRequestBody(String content){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody=RequestBody.create(JSON, content);
        return  requestBody;
    }
    public synchronized void retrofitPost(String url,String content ){

        PostService postService = createRetrofit(url).create(PostService.class);
        RequestBody requestBody=createRequestBody(content);
        retrofit2.Call<PostSnResultDto>  snResultJavaBean= postService.getSnResult(requestBody);
        snResultJavaBean.enqueue(getCallback());

    }


    public void setCallback(Callback<PostSnResultDto> callback) {
        this.callback = callback;
    }

    public Callback<PostSnResultDto> getCallback() {

        return callback;
    }
    public static Gson getInstanceGson() {
        if (instanceGson==null){
            instanceGson=new Gson();
        }
        return instanceGson;
    }
    public  String  postJsonString(PostVideoDataDto postVideoDataDto){
        String jsonString=  getInstanceGson().toJson(postVideoDataDto);
        return jsonString;
    }
    public synchronized void retrofitPostResponsebody(String url,String content ){
        PostService postService = createRetrofit(url).create(PostService.class);
        RequestBody requestBody=createRequestBody(content);
        retrofit2.Call<ResponseBody>  snResultJavaBean= postService.getVideoTest(requestBody);
        snResultJavaBean.enqueue(getCallBackResponsebody());
    }
}
