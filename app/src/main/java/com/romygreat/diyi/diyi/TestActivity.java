//package com.appcation.diyi.diyiproject;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//
//import com.tencent.smtt.sdk.ValueCallback;
//import com.tencent.smtt.sdk.WebSettings;
//import com.tencent.smtt.sdk.WebView;
//
//import jsinterface.JavaScriptinterface;
//
//public class TestActivity extends Activity {
//    private WebView mWebview;
//    String TAG="MYTest";
//    private int mTime;
//    private JavaScriptinterface javaScriptinterface;
//    private Button mButton;
//
//    //this project is X5 frameWork is OK
//    //正常加载x5内核
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
//        setContentView(R.layout.activity_main);
//
//        mWebview = findViewById(R.id.webview);
////        mWebview.loadUrl("http://119.23.63.140/shop");
//        javaScriptinterface=new JavaScriptinterface(this);
//        mButton=findViewById(R.id.calljs);
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "onClick: test()");
//                mWebview.loadUrl("javascript:test1('\" + msg + \"')");
////                mWebview.loadUrl("javascript:javacalljswithargs(\"params\")");
////                Log.i(TAG, "onClick:test()1");
//            }
//        });
//        mWebview.evaluateJavascript("javascript:test()", new ValueCallback<String>() {
//            @Override
//            public void onReceiveValue(String s) {
////                Log.i(TAG, "onReceiveValue: test()"+s);
//            }
//        });
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
////        mWebview.loadUrl("http://om.gddiyi.com/playad/");
////        mWebview.loadUrl("http://om.gddiyi.com/");
//        mWebview.addJavascriptInterface(javaScriptinterface,
//                "android");
//        mWebview.loadUrl("file:///android_asset/test.html");
//
////        mWebview.loadUrl("https://www.baidu.com/");
//        WebSettings settings = mWebview.getSettings();
//        settings.setLoadWithOverviewMode(true);
////        settings.setBuiltInZoomControls(true);
//        settings.setJavaScriptEnabled(true);
//        settings.setUseWideViewPort(true);
////        settings.setSupportZoom(true);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        settings.setGeolocationEnabled(true);
//        settings.setDomStorageEnabled(true);
//        settings.setDatabaseEnabled(true);
//    }
//
//
//
//}
