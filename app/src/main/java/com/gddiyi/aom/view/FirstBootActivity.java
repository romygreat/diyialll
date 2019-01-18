package com.gddiyi.aom.view;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gddiyi.aom.R;

import com.gddiyi.aom.wifypresenter.WifyPresenterImpl;

import java.util.ArrayList;
import java.util.List;

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
String TAG="wify";
String[] wifyList;
ListView listView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bootlayout);
        if (Build.VERSION.SDK_INT >23){
            requestPermission(); }
            getWifyFragment();

        listView=findViewById(R.id.wifyList);
    }

    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS_STORAGE,10);}
    }
    private void getWifyFragment() { WifyFragment fragment = new WifyFragment();
        fragmentTransaction=  getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainActivityId , fragment);
        fragmentTransaction.commit();

    }


}

