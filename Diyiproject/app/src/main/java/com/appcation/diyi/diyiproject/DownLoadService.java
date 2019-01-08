package com.appcation.diyi.diyiproject;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

//          String  body=  okhttpPost("http://apis.juhe.cn/cook/query.php", json);
          String  body=  okhttpPost("http://service.gddiyi.com/device/Verify/checkDevice", json);
            Log.i(TAG, "onHandleIntent: okhttp=="+body);
        } catch (IOException e) {
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
//                 .add("menu", "鱼香肉丝")
//                 .add("key", "77643fdff291bce1a3db9b6a74817743")
                 .add("sn","sn88888888")
//
//                   .add("","")
//                   .add()
                 .build();
//         Request request = new Request.Builder()


        Request request = new Request.Builder()
                .url(url)
//                .post(body)
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();

        Log.i(TAG, "okhttpPost: end");
        return response.body().string();
    }

}
