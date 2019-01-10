package com.gddiyi.aom;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import jsinterface.JavaScriptinterface;

public class TestActivity extends Activity {
    private WebView mWebview;
    String TAG="MYTest";
    private int mTime;
    private JavaScriptinterface javaScriptinterface;
    private Button mButton;

    //this project is X5 frameWork is OK
    //正常加载x5内核
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题
        // 设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mWebview = findViewById(R.id.webview);

        javaScriptinterface=new JavaScriptinterface(this);
        mButton=findViewById(R.id.calljs);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: test()");
                mWebview.loadUrl("javascript:test1('\" + msg + \"')");

            }
        });
        mWebview.evaluateJavascript("javascript:test()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
//                Log.i(TAG, "onReceiveValue: test()"+s);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
//        mWebview.loadUrl("http://om.gddiyi.com/playad/");
//        mWebview.loadUrl("http://om.gddiyi.com/");
        mWebview.addJavascriptInterface(javaScriptinterface,
                "android");
        mWebview.loadUrl("file:///android_asset/test.html");

//        mWebview.loadUrl("https://www.baidu.com/");
        WebSettings settings = mWebview.getSettings();
        settings.setLoadWithOverviewMode(true);
//        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
//        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setGeolocationEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);



    }



}
