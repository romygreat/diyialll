package com.gddiyi.aom.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.gddiyi.aom.view.FirstBootActivity;

public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String TAG = "BootBroadcastReceiver";
    static final String action_boot = "android.intent.action.BOOT_COMPLETED";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction().toString();
        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            try {
                Log.i(TAG, "onReceive: boot");
                Thread.sleep(3000L);
                intent = new Intent(context, FirstBootActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}