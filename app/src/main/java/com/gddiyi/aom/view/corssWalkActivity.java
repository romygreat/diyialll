package com.gddiyi.aom.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gddiyi.aom.R;
import com.gddiyi.aom.constant.VSConstances;
import com.gddiyi.aom.jsinterface.JavaScriptinterface;

import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

//这是crosswalk版本
/**
 *
 *
 */
public class corssWalkActivity extends BaseActivity {
    private XWalkView xWalkWebView;
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
    int reload=1;


    //优化代码
    LinearLayout linearLayout;
    ImageView imageView;
    LinearLayout webviewError;
    View main;

    @Override
    protected void onXWalkReady() {
        xWalkWebView.load("http://om.gddiyi.com/", null);
//        http://om.gddiyi.com/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crosswalk);
        xWalkWebView=(XWalkView)findViewById(R.id.xwalkWebView);
        // turn on debugging
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
        if (!isNetworkAvailable()){
            Intent intent=new Intent(this,FirstBootActivity.class);
            startActivity(intent);
            intent.putExtra("noNetWork","noNetWork");
            this.finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: ");
        mHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        File downloadFile = new File(Environment.getExternalStorageDirectory(), VSConstances.AD);
                        if (downloadFile.exists()) {
//                            Intent intent = new Intent(MainActivity.this, VideoActivity.class);
//                            startActivity(intent);
                        }
                        Log.i(TAG, "handleMessage: dwonloadfile not exit");
                        break;
                    case 2:
                        startDownloadService();
                        break;


                    default:
                        break;
                }
            }
        };
        mWebview = findViewById(R.id.webview);
        initView();
//        mWebview.setOnTouchListener(this);

//        initTimer();


    }

    private void initView() {
//        linearLayout = (LinearLayout) findViewById(R.id.web_started);
//        imageView = (ImageView) findViewById(R.id.started_gif);
//        webviewError = (LinearLayout) findViewById(R.id.webview_error);



    }
}
