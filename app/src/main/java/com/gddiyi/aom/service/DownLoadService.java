package com.gddiyi.aom.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


import com.gddiyi.aom.dto.RequestJsonSn;
import com.gddiyi.aom.dto.RequestJsonVideo;
import com.gddiyi.aom.dto.ResponseJsonSn;
import com.gddiyi.aom.dto.ResponseJsonVideo;
import com.gddiyi.aom.netutils.CallBackUtil;
import com.gddiyi.aom.netutils.DownLoadVideoUtils;
import com.gddiyi.aom.netutils.OkhttpUtil;
import com.gddiyi.aom.presenter.RetrofitPresenter;


import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DownLoadService extends IntentService {
    String TAG = "MyTest";
    RetrofitPresenter mPrensenter;
    String token;
    String doMainName;
    String realVideoPath;
    ExecutorService executorService;


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
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
        try {
            final String json;
            //原因是缺少{}导致的问题,注意这个问题，这个就报错"sn": "sn88888888"
            json = " {\"sn\": \"sn88888888\"}";

            String url = "http://service.gddiyi.com/";

            mPrensenter = new RetrofitPresenter();

            //使用Rxjava，对于下载的迪溢视频的线程切换到IO线程

            mPrensenter.setCallback(new Callback<ResponseJsonSn>() {
                @Override
                public void onResponse(Call<ResponseJsonSn> call, final retrofit2.Response<ResponseJsonSn> response) {
                    Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(final Subscriber<? super String> subscriber) {
                            ResponseJsonSn r = response.body();
                            String sn = r.getData().getSn();
                            token = r.getData().getToken();
//                            subscriber.onNext(sn);
                            subscriber.onNext(token);
                            String url = "http://service.gddiyi.com/device/Verify/checkDevice";
                            url = "http://service.gddiyi.com/qiniu/Upload/getDomain";
//                            json=" {\"sn\": \"sn88888888\"}";

                            String jsontoken = "{\"token\":" + "\"" + token + "\"}";
                            Log.i(TAG, "call: " + jsontoken);
                            OkhttpUtil.okHttpPostJson(url, jsontoken, new CallBackUtil() {
                                @Override
                                public Object onParseResponse(okhttp3.Call call, okhttp3.Response response) {
                                    try {
                                        final JSONObject tokenJsonObject;
//                                        tokenJsonObject=  RetrofitPresenter.getInstanceGson().toJson(response.body().string());
                                        tokenJsonObject = new JSONObject(response.body().string());
                                        doMainName = tokenJsonObject.getString("data");
                                        Log.i(TAG, "onParseResponse: " + doMainName);
                                        realVideoPath = doMainName;
                                        subscriber.onCompleted();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    return null;
                                }

                                @Override
                                public void onFailure(okhttp3.Call call, Exception e) {

                                }

                                @Override
                                public void onResponse(Object response) {
                                    Log.i(TAG, "onResponse: 123");

                                }
                            });
//                            subscriber.onCompleted();
                        }
                    }).observeOn(Schedulers.io())
                            .subscribe(new Subscriber<String>() {
                                @Override
                                public void onCompleted() {
                                    Log.i(TAG, "onCompleted:begin " + token);
                                    if (token != null) {
                                        getVideo();

                                    }
                                    Log.i(TAG, "onCompleted: response");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.i(TAG, "onError: response");

                                }

                                @Override
                                public void onNext(String s) {
                                    Log.i(TAG, "onNext:onResponse: " + s);
                                }
                            });
                }

                @Override
                public void onFailure(Call<ResponseJsonSn> call, Throwable t) {
                    Log.i(TAG, "onFailure: test");
                }
            });
            RequestJsonSn requestJsonSn = new RequestJsonSn();
            requestJsonSn.setSn("sn88888888");
            mPrensenter.retrofitPost(url, mPrensenter.postJsonString(requestJsonSn));


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public void getVideo() {
        Log.i(TAG, "getVideo: ");
        try {
            RequestJsonVideo requestJsonVideo = new RequestJsonVideo();
            requestJsonVideo.setToken(token);
            requestJsonVideo.setMachine("machine");
            RequestJsonVideo.SortBean sortBean = new RequestJsonVideo.SortBean();
            sortBean.setId("asc");
            sortBean.setShop_id("desc");
            sortBean.setSort("desc");
            requestJsonVideo.setSort(sortBean);
            mPrensenter.setCallbackVideo(new Callback<ResponseJsonVideo>() {
                @Override
                public void onResponse(Call<ResponseJsonVideo> call, Response<ResponseJsonVideo> response) {
                    String path = response.body().getData().getList().get(2).getPath();
                    Log.i(TAG, "onResponse:path= " + response.body().getData().getList().get(2).getPath());
                    realVideoPath = "http://" + doMainName + "/" + path;
                    Log.i(TAG, "onResponse: downLoadVedio" + realVideoPath);
                    executorService = Executors.newFixedThreadPool(20);
                    executorService.execute(new DownLoadVideoUtils(realVideoPath, null));
                }

                @Override
                public void onFailure(Call<ResponseJsonVideo> call, Throwable t) {
                    Log.i(TAG, "onFailure:getVideo " + t.toString());
                }
            });
            String url = "http://service.gddiyi.com/";
            mPrensenter.retrofitPostVideo(url, mPrensenter.postJsonString(requestJsonVideo));

        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "getVideo: response Ex==" + e.toString());
        }

    }


}
