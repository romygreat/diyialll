package com.gddiyi.aom.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.gddiyi.aom.R;
import com.gddiyi.aom.constant.VSConstances;
import com.gddiyi.aom.jsinterface.JavaScriptinterface;
import com.gddiyi.aom.presenter.WifiAutoConnectManager;
import com.gddiyi.aom.service.DownLoadService;
import com.hdy.hdylights.LedAndChargeManager;

public class BaseActivity extends Activity implements JavaScriptinterface.NoticefyPay {
    public static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_PHONE_STATE"
    };
    Handler mBaseHandler;

   public boolean touch=true;
    public  int  BACKPRESS_TIME=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fullScreen();
        hideBottomUIMenu();
        mBaseHandler=new Handler(getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                touch=true;
               LedAndChargeManager.switchCharge(LedAndChargeManager.SWITCH_OFF);
                Log.i(TAG, "handleMessage: "+LedAndChargeManager.switchCharge(LedAndChargeManager.SWITCH_OFF));
                Log.i(TAG, "finishPay: red== "+LedAndChargeManager.setLedColor(LedAndChargeManager.RED_CLOSE));
            }
        };
    }

    String TAG = "BaseActivity";

    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    BroadcastReceiver chargeBroadCast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String acttion = intent.getAction();
            if (acttion.equals(Intent.ACTION_POWER_CONNECTED)) {
//                printMytips("手机已连接，正在充电");
            } else if (acttion.equals(Intent.ACTION_POWER_DISCONNECTED)) {
//                printMytips("手机拔出充电开关，请注意");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(chargeBroadCast);
    }


    private void fullScreen() {
        // 隐藏标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        hideBottomUIMenu();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        registerReceiver(chargeBroadCast, intentFilter);
    }

    public void launcherMainActvity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Log.i(TAG, "onRequestPermissionsResult: ");

    }

    public boolean isNetworkAvailable() {
        //得到应用上下文
        Context context = getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）  notificationManager /alarmManager
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //启动服务
    public void startDownloadService() {
        Log.i(TAG, "startDownloadService: ");
        Intent downLoadIntent = new Intent(this, DownLoadService.class);
        startService(downLoadIntent);
    }

    @Override
    public boolean readyPay() {
        return true;
    }
    @Override
    public boolean finishPay(int time) {
//        boolean b=LedAndChargeManager.switchCharge(LedAndChargeManager.SWITCH_ON);
//        Log.i(TAG, "finishPay:bb "+b);
        //一、同时将touch变量设置为一个boolean=true
        touch=true;
        //二、完成支付，无论是否为0，此时应该重新打开计时功能
        //三、将时间time保存，开启充电功能后，开启线程定时，定时取消充电功能
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.diyi), Context.MODE_PRIVATE);
        int chageTime=sharedPreferences.getInt(getString(R.string.chargeTime),1);
        Log.i(TAG, "finishPay: "+chageTime);
        mBaseHandler.sendEmptyMessageDelayed(VSConstances.PAY2CHARGE,chageTime*VSConstances.TIME_UNIT_MIN);
        return true;
    }

    @Override
    public void onBackPressed() {
        BACKPRESS_TIME++;
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.diyi), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String SSID=getString(R.string.SSID);
        editor.putString(SSID,"..");
        editor.commit();//提交修改
        super.onBackPressed();
    }
}
