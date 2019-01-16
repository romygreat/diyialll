package com.gddiyi.aomwify.presenter;


import com.gddiyi.aomwify.model.dto.ResponseJsonSn;
import com.gddiyi.aomwify.model.dto.RequestJsonVideo;
import com.gddiyi.aomwify.model.dto.ResponseJsonVideo;
import com.gddiyi.aomwify.netutils.PostService;
import com.gddiyi.aomwify.model.dto.RequestJsonSn;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitPresenter {
    String TAG="RetrofitPresenter";
    Callback<ResponseJsonSn> callback;
    Callback<ResponseJsonVideo> callbackVideo;
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

    public void setCallbackVideo(Callback<ResponseJsonVideo> callbackVideo) {
        this.callbackVideo = callbackVideo;
    }

    public Callback<ResponseJsonVideo> getCallbackVideo() {

        return callbackVideo;
    }

    public synchronized void retrofitPostVideo(String url,String content ){

        PostService postService = createRetrofit(url).create(PostService.class);
        RequestBody requestBody=createRequestBody(content);
        retrofit2.Call<ResponseJsonVideo>  snResultJavaBean= postService.getVideoResult(requestBody);
        snResultJavaBean.enqueue(getCallbackVideo());

    }

    static Gson instanceGson;

    public Retrofit createRetrofit(String url){
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
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
        retrofit2.Call<ResponseJsonSn>  snResultJavaBean= postService.getSnResult(requestBody);
        snResultJavaBean.enqueue(getCallback());

    }

    public void setCallback(Callback<ResponseJsonSn> callback) {
        this.callback = callback;
    }

    public Callback<ResponseJsonSn> getCallback() {

        return callback;
    }
    public static Gson getInstanceGson() {
        if (instanceGson==null){
            instanceGson=new Gson();
        }
        return instanceGson;
    }
    public  String  postJsonString(RequestJsonSn requestJsonSn){
        String jsonString=  getInstanceGson().toJson(requestJsonSn);
        return jsonString;
    }
    public synchronized void retrofitPostResponsebody(String url,String content ){
        PostService postService = createRetrofit(url).create(PostService.class);
        RequestBody requestBody=createRequestBody(content);
        retrofit2.Call<ResponseBody>  snResultJavaBean= postService.getVideoTest(requestBody);
        snResultJavaBean.enqueue(getCallBackResponsebody());
    }
    public  String  postJsonString(RequestJsonVideo postVideoDataDto){
        String jsonString=  getInstanceGson().toJson(postVideoDataDto);
        return jsonString;
    }
    public String postJsonString(String postVideoDataDto){
        String jsonString=  getInstanceGson().toJson(postVideoDataDto);
        return jsonString;
    }
}
