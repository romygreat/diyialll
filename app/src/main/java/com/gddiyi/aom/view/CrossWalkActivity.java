package com.gddiyi.aom.view;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gddiyi.aom.R;
import com.gddiyi.aom.constant.VSConstances;
import com.gddiyi.aom.jsinterface.JavaScriptinterface;
import com.gddiyi.aom.presenter.WifiAutoConnectManager;
import com.hdy.hdylights.LedAndChargeManager;

import org.xwalk.core.XWalkActivity;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkView;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import static android.os.Looper.getMainLooper;


//这是crosswalk版本

/**
 *
 *
 */
public class CrossWalkActivity extends BaseActivity implements View.OnTouchListener, JavaScriptinterface.NoticefyPay {
    private XWalkView mWebView;

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
    int reload = 1;


    //优化代码
    LinearLayout linearLayout;
    ImageView imageView;
    LinearLayout webviewError;
    View main;
    XWalkSettings settings;
    boolean xwalkWebviewReady = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (xwalkWebviewReady) {
            mWebView.onDestroy();
        }
//        if(hideSystemKeyReceiver!=null){
////        unregisterReceiver(hideSystemKeyReceiver);
//        }
    }

    HideSystemKeyReceiver hideSystemKeyReceiver = new HideSystemKeyReceiver();

    @Override
    protected void onXWalkReady() {
        Log.i(TAG, "onXWalkReady: order");
        if (mWebView != null) {
            //api 可能not ready
            mWebView.setOnTouchListener(this);

        }
        xwalkWebviewReady = true;
        setWebSettings();
        mWebView.load("http://om.gddiyi.com/", null);
        mHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        File downloadFile = new File(Environment.getExternalStorageDirectory(), VSConstances.AD);
                        if (downloadFile.exists()) {
                            Intent intent = new Intent(CrossWalkActivity.this, VideoActivity.class);
                            startActivity(intent);
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
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.i(TAG, "dispatchKeyEvent:== " + event.getKeyCode());
        Log.i(TAG, "dispatchKeyEvent: ==" + super.dispatchKeyEvent(event));
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //注册广播
        //  brocastBottom();
    }

    public void brocastBottom() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.DISABLE_CONTROL");
        registerReceiver(hideSystemKeyReceiver, intentFilter);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DISABLE_CONTROL");
        sendBroadcast(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: order");
        if (!isNetworkAvailable()) {
            Intent intent = new Intent(this, FirstBootActivity.class);
//            Intent intent1=getIntent();
//            String  correctPassword=  intent1.getStringExtra(getString(R.string.falsePassword));
//            if (!Boolean.parseBoolean(correctPassword)){
//                Log.i(TAG, "onCreate:boolean "+Boolean.parseBoolean(correctPassword));
//                intent.putExtra(getString(R.string.falsePassword),"true");
//
//            }
            startActivity(intent);
            this.finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crosswalk);
        mWebView = (XWalkView) findViewById(R.id.xwalkWebView);
        initView();

        // turn on debugging
//        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);

    }

    private void setWebSettings() {

        settings = mWebView.getSettings();

        //注册javascript接口
        settings.setJavaScriptEnabled(true);

        javaScriptinterface = new JavaScriptinterface(this);

        mWebView.addJavascriptInterface(javaScriptinterface,
                "android");

        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);

        mWebView.setVisibility(View.INVISIBLE);
        XWalkResourceClient xWalkResourceClient = new XWalkResourceClient(mWebView) {
            @Override
            public void onLoadStarted(XWalkView view, String url) {
                super.onLoadStarted(view, url);
                loadReady();
            }

            @Override
            public void onLoadFinished(XWalkView view, String url) {
                mWebView.setVisibility(View.VISIBLE);
                super.onLoadFinished(view, url);
                linearLayout.setVisibility(View.INVISIBLE);
                webviewError.setVisibility(View.INVISIBLE);
            }
        };
        mWebView.setResourceClient(xWalkResourceClient);
    }

    public void loadReady() {
        Glide.with(CrossWalkActivity.this).load(R.mipmap.loading_circle).asGif().into(imageView);
    }

    private void initView() {
        linearLayout = (LinearLayout) findViewById(R.id.web_started);
        imageView = (ImageView) findViewById(R.id.started_gif);
        webviewError = (LinearLayout) findViewById(R.id.webview_error);


        loadReady();
    }

    public void setWify() {
        final EditText inputedit = new EditText(this);
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("您需要设置wify密码权限");
        alertdialog.setView(inputedit);
        inputedit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        alertdialog.setNegativeButton(getString(R.string.cancel), null);
        alertdialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String editTextString = inputedit.getText().toString();
                Log.i(TAG, "onClick: " + editTextString);
                if (editTextString.isEmpty()) {
                    //重新设置SSID到数据库
                    SharedPreferences mSharedPreferences = getSharedPreferences(getString(R.string.diyi), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString(getString(R.string.SSID), "..");
                    editor.commit();
                    //提交修改
                    WifiManager mWifiManager = (WifiManager) CrossWalkActivity.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiAutoConnectManager wac = new WifiAutoConnectManager(mWifiManager);
                    wac.removeWifiBySsid(mWifiManager);

                    Intent intent = new Intent(CrossWalkActivity.this, FirstBootActivity.class);
                    intent.putExtra(getString(R.string.removeWify), "removeWify");
                    startActivity(intent);
                    //退出主页面
//                    CrossWalkActivity.this.finish();

                    //退出wify修改wify密码
                    // 不退出本activity,否则会wify跳转activity加载会很慢

                }
            }
        });
        alertdialog.show();
    }

    @Override
    public boolean readyPay() {
        //一、准备支付阶段，取消定时功能，使用stoptimer
        //二、同时使用一个boolean,变量为touch=false,触摸屏幕失效
        Log.i(TAG, "readyPay: ");
        touch = false;
        stopTimer();
        Log.i(TAG, "readyPay: stopTimer");
        //继续监听页面支付情况
        return true;
    }

    @Override
    public boolean finishPay(int time) {
        Log.i(TAG, "finishPay:传进来time " + time);
        boolean b = super.finishPay(time);
        //一、完成支付，无论是否为0，此时应该重新打开计时功能
        //二、同时将touch变量设置为一个boolean=true
        //三、将时间time保存，开启充电功能后，开启线程定时，定时取消充电功能
        Log.i(TAG, "finishPay: time" + time);
        if (time > 0) {
            Log.i(TAG, "finishPay:charge " + LedAndChargeManager.switchCharge(LedAndChargeManager.SWITCH_ON));

            Log.i(TAG, "finishPay: red " + LedAndChargeManager.setLedColor(LedAndChargeManager.RED_OPEN));
        }
        touch = true;
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
    protected void onResume() {
        super.onResume();
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "迪溢科技提醒您,请检查网络", Toast.LENGTH_LONG).show();
        }
        startTimer();
        Log.i(TAG, "onResume: " + VSConstances.SET_FROM_WIFY);

//        if (VSConstances.SET_FROM_WIFY){
//            if (mWebView.)
//           mWebView.reload(0);
//            VSConstances.SET_FROM_WIFY=false;
//        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        BACKPRESS_TIME = 0;
        Log.i(TAG, "onTouch: ");
        mCount++;

        Log.i(TAG, "onTouch: " + Build.SERIAL);
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
        if (touch) {
            initTimer();
            try {
                timer.schedule(task, VSConstances.TIME_UNIT_MIN, VSConstances.TIME_UNIT_MIN);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                initTimer();
                timer.schedule(task, VSConstances.TIME_UNIT_MIN, Long.MAX_VALUE);
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

    class HideSystemKeyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        try {
            String staus = "statusbar";
            Object service = getSystemService(staus);
            Class<?> statusbarManager = Class.forName("android.app.StatusBarManager");
            Method test = statusbarManager.getMethod("collapse");
            test.invoke(service);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
