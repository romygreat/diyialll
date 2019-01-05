package com.romygreat.diyi.diyi;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView mWebview;
    String TAG="MYTest";
    private int mTime;
    TextView mtextView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager=MainActivity.this.getSupportFragmentManager();
        webPageFragment webPageFragment=new webPageFragment();
        FragmentTransaction transaction = fragmentManager.
                beginTransaction();
        transaction.replace(R.id.webviewmain, webPageFragment);//修改id问题
        transaction.commit();

//        mWebview = findViewById(R.id.webview);
//        mtextView=findViewById(R.id.textView);
////        mWebview.loadUrl("http://119.23.63.140/shop");
//        mWebview.setVisibility(View.INVISIBLE);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: already execute");
                FragmentManager fragmentManager=MainActivity.this.getSupportFragmentManager();
                webPageFragment webPageFragment=new webPageFragment();
                FragmentTransaction transaction = fragmentManager.
                        beginTransaction();
                transaction.replace(R.id.webviewmain, webPageFragment);//修改id问题
                transaction.commit();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
//        mWebview.loadUrl("http://om.gddiyi.com/playad/");
////        mWebview.loadUrl("https://www.baidu.com/");
//        WebSettings settings = mWebview.getSettings();
//        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        settings.setLoadWithOverviewMode(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setJavaScriptEnabled(true);
//        settings.setUseWideViewPort(true);
//        settings.setSupportZoom(true);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
////        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        settings.setGeolocationEnabled(true);
//        settings.setDomStorageEnabled(true);
//        settings.setDatabaseEnabled(true);
////        mWebview.setWebViewClient(new WebViewClient(){
////                                      @Override
////                                      public void onPageFinished(WebView webView, String s) {
////                                          super.onPageFinished(webView, s);
////                                          mWebview.setVisibility(View.VISIBLE);
////                                          mtextView.setVisibility(View.INVISIBLE);
////                                      }
////                                  }
////        );
//        mWebview.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public void onProgressChanged(WebView webView, int i) {
//                super.onProgressChanged(webView, i);
//                if (i==100){
//                    mWebview.setVisibility(View.VISIBLE);
//                    mtextView.setVisibility(View.INVISIBLE);
//                    Log.i(TAG, "onProgressChanged: "+i);
//                }
//            }
//        });
    }
}


