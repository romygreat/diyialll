package com.gddiyi.aom.jsinterface;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;


import com.gddiyi.aom.view.MainActivity;
import com.hdy.hdylights.LedAndChargeManager;


public class JavaScriptinterface {

    Context mContext;
    String TAG = getClass().getSimpleName();
    noticefyCharge mNoticefyCharge;

    public JavaScriptinterface(Context c) {
        mContext = c;
        mNoticefyCharge = (noticefyCharge)c;
    }

    /**
     * 与js交互时用到的方法，在js里直接调用的
     */
    @JavascriptInterface
    public void showToast(String s) {

    }

    @JavascriptInterface
    public String JScall1(String string) {
        Log.i(TAG, "JScall1: " + string);
        return "java JSCALL";
    }

    @JavascriptInterface
    public void intentMain() {
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
        Log.i(TAG, "showToast: jscall");
    }

    @JavascriptInterface
    public String getSn() {
        Log.i(TAG, "getSn: Yes");
        return "sn99999999";
    }

    @JavascriptInterface
    public boolean switchCharger(int charge) {
        Log.i(TAG, "switchCharge: true"+charge);
        Toast.makeText(mContext,"ischarge"+charge,Toast.LENGTH_LONG).show();
        boolean isCharge = false;
        isCharge = LedAndChargeManager.switchCharge(charge);
        if (mNoticefyCharge==null){
            Log.i(TAG, "switchCharger: mNOticefyChargeis null");
        }
        if (isCharge) {
            if (mNoticefyCharge != null) {
                mNoticefyCharge.noticefyCharge();
            }
        } else {
            if (mNoticefyCharge != null) {
                mNoticefyCharge.noticefyUnCharge();

            }
        }
        return isCharge;
    }

    public interface noticefyCharge {
        void noticefyCharge();
        void noticefyUnCharge();
    }
}