package com.gddiyi.aom.view;

import android.app.Activity;
import android.os.Bundle;

import com.gddiyi.aom.R;

public class TestActivity extends Activity {
    X5WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x5view);
        mWebView=findViewById(R.id.webview);
        mWebView.loadUrl("http://om.gddiyi.com/");

    }
}
