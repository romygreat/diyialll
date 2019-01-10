package com.gddiyi.aom;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.gddiyi.aom.DTO.PostVideoDataDto;
import com.gddiyi.aom.DTO.PostSnResultDto;

import com.gddiyi.aom.presenter.RetrofitPresenter;



import retrofit2.Call;
import retrofit2.Callback;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DownLoadService extends IntentService {
    String TAG="MyTest";
    RetrofitPresenter mPrensenter;


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
        try {
            String json;
            //原因是缺少{}导致的问题,注意这个问题，这个就报错"sn": "sn88888888"
            json=" {\"sn\": \"sn88888888\"}";

            String url="http://service.gddiyi.com/";

            mPrensenter=new RetrofitPresenter();

            //使用Rxjava，对于下载的迪溢视频的线程切换到IO线程

            mPrensenter.setCallback(new Callback<PostSnResultDto>() {
                @Override
                public void onResponse(Call<PostSnResultDto> call, final retrofit2.Response<PostSnResultDto> response) {
                    Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            PostSnResultDto r=response.body();
                             String sn=r.getData().getSn();
                             String token=r.getData().getToken();
                            subscriber.onNext(sn);
                            subscriber.onNext(token);
                        }
                    }).observeOn(Schedulers.io())
                            .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                        @Override
                        public void onNext(String s) {
                            Log.i(TAG, "onNext:onResponse: "+s);
                        }
                    });
                }
                @Override
                public void onFailure(Call<PostSnResultDto> call, Throwable t) {
                    Log.i(TAG, "onFailure: test");
                }
            });
            PostVideoDataDto postVideoDataDto =new PostVideoDataDto();
            postVideoDataDto.setSn("sn88888888");
            mPrensenter.retrofitPost(url,mPrensenter.postJsonString(postVideoDataDto));
//            mPrensenter.retrofitPost(url,json);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public void onCreate() {
        super.onCreate();

    }



}
