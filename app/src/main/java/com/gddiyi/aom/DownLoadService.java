package com.gddiyi.aom;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.gddiyi.aom.DTO.PostVideoDataDto;
import com.gddiyi.aom.DTO.PostSnResultDto;

import com.gddiyi.aom.DTO.PostVideoResult;
import com.gddiyi.aom.DTO.VideoSort;
import com.gddiyi.aom.presenter.RetrofitPresenter;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DownLoadService extends IntentService {
    String TAG="MyTest";
    RetrofitPresenter mPrensenter;
    String token;


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
                             token=r.getData().getToken();
                            subscriber.onNext(sn);
                            subscriber.onNext(token);
                            subscriber.onCompleted();
                        }
                    }).observeOn(Schedulers.io())
                            .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            Log.i(TAG, "onCompleted:begin "+token);
                            if (token!=null){
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
                            Log.i(TAG, "onNext:onResponse: "+s);
                        }
                    });
                }
                @Override
                public void onFailure(Call<PostSnResultDto> call, Throwable t) {
                    Log.i(TAG, "onFailure: test");
                }
            });
            PostVideoDataDto postDataDto =new PostVideoDataDto();
            postDataDto.setSn("sn88888888");
            mPrensenter.retrofitPost(url,mPrensenter.postJsonString(postDataDto));
//            mPrensenter.retrofitPost(url,json);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
public void getVideo(){
    Log.i(TAG, "getVideo: ");
    try {
        PostVideoDataDto postVideoDataDto=new PostVideoDataDto();
        VideoSort videoSort=new VideoSort();
        videoSort.setId("asc");
        videoSort.setSort("desc");
        videoSort.setShop_id("desc");
        postVideoDataDto.setSn("sn88888888");
        postVideoDataDto.setToken(token);
//        postVideoDataDto.setSort(videoSort);
//        mPrensenter.setCallbackVideo(new Callback<PostVideoResult>() {
//            @Override
//            public void onResponse(Call<PostVideoResult> call, Response<PostVideoResult> response) {
//                Log.i(TAG, "onResponse:getVideo: "+response.code());
//                Log.i(TAG, "onResponse: getVideo"+response.message());
//                Log.i(TAG, "onResponse:getVideo "+response.body().getMessage());
//            }
//            @Override
//            public void onFailure(Call<PostVideoResult> call, Throwable t) {
//                Log.i(TAG, "onFailure:getVideo "+t.toString());
//            }
//        });


        mPrensenter.setCallBackResponsebody(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//               PostVideoResult t=(PostVideoResult) response;
                Log.i(TAG, "onResponse: "+response.code());
                Log.i(TAG, "onResponse: "+response.message());
                try {
                    Log.i(TAG, "onResponse: "+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onFailure: t"+t.toString());
            }
        });

        String url="http://service.gddiyi.com/";
//        mPrensenter.retrofitPostVideo(url,mPrensenter.postJsonString(postVideoDataDto));
        mPrensenter.retrofitPostResponsebody(url,mPrensenter.postJsonString(postVideoDataDto));
    } catch (Exception e) {
        e.printStackTrace();
        Log.i(TAG, "getVideo: response Ex=="+e.toString());
    }

}


}
