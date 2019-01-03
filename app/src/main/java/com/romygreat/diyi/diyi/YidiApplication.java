package com.romygreat.diyi.diyi;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

public class YidiApplication extends Application {
    private Application mInstance;
    String TAG="MYTest";
    private int  mTime=0;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
       mTime=(int) System.currentTimeMillis();

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d(TAG, " onViewInitFinished is " + arg0);
                mTime=(int)System.currentTimeMillis()-mTime;

                Log.d(TAG, " mTIME is " + mTime);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
}
