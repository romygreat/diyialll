package com.gddiyi.aom.jsinterface;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;


import com.gddiyi.aom.view.MainActivity;


public class JavaScriptinterface {

    Context mContext;
    String TAG=getClass().getSimpleName();

    public JavaScriptinterface(Context c) {
        mContext = c;
    }
    /**
     * 与js交互时用到的方法，在js里直接调用的
     */
    @JavascriptInterface
    public void showToast(String s) {

    }
    @JavascriptInterface
    public String  JScall1(String string){
        Log.i(TAG, "JScall1: "+string);
    return  "java JSCALL";
    }
    @JavascriptInterface
    public void intentMain(){
        Intent intent=new Intent(mContext,MainActivity.class);
        mContext.startActivity(intent);
        Log.i(TAG, "showToast: jscall");
    }
    @JavascriptInterface
    public String getSN(){

        return "12345678";
    }
}