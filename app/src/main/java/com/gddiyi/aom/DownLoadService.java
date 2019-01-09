package com.gddiyi.aom;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.gddiyi.aom.NetUtils.TokenService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class DownLoadService extends IntentService {
    String TAG="MyTest";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *
     */
    public DownLoadService() {
        super("DownLoadservice");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent: ");
       // postDataWithParame();
        try {

            String json="{menu:鱼香肉丝,key:77643fdff291bce1a3db9b6a74817743}";
            json=" {\"sn\": \"sn88888888\"}";//原因是缺少{}导致的问题,注意这个问题，这个就报错"sn": "sn88888888"

//          String  body=  okhttpPost("http://apis.juhe.cn/cook/query.php", json);
//            String  body=  okhttpPost("http://service.gddiyi.com/device/Verify/checkDevice", json);
            String url="http://service.gddiyi.com/";
               retrofitPost(url,json);
//

        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "onHandleIntent: IOException");
        }
        Log.i(TAG, "onHandleIntent: end ");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
     synchronized  String   okhttpPost(String url, String json) throws IOException {
        Log.i(TAG, "okhttpPost: ");
         MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        MediaType JSON = MediaType.parse("video/mp4; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);

        OkHttpClient client = new OkHttpClient();
         FormBody formBody = new FormBody.Builder()
                 .add("sn","sn88888888")
                 .build();
         RequestBody requestBody=(RequestBody)formBody;
         Log.i(TAG, "okhttpPost: "+requestBody.contentType().toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
//                .post(requestBody)

                .build();
        Response response = client.newCall(request).execute();
//        response.close();

        Log.i(TAG, "okhttpPost: end");
        return response.body().string();
    }

    public synchronized void retrofitPost(String url,String content ){
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
                .client(new OkHttpClient())
                .build();
      TokenService tokenService = retrofit2.create(TokenService.class);
      MediaType JSON = MediaType.parse("application/json; charset=utf-8");
      RequestBody requestBody=RequestBody.create(JSON, content);
        retrofit2.Call<ResponseBody> call= tokenService.getSn(requestBody);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    Log.i(TAG, "onResponse: "+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onFailure: onResponse:"+t);
            }
        });





    }

}
