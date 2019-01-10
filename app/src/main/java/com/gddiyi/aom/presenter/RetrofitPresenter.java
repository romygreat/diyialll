package com.gddiyi.aom.presenter;


import com.gddiyi.aom.NetUtils.PostService;
import com.gddiyi.aom.com.javaBean.PostResultJavaBean;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitPresenter {
    String TAG="RetrofitPresenter";
    Callback<PostResultJavaBean> callback;
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
        retrofit2.Call<PostResultJavaBean>  snResultJavaBean= postService.getSnResult(requestBody);
        snResultJavaBean.enqueue(getCallback());

    }


    public void setCallback(Callback<PostResultJavaBean> callback) {
        this.callback = callback;
    }

    public Callback<PostResultJavaBean> getCallback() {

        return callback;
    }
}
