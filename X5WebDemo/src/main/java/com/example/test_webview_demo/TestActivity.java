package com.example.test_webview_demo;

import android.app.Activity;
import android.os.Bundle;

import com.example.test_webview_demo.utils.X5WebView;

public class TestActivity extends Activity {
    X5WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x5view);
        mWebView=findViewById(R.id.webview);
        mWebView.loadUrl("https://baidu.com/");

    }
}
