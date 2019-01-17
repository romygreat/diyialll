package com.gddiyi.aomwify.wify;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.gddiyi.aomwify.functionPrenster.WifyFunctionPresenter;

public class WifyPresenterImpl implements WifyFunctionPresenter {
    Context mContext;
   static WifiManager mWifiManager;

    public WifyPresenterImpl(Context mContext) {
        this.mContext = mContext;
        mWifiManager=(WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    }

    public static WifiManager getmWifiManager() {
        return mWifiManager;
    }
    public WifyPresenterImpl() {

    }
    @Override
    public void openWify() {
        if (!getWifyManager().isWifiEnabled()) {
            //开启wifi
            getWifyManager().setWifiEnabled(true);
        }
    }

    @Override
    public void closeWify() {
        if (!getWifyManager().isWifiEnabled()) {
            //开启wifi
            getWifyManager().setWifiEnabled(false);
        }
    }

    @Override
    public void connectWify() {

    }

    @Override
    public boolean checkIsConnected() {
        return false;
    }

    @Override
    public void setContext(Context context) {

    }

    @Override
    public void setWifyPassWord() {

    }
    WifiManager getWifyManager() {
        if (mWifiManager != null) {
            return mWifiManager;
        }
        else{
     return (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);}
    }


}
