package com.gddiyi.aom.view;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gddiyi.aom.R;
import com.gddiyi.aom.constant.VSConstances;
import com.gddiyi.aom.jsinterface.JavaScriptinterface;
import com.gddiyi.aom.netutils.ADFilterUtil;
import com.gddiyi.aom.service.DownLoadService;
import com.hdy.hdylights.LedAndChargeManager;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.core.Main;


/**
 * 程序入口启动类进入onreate()
 */
public class MainActivity extends BaseActivity implements View.OnTouchListener, JavaScriptinterface.NoticefyPay {
    private WebView mWebview;
    String TAG = "MYTest";
    private int mTime;
    private JavaScriptinterface javaScriptinterface;
    private Button mButton;
    Handler mHandler;
    int mCount = 0;
    String vedioURL;
    private Timer timer;
    private TimerTask task;
    private int currentTime = 0;
    ScheduledExecutorService executorService;


    //优化代码
    LinearLayout linearLayout;
    ImageView imageView;
    LinearLayout webviewError;
    View main;

    //this project is X5 frameWork is OK
    //正常加载x5内核
    //使用fragment不能全屏问题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebview = findViewById(R.id.webview);
        initView();
        mWebview.setOnTouchListener(this);
        Diyi_setWebSettings();
//        initTimer();
        mHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
//                        File downloadFile = new File(Environment.getExternalStorageDirectory(), VSConstances.AD);
//                        if (downloadFile.exists()) {
//                            Intent intent = new Intent(MainActivity.this, VideoActivity.class);
//                            startActivity(intent);
//                        }
//                        Log.i(TAG, "handleMessage: dwonloadfile not exit");
                        break;
                    case 2:
                        startDownloadService();

                    default:
                        break;
                }
            }
        };
        requestPermission();

    }


    private void Diyi_setWebSettings() {
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest, Bundle bundle) {
                Log.i(TAG, "shouldInterceptRequest: " + webResourceRequest.getUrl());
                if (webResourceRequest.getUrl().toString().contains("om.gddiyi.com"))

                {
                    if (ADFilterUtil.booleanhasAd(MainActivity.this, webResourceRequest.getUrl().toString()))
                        return super.shouldInterceptRequest(webView, webResourceRequest, bundle);
                    else return super.shouldInterceptRequest(null, null, null);
                } else {
                    return super.shouldInterceptRequest(null, null, null);
                }
            }

            //当开始载入页面的时候调用

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                loadReady();
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
                webviewError.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                Log.i(TAG, "onPageFinished: okhttp url==" + s);
                linearLayout.setVisibility(View.GONE);

            }
        });


        WebSettings settings = mWebview.getSettings();
        settings.setLoadWithOverviewMode(true);

        //注册javascript接口
        javaScriptinterface = new JavaScriptinterface(this);
        settings.setJavaScriptEnabled(true);
        mWebview.addJavascriptInterface(javaScriptinterface,
                "android");

        settings.setUseWideViewPort(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setGeolocationEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);

        //白屏调优代码
        mWebview.requestFocus();
        settings.setAppCacheEnabled(true);

        String appCachePath = getApplicationContext().getCacheDir().getPath() + "/webcache";
        settings.setAppCachePath(appCachePath);
        settings.setDatabasePath(appCachePath);

        mWebview.clearCache(true);
        mWebview.clearHistory();
        vedioURL = "file:///sdcard/ad/5cb2264ae3dd42a17248c22dbe942e26.mp4";
        mWebview.loadUrl(VSConstances.MAIN_URL);

    }



    @Override
    public boolean readyPay() {
        //一、准备支付阶段，取消定时功能，使用stoptimer
        //二、同时使用一个boolean,变量为touch=false,触摸屏幕失效
        Log.i(TAG, "readyPay: ");
        touch=false;
        stopTimer();
        Log.i(TAG, "readyPay: stopTimer");
        //继续监听页面支付情况
        return true;
    }

    @Override
    public boolean finishPay(int time) {
        Log.i(TAG, "finishPay:传进来time "+time);
        boolean b=  super.finishPay(time);
        //一、完成支付，无论是否为0，此时应该重新打开计时功能
        //二、同时将touch变量设置为一个boolean=true
        //三、将时间time保存，开启充电功能后，开启线程定时，定时取消充电功能
        Log.i(TAG, "finishPay: time"+time);
        if (time>0){
            LedAndChargeManager.switchCharge(LedAndChargeManager.SWITCH_ON);
            Log.i(TAG, "finishPay: red "+LedAndChargeManager.setLedColor(LedAndChargeManager.RED_OPEN));
        }
        touch=true;
        startTimer();
        return b;
    }

    class MyTask extends TimerTask {
        @Override
        public void run() {
            // 初始化计时器
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    {
                        mHandler.sendEmptyMessage(1);
                        Log.i(TAG, "onTouch: mHandler");
                        stopTimer();
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "onTouch: ");
        mCount++;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //有按下动作时取消定时
                stopTimer();
                break;
            case MotionEvent.ACTION_UP:
                //抬起时启动定时
                startTimer();

                break;
            default:
                break;
        }

        return false;
    }

    private void startTimer() {
        //启动计时器
        /**
         * java.util.Timer.schedule(TimerTask task, long delay, long period)：
         * 这个方法是说，delay/1000秒后执行task,然后进过period/1000秒再次执行task，
         * 这个用于循环任务，执行无数次，当然，你可以用timer.cancel();取消计时器的执行。
         */
        if (touch){
        initTimer();
        try {
            timer.schedule(task, 1000 * 20, 60*1000);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            initTimer();
            timer.schedule(task, 1000 * 50, Long.MAX_VALUE);
        }
        Log.i(TAG, "startTimer: onTouch");

        }

    }

    private void stopTimer() {
        if (touch) {
            if (timer != null) {

                timer.cancel();
            }

            currentTime = 0;
        }
    }

    private void initTimer() {
        // 初始化计时器
        task = new MyTask();
        timer = new Timer();

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
//        ActivityStack.getInstance().push(this);
    }


    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            mWebview.setVisibility(View.INVISIBLE);
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS_STORAGE, 10);
            mHandler.sendEmptyMessageDelayed(2,0);
        } else {
            mHandler.sendEmptyMessageDelayed(2, 1000 * 20);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mWebview.setVisibility(View.VISIBLE);
        launcherMainActvity();
        //延迟60秒启动DownLoadService
        mHandler.sendEmptyMessageDelayed(2, 1000 * 60);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initView() {
        linearLayout = (LinearLayout) findViewById(R.id.web_started);
        imageView = (ImageView) findViewById(R.id.started_gif);
        webviewError = (LinearLayout) findViewById(R.id.webview_error);
        loadReady();
    }

    public void loadReady() {
        Glide.with(MainActivity.this).load(R.mipmap.loading_circle).asGif().into(imageView);
    }


}


