package com.gddiyi.aom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import jsinterface.JavaScriptinterface;

public class MainActivity extends Activity implements View.OnTouchListener {
    private WebView mWebview;
    String TAG="MYTest";
    private int mTime;
    private  JavaScriptinterface javaScriptinterface;
    private Button mButton;
    Handler mHandler;
    int mCount=0;
    String vedioURL;
    VideoFragment videoFragment;
    private Timer timer;
    private TimerTask task;
    private int currentTime = 0;

    //this project is X5 frameWork is OK
    //正常加载x5内核
    //使用fragment不能全屏问题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen();
        setContentView(R.layout.layout_test);
        mWebview = findViewById(R.id.webview);
//        mWebview.loadUrl("http://119.23.63.140/shop");
        mWebview.setOnTouchListener(this);
        Diyi_setWebSettings();
        initTimer();
        startDownloadService();
    }

    private void fullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏

    }

    private void Diyi_setWebSettings() {
        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                Log.i(TAG, "onPageFinished: okhttp url=="+s);

            }
        });

        javaScriptinterface=new JavaScriptinterface(this);
        mWebview.loadUrl("http://om.gddiyi.com/");
        mWebview.addJavascriptInterface(javaScriptinterface,
                "android");
//        mWebview.loadUrl("file:///android_asset/test.html");
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
        //白屏调优代码
        mWebview.requestFocus();
        settings.setAppCacheEnabled(true);

        String appCachePath = getApplicationContext().getCacheDir().getPath()+ "/webcache";
        settings.setAppCachePath(appCachePath);
        settings.setDatabasePath(appCachePath);
        vedioURL=
                "https://vdse.bdstatic.com//c28e7e9a5730e0dae8cc6b0fc5b85d00?authorization=bce-auth-v1%2Ffb297a5cc0fb434c971b8fa103e8dd7b%2F2017-05-11T09%3A02%3A31Z%2F-1%2F%2F6528af5e55ac29260b145e2080e9e2867dbd3026b21cebdd7196740a48ea7b49";
    }
    private void startDownloadService() {
        Intent downLoadIntent=new Intent(this,DownLoadService.class);
        startService(downLoadIntent);
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
//         file:///sdcard/test.mp4
//        vedioURL=" file:///sdcard/zdiyi/test.mp4";
//        mWebview.loadUrl(vedioURL);
        mHandler=new Handler(getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        Intent intent=new Intent(MainActivity.this,VideoActivity.class);
                        startActivity(intent);

                     //   finish();//添加finish()
                        break;
//                        FragmentManager fragmentManager=getFragmentManager();
//                        videoFragment=new VideoFragment ();
//                        FragmentTransaction transaction = fragmentManager.
//                                beginTransaction();
//                        transaction.replace(R.id.mainActivityId , videoFragment);//修改id问题
//                        transaction.commit();
                    default:break;
                }
                //super.handleMessage(msg);
                // mWebview.loadUrl("http://119.23.63.140/shop");

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mCount++;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //有按下动作时取消定时
                stopTimer();
                Log.i(TAG, "onTouch: stopTimer");
                break;
            case MotionEvent.ACTION_UP:
                //抬起时启动定时
                startTimer();
                Log.i(TAG, "onTouch: startTimer");
                break;
        }

//        mHandler.sendEmptyMessageDelayed(1,20000);
        return false;
    }
    private void startTimer() {
        //启动计时器
        /**
         * java.util.Timer.schedule(TimerTask task, long delay, long period)：
         * 这个方法是说，delay/1000秒后执行task,然后进过period/1000秒再次执行task，
         * 这个用于循环任务，执行无数次，当然，你可以用timer.cancel();取消计时器的执行。
         */
        initTimer();
        try {
            timer.schedule(task, 1000*20, 10000);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            initTimer();
            timer.schedule(task, 1000*30, Long.MAX_VALUE);
        }
        Log.i(TAG, "startTimer: onTouch");
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
        currentTime = 0;

//        if (timeTv != null) {
//            timeTv.setText(String.valueOf(currentTime));
//        }

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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }
}


