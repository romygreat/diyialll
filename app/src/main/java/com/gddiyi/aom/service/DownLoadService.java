package com.gddiyi.aom.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;


import com.gddiyi.aom.R;
import com.gddiyi.aom.constant.VSConstances;
import com.gddiyi.aom.model.MyThreadPool;
import com.gddiyi.aom.model.PlayData;
import com.gddiyi.aom.model.VideoPlayAll;
import com.gddiyi.aom.model.dto.RequestJsonSn;
import com.gddiyi.aom.model.dto.RequestJsonVideo;
import com.gddiyi.aom.model.dto.ResponseJsonSn;
import com.gddiyi.aom.model.dto.ResponseJsonVideo;
import com.gddiyi.aom.netutils.CallBackUtil;
import com.gddiyi.aom.netutils.DownloadUtil;
import com.gddiyi.aom.netutils.OkhttpUtil;
import com.gddiyi.aom.presenter.RetrofitPresenter;
import com.gddiyi.aom.presenter.VideoPresenter;


import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DownLoadService extends IntentService implements Callback<ResponseJsonVideo>, VideoPresenter.DownloadVideoReady {
    String TAG = "MyTest";
    RetrofitPresenter mPrensenter;
    VideoPresenter mVideoPrensenter;
    String token;
    String doMainName;
    String realVideoPath;
    ExecutorService executorService;
    int downloadSuccess;
    SharedPreferences sharedPreferences;
    int updateSuccess;

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
        mVideoPrensenter = new VideoPresenter();
        mVideoPrensenter.setDownloadVideReady(this);
        sharedPreferences = getSharedPreferences(getString(R.string.diyi), Context.MODE_PRIVATE);
        try {
            final String json;
           // String url = VSConstances.MAIN_URL;
            Log.i(TAG, "onHandleIntent: ");
            mPrensenter = new RetrofitPresenter();
            //response sn
            mPrensenter.setCallback(new Callback<ResponseJsonSn>() {
                @Override
                public void onResponse(Call<ResponseJsonSn> call, final retrofit2.Response<ResponseJsonSn> response) {
                    Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(final Subscriber<? super String> subscriber) {
                            Log.i(TAG, "call: response sn");
                            ResponseJsonSn r = response.body();
                            Log.i(TAG, "call:response "+r.toString());
                            int chargeTime=Integer.parseInt(r.getData().getShop_set().getCharge_time());
                            Log.i(TAG, "call:chargeTime "+r.getData().getShop_set().getCharge_time());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt(getString(R.string.chargeTime),chargeTime);
                            editor.commit();
                            token = r.getData().getToken();
                            subscriber.onNext(token);
                            String url = VSConstances.REQUEST_DOMAINURL;
                            String jsontoken = "{\"token\":" + "\"" + token + "\"}";
                            Log.i(TAG, "call:token "+token);
                            OkhttpUtil.okHttpPostJson(url, jsontoken, new CallBackUtil() {
                                @Override
                                public Object onParseResponse(okhttp3.Call call, okhttp3.Response response) {
                                    try {
                                        final JSONObject tokenJsonObject;
                                        tokenJsonObject = new JSONObject(response.body().string());
                                        doMainName = tokenJsonObject.getString("data");
                                        Log.i(TAG, "onParseResponse: " + doMainName);
                                        mVideoPrensenter.setDoMain(doMainName);
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
                                    e.printStackTrace();

                                }

                                @Override
                                public void onNext(String s) {
                                    Log.i(TAG, "onNext:onResponse: " + s);
                                }
                            });
                }

                @Override
                public void onFailure(Call<ResponseJsonSn> call, Throwable t) {
                    Log.i(TAG, "onFailure:");
                }
            });
            //fist step,get the sn from server,in order to get the unique token
            RequestJsonSn requestJsonSn = new RequestJsonSn();
            requestJsonSn.setSn(VSConstances.TEST_SN);
            String urlSN=VSConstances.URL_SN;
            mPrensenter.retrofitPost(urlSN, mPrensenter.postJsonString(requestJsonSn));
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
            mPrensenter.setCallbackVideo(this);
            String url = VSConstances.MAIN_URL;
            url=VSConstances.POST_VIDEO_PATH;
            mPrensenter.retrofitPostVideo(url, mPrensenter.postJsonString(requestJsonVideo));
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "getVideo: response Ex==" + e.toString());
        }

    }

    //获取所有的视频信息,真实路径
    @Override
    public void onResponse(Call<ResponseJsonVideo> call, Response<ResponseJsonVideo> response) {
        Log.i(TAG, "onResponse: test1234");
        mVideoPrensenter.saveVideoPrsenter(response.body().getData());
    }

    @Override
    public void onFailure(Call<ResponseJsonVideo> call, Throwable t) {
        Log.i(TAG, "onFailure:getVideo " + t.toString());
    }
     //第一次安装使用下载视频使用时走的方法
    @Override
    public void noticefyDownLoadReady(final VideoPlayAll<PlayData> sparseArray) {
        Log.i(TAG, "noticefyDownLoadReady: " + sparseArray.getAllNetvideoPath().length);
        Log.i(TAG, "noticefyDownLoadReady: " + sparseArray);
        boolean isFirstBoot = sharedPreferences.getBoolean("firstBoot", true);
        setSharePreference(sparseArray.getCount());
        if (isFirstBoot) {
            setSharePreference(VSConstances.NA);
            MyThreadPool.getExeCutor().execute(new Runnable() {
                @Override
                public void run() {
                    mVideoPrensenter.save2LocalFile(sparseArray);
                }
            });
            File file = mVideoPrensenter.createFile(VSConstances.JSONFILEPATH);

                        Log.i(TAG, "noticefyDownLoadReady:for " + sparseArray.get(17).getVideoName());
            for (int i = 0; i < sparseArray.getCount(); i++) {
                DownloadUtil.get().download(sparseArray.get(i).getNetVideoPath(), VSConstances.AD,
                        new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess() {
                        Log.i(TAG, "onDownloadSuccess: " + downloadSuccess++);
                        if (sparseArray.getCount()==downloadSuccess){
                            //服务停止代码
                            downloadSuccess=0;

                        }
                    }

                    private void stopMyservice(int count) {

                    }

                    @Override
                    public void onDownloading(int progress) {

                    }

                    @Override
                    public void onDownloadFailed() {

                    }
                });
            }
        } else {
            //判断与网络的获取结果是否一致，不一致时并在此方法中更新
            if (mVideoPrensenter.checkUpdate()) {
                Log.i(TAG, "noticefyDownLoadReady:no update file ");
            }
        }
        Log.i(TAG, "noticefyDownLoadReady:isFirstBoot " + isFirstBoot);
    }
    //非首次安装使用下载视频使用时走的方法
    //查询更新的方式，节省流量
    @Override
    public void noticefyUpdate(final VideoPlayAll<PlayData> sparseArray, final ArrayList list) {
        Log.i(TAG, "noticefyDownLoadReady:getCount "+sparseArray.getCount());
        MyThreadPool.getExeCutor().execute(new Runnable() {
            @Override
            public void run() {
                mVideoPrensenter.save2LocalFile(sparseArray);
            }
        });
        setSharePreference(sparseArray.getCount());
        mVideoPrensenter.deleteSDadVideo();
        for (int  i = 0; i < list.size(); i++) {
            final String name= sparseArray.get((int)list.get(i)).getVideoName();
            final int downLoadNum=list.size();
            DownloadUtil.get().download( sparseArray.get((int)list.get(i)).getNetVideoPath(), "ad", new DownloadUtil.OnDownloadListener() {
                @Override
                public void onDownloadSuccess() {
                    Log.i("noticefyUpdate:", "onDownloadSuccess: "+name);
                   updateSuccess++;
                   if (updateSuccess==downLoadNum){
                       //这里停止写掉服务代码
                       updateSuccess=0;
                   }
                }
                @Override
                public void onDownloading(int progress) {

                }

                @Override
                public void onDownloadFailed() {

                }
            });
        }
    }
   //设置更改为否为首次启动
    public void setSharePreference(int a) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //测试时候修改该参数为true
        editor.putBoolean("firstBoot", false);
        if (a!=VSConstances.NA){
        editor.putInt(VSConstances.VIDEOCOUNT,a);}
        editor.commit();//提交修改
    }
}
