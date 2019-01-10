package com.gddiyi.aom;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.gddiyi.aom.NetUtils.DownloadUtil;

import java.io.File;
import java.io.IOException;

public class JcallActivity extends Activity implements View.OnTouchListener {
    String TAG=getClass().getSimpleName();
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
//            ,
//            "android.permission.ACCESS_FINE_LOCATION"
    };
    String vedioURL="https://vdse.bdstatic.com//c28e7e9a5730e0dae8cc6b0fc5b85d00?authorization=bce-auth-v1%2Ffb297a5cc0fb434c971b8fa103e8dd7b%2F2017-05-11T09%3A02%3A31Z%2F-1%2F%2F6528af5e55ac29260b145e2080e9e2867dbd3026b21cebdd7196740a48ea7b49";
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview);
        TAG="MYTest";
        File file=new File("/sdcard/testdiyi.txt");
        requestPermission();
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.i(TAG, "onCreate: exception");
                // e.printStackTrace();
                e.printStackTrace();
            }
        }
    }
    public void onclickButton(View view){
        Log.i(TAG, "onclickButton: onclick");
        switch (view.getId()){
            case R.id.finish:finish();
                break;
            case R.id.download:
//                file:///sdcard/test.mp4
                DownloadUtil downloadUtil=DownloadUtil.get();
//                vedioURL="https://pic.ibaotu.com/01/07/52/39W888piC3ty.mp4";
              //  vedioURL="";
                downloadUtil.download(vedioURL, "zdiyi", new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess() {
                        Toast.makeText(JcallActivity.this,"下载完成,diyi请点击android.intentMain准备进入播放视频",Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onDownloadSuccess: ");
                    }

                    @Override
                    public void onDownloading(int progress) {
                        Log.i(TAG, "onDownloading: "+progress);
                    }

                    @Override
                    public void onDownloadFailed() {
                        Log.i(TAG, "onDownloadFailed: ");
                    }
                });
                break;
        }

    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "onTouch:  JcallActivity");
        return false;
    }
    public void requestPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS_STORAGE,10);
    }
}
