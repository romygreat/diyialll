package com.gddiyi.aomwify.wify;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.gddiyi.aomwify.broadcast.WifyBrocastReceiver;
import com.gddiyi.aomwify.functionPrenster.WifiAutoConnectManager;

import java.util.ArrayList;

public class WifyActivity extends AppCompatActivity {
    String TAG = "wify";
    WifiManager mWifiManager;
    WifiAutoConnectManager wac;
    WifyBrocastReceiver wifyBrocastReceiver = new WifyBrocastReceiver();
    ArrayList<ScanResult> wifiListAdapter = new ArrayList<>();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifyl);
        requestPermission();
        WifyPresenterImpl wifyPresenter = new WifyPresenterImpl(getApplicationContext());

        mWifiManager = WifyPresenterImpl.getmWifiManager();
        mWifiManager.startScan();
        if (mWifiManager.isWifiEnabled()) {
            Log.i(TAG, "onCreate: enable");
            wifiListAdapter = (ArrayList<ScanResult>) mWifiManager.getScanResults();
            for (ScanResult scanResult : wifiListAdapter) {
                Log.i(TAG, "onReceive: " + scanResult.SSID);
            }
        }
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        Log.i(TAG, "onCreate:SSID =" + wifiInfo.getSSID());
        Log.i(TAG, "onCreate: IP=" + wifiInfo.getIpAddress());
        WifiConfiguration wifiConfiguration=new WifiConfiguration();
        wac = new WifiAutoConnectManager(mWifiManager);
        wac.mHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.i(TAG, "handleMessage: "+msg.obj);
                super.handleMessage(msg);
            }
        };

    }


    public void requestPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
        { ActivityCompat.requestPermissions(this,
                    PERMISSIONS_STORAGE,10);}
                    
    }
    public void clickbutton(View view){
        Log.i(TAG, "clickbutton: ");
        wac = new WifiAutoConnectManager(mWifiManager);
        try {
            wac.connect("gddiyi", "gddiyi2018",
                    "myAdmin".equals("")? WifiAutoConnectManager.WifiCipherType.WIFICIPHER_NOPASS: WifiAutoConnectManager.WifiCipherType.WIFICIPHER_WPA);
        } catch (Exception e) {
            Log.i(TAG, "clickbutton: "+e.toString());
        }
    }
}