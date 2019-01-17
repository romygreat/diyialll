package com.gddiyi.aomwify.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;


import com.gddiyi.aomwify.wify.WifyPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author romygreat
 * @time 20180116
 */

public class WifyBrocastReceiver extends BroadcastReceiver {
WifiManager wifiManager;
 ArrayList<ScanResult> wifiListAdapter=new ArrayList<>();
 WifyReiceverInterface wifyReiceverInterface;
String TAG="MyBbroadCast";
    public void setWifyReiceverInterface(WifyReiceverInterface wifyReiceverInterface) {
        this.wifyReiceverInterface = wifyReiceverInterface;
    }

    public WifyReiceverInterface getWifyReiceverInterface() {

        return wifyReiceverInterface;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        wifiManager=WifyPresenterImpl.getmWifiManager();
        Log.i(TAG, "onReceive: 123");
        if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
//             wifi已成功扫描到可用wifi。
            List<ScanResult> scanResults = wifiManager.getScanResults();
            wifiListAdapter.addAll(scanResults);
            for (ScanResult scanResult:scanResults){
                Log.i(TAG, "onReceive: "+scanResult.SSID);
            }
            if (getWifyReiceverInterface()!=null){
                wifyReiceverInterface.noticefyScanResult(wifiListAdapter);
            }
        }

    }
    interface WifyReiceverInterface{
        List<ScanResult> noticefyScanResult(List<ScanResult> scanResults);
    }
}
