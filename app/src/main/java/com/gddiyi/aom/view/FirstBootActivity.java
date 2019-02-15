package com.gddiyi.aom.view;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.gddiyi.aom.R;
import com.gddiyi.aom.constant.VSConstances;

/**
 * @author romygreat
 * @date 20180119
 */
public class FirstBootActivity extends FragmentActivity {
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.Manifest.permission.READ_PHONE_STATE",
            "android.permission.CHANGE_WIFI_STATE",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_WIFI_STATE",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.CHANGE_WIFI_MULTICAST_STATE"
    };
    FragmentTransaction fragmentTransaction;
    String TAG = "wify";
    SharedPreferences mSharedPreferences;
    WifyFragment fragment;
    WifiManager mWifiManager;
    String  removewify;
    MyBroadCastReciver myWifyBrocastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bootlayout);
        requestPermission();
        VSConstances.SET_FROM_WIFY=true;
//        Intent intent=getIntent();
//        if (Boolean.parseBoolean(intent.getStringExtra(getString(R.string.falsePassword)))){
//            Log.i(TAG, "onCreate: intent"+intent.getStringExtra(getString(R.string.falsePassword)));
//            Toast.makeText(this,"请选择wify,并输入相应的密码",Toast.LENGTH_LONG).show();
//        }

        getWifyFragment();
        registerMyBroadCast();
    }

    private void registerMyBroadCast() {
//        mSharedPreferences = getSharedPreferences(getString(R.string.diyi), MODE_PRIVATE);
//         myWifyBrocastReceiver = new MyBroadCastReciver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(myWifyBrocastReceiver, filter);
//        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS_STORAGE, 10);
        }
    }

    private void getWifyFragment() {
        fragment = new WifyFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainActivityId, fragment);
        fragmentTransaction.commit();
    }

    public class MyBroadCastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            //如果是在开启wifi连接和有网络状态下
            Log.i(TAG, "onReceive: mywify");
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (NetworkInfo.State.CONNECTED == info.getState()) {
                    String mySsid = mSharedPreferences.getString(getString(R.string.SSID), "..");
                    Log.i(TAG, "onReceive: myssid==" + mySsid);
                    String SSID = mWifiManager.getConnectionInfo().getSSID();
                    Log.i(TAG, "onReceive: SSID==" + SSID);
                    if (mySsid.equals(SSID)) {
                        startMyMainActivity();
                    } else {
                        SSID = SSID.substring(1, SSID.length() - 1);
                        if (mySsid.equals(SSID)) {
                            startMyMainActivity();
                        }
                    }

                } else {
                    Log.e("diyi", "无网络连接");
                }
            }
        }

    }



    private void startMyMainActivity() {
        Intent intentMain = new Intent(FirstBootActivity.this, MainActivity.class);
        startActivity(intentMain);
    }

    @Override
    public void onBackPressed() {

         super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(myWifyBrocastReceiver);
    }
}

