package com.gddiyi.aom.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gddiyi.aom.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestActivity extends Activity {
    Runnable runnable;
    String TAG="TestSCH";
    ScheduledExecutorService scheduledExecutorService;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
//        scheduledExecutorService= (ScheduledExecutorService) Executors.s
    }
   public void  clickView(View view){
       runnable=new Runnable() {
           @Override
           public void run() {
               try {
                   Log.i(TAG, "run: beigin");
                   Thread.sleep(5000);
                   Log.i(TAG, "run: end");
               } catch (InterruptedException e) {
               }
           }
       };
        switch (view.getId()){
            case R.id.execute:
                Log.i(TAG, "clickView: thread.currentThread"+Thread.currentThread());
                Log.i(TAG, "clickView: isShutdown()"+scheduledExecutorService.isShutdown());
         if (scheduledExecutorService.isShutdown()){
             scheduledExecutorService.schedule(runnable,2,TimeUnit.SECONDS);}
             scheduledExecutorService.execute(runnable);
                Log.i(TAG, "clickView: if");
                break;
            case R.id.shutdown:
                Log.i(TAG, "clickView: shutDown");
//                scheduledExecutorService.shutdown();
                scheduledExecutorService.shutdownNow();
                Log.i(TAG, "clickView: shutsch");
                break;
            default:break;
        }
   }
}
