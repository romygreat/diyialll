package com.gddiyi.aom.jsinterface;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;


import com.gddiyi.aom.view.CrossWalkActivity;
import com.gddiyi.aom.view.MainActivity;
import com.hdy.hdylights.LedAndChargeManager;

import java.lang.reflect.Method;


public class JavaScriptinterface {

    Context mContext;
    String TAG = getClass().getSimpleName();
    NoticefyPay mNoticefyPay;

    public JavaScriptinterface(Context c) {
        mContext = c;
        mNoticefyPay = (NoticefyPay) c;
    }

    /**
     * 与js交互时用到的方法，在js里直接调用的
     */
    @org.xwalk.core.JavascriptInterface
    @JavascriptInterface
    public void showToast(String s) {

    }

    @org.xwalk.core.JavascriptInterface
    @JavascriptInterface
    public String JScall1(String string) {
        Log.i(TAG, "JScall1: " + string);
        return "java JSCALL";
    }

    @org.xwalk.core.JavascriptInterface
    public void intentMain() {
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
        Log.i(TAG, "showToast: jscall");
    }

    @org.xwalk.core.JavascriptInterface
    public String getSn() {
        Log.i(TAG, "getSn: Yes");
        return getSerialNumber();
//        return "sn88888888";
    }

    @org.xwalk.core.JavascriptInterface
    public boolean readyPay() {
        Log.i(TAG, "readyPay: ");
        if (mNoticefyPay != null) {
            Toast.makeText(mContext,"readyPay",Toast.LENGTH_LONG);
            mNoticefyPay.readyPay();
            return true;
        } else {
            return true;
        }
    }

    @org.xwalk.core.JavascriptInterface
    public boolean finishPay(int time) {
        Log.i(TAG, "finishPay: time"+time);
        // time=0,支付不成功,time=1支付成功
        if (mNoticefyPay != null)
        {
          boolean returnResult=  mNoticefyPay.finishPay(time);
          return returnResult;
        }
        return false;
    }
    @org.xwalk.core.JavascriptInterface
    void openWifySetting(){
        ((CrossWalkActivity) mContext).setWify();
    }

    public interface NoticefyPay {
        boolean readyPay();

        boolean finishPay(int time);

    }
    public  String getSerialNumber(){
      return Build.SERIAL;}
}