package com.gddiyi.aom;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.hdy.hdylights.LedAndChargeManager;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

/**
 *
 * Application
 * @author romygreat
 * @date 20190103
 * QQ:907264506
 *
 * this class is running before MainActivity,with the aim to init
 * LED and X5 core,accelerate webView
 *
 */
public class YidiApplication extends Application {

    String TAG = "DIYIAPPCATION";
    private int mTime = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: X5 Core not init");
        Log.i(TAG, "onCreate:CPU_ABI=="+Build.CPU_ABI);
        Log.i(TAG, "onCreate: CPU_ABI2=="+Build.CPU_ABI2);
//
//        HashMap map = new HashMap();
//        map.put(TbsCoreSettings. TBS_SETTINGS_USE_PRIVATE_CLASSLOADER,true);
//        QbSdk.initTbsSettings(map);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d(TAG, " x5 core already init  == " + arg0);
            }
            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
        LedAndChargeManager.init();
    }


}