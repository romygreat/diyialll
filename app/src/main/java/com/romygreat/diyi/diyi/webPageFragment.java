package com.romygreat.diyi.diyi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

public class webPageFragment extends Fragment {
    View mView;
    String TAG="webPageFragmentTest";
    WebView mWebview;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i(TAG, "onCreateView: onclick");
        mView = inflater.inflate(R.layout.webpage_fragment, container, false);
        mWebview=mView.findViewById(R.id.webviewfragment);
        Log.i(TAG, "onCreateView: ");
        return mView;

    }

    @Override
    public void onStart() {
        super.onStart();
//        mWebview.loadUrl("http://om.gddiyi.com/playad/");


//        mWebview.loadUrl("https://www.baidu.com/");

        mWebview.loadUrl("http://om.gddiyi.com/");
        WebSettings settings = mWebview.getSettings();
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadWithOverviewMode(true);
//        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
       // settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setGeolocationEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
//        mWebview.setWebViewClient(new WebViewClient(){
//                                      @Override
//                                      public void onPageFinished(WebView webView, String s) {
//                                          super.onPageFinished(webView, s);
//                                          mWebview.setVisibility(View.VISIBLE);
//                                          mtextView.setVisibility(View.INVISIBLE);
//                                      }
//                                  }
//        );
        mWebview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i==100){
                    mWebview.setVisibility(View.VISIBLE);

                    Log.i(TAG, "onProgressChanged: "+i);
                }
            }
        });
    }
}
