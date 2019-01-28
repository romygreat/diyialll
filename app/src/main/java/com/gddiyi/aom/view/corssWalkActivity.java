package com.gddiyi.aom.view;


import android.os.Bundle;

import com.gddiyi.aom.R;

import org.xwalk.core.XWalkActivity;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;

//这是crosswalk版本
public class corssWalkActivity extends XWalkActivity {
    private XWalkView xWalkWebView;
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
    }
}
