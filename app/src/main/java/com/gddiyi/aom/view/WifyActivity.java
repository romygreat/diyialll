package com.gddiyi.aom.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.gddiyi.aom.R;
import com.gddiyi.aom.wifypresenter.WifyPresenterImpl;


import java.lang.reflect.Method;

public class WifyActivity extends AppCompatActivity {
    String TAG = "wify";
    WifiManager mWifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wify);
        WifyPresenterImpl wifyPresenter=new WifyPresenterImpl(this);
        mWifiManager=WifyPresenterImpl.getmWifiManager();
        mWifiManager.startScan();
        WifiInfo wifiInfo= mWifiManager.getConnectionInfo();
        Log.i(TAG, "onCreate:SSID ="+wifiInfo.getSSID());
        Log.i(TAG, "onCreate: IP="+wifiInfo.getIpAddress());




    }

}
