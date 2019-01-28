package com.gddiyi.aom;

import android.app.Application;
import android.util.Log;

import com.hdy.hdylights.LedAndChargeManager;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

public class YidiApplication extends Application {

    String TAG = "MYTest";
    private int mTime = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        LedAndChargeManager.init();
//        HashMap map = new HashMap();
//        map.put(TbsCoreSettings. TBS_SETTINGS_USE_PRIVATE_CLASSLOADER,true);
//        QbSdk.initTbsSettings(map);
//        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//            @Override
//            public void onViewInitFinished(boolean arg0) {
//                // TODO Auto-generated method stub
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Log.d(TAG, " onViewInitFinished is " + arg0);
//                mTime=(int)System.currentTimeMillis()-mTime;
//                Log.d(TAG, " mTIME is " + mTime);
//            }
//            @Override
//            public void onCoreInitFinished() {
//                // TODO Auto-generated method stub
//
//
//            }
//        };
//        //x5内核初始化接口
//        QbSdk.initX5Environment(getApplicationContext(), cb);
//
//    }
    }
}