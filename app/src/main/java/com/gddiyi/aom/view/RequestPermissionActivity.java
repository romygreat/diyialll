package com.gddiyi.aom.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.gddiyi.aom.R;

public class RequestPermissionActivity extends Activity {
    String TAG="request";
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requestpermission);
        requestPermission();
    }
    public void requestPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        { ActivityCompat.requestPermissions(this,
                PERMISSIONS_STORAGE,10);}
                else {
            launcherMainActvity();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        launcherMainActvity();
    }
    public void launcherMainActvity(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        Log.i(TAG, "onRequestPermissionsResult: ");
        finish();
    }
}
