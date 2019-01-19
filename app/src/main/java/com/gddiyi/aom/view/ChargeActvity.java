package com.gddiyi.aom.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gddiyi.aom.R;
import com.hdy.hdylights.LedAndChargeManager;


/**
 * @author romygreat
 * @date 20180119
 *
 */
public class ChargeActvity extends Activity {
    String TAG="chargeOnclick";
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charge);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        registerReceiver(chargeBroadCast,intentFilter);
    }
    private void dialogMessage(String titles, String messages) {
               //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
               LayoutInflater inflater = getLayoutInflater();
               //调用Activity的getLayoutInflater()
               View view = inflater.inflate(R.layout.toast_style, null);
               //加載layout下的布局

               TextView title = view.findViewById(R.id.test1);
               title.setText(titles);
               //toast的标题
               TextView text = view.findViewById(R.id.test2);
               text.setText(messages);
               //toast内容
               Toast toast = new Toast(getApplicationContext());
               toast.setGravity(Gravity.CENTER, 12, 20);
               //setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
               toast.setDuration(20*1000);
               //setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
               toast.setView(view);
               //添加视图文件
               toast.show();
           }
    public void clickView(View view){
        Log.i(TAG, "clickView: i=="+i);
        switch (view.getId()){
            case R.id.charge:
             boolean ch=   LedAndChargeManager.switchCharge(LedAndChargeManager.SWITCH_ON);
                Log.i(TAG, "clickView: "+ch);
                break;
            case R.id.uncharge:
                dialogMessage("hello","this is hello");
             boolean uch=   LedAndChargeManager.switchCharge(LedAndChargeManager.SWITCH_OFF);
                Log.i(TAG, "clickView: "+uch);
                break;
            case R.id.redColor:
                if (i%2==0)
                { boolean red= LedAndChargeManager.setLedColor(LedAndChargeManager.RED_OPEN);
                    Log.i(TAG, "clickView:red "+red);
                }
                else {
                    LedAndChargeManager.setLedColor(LedAndChargeManager.RED_CLOSE);
                }
                i++;
                break;
            case R.id.bulueColor:
                if (i%2==0)
                { boolean red=LedAndChargeManager.setLedColor(LedAndChargeManager.BLUE_OPEN);
                    Log.i(TAG, "clickView:blue "+red);
                }
                else {
                    LedAndChargeManager.setLedColor(LedAndChargeManager.BLUE_CLOSE);
                }
                i++;
                break;
            case R.id.greenColor:
                if (i%2==0)
                { boolean red=LedAndChargeManager.setLedColor(LedAndChargeManager.GREEN_OPEN);
                    Log.i(TAG, "clickView:green "+red);
                }
                else {
                    LedAndChargeManager.setLedColor(LedAndChargeManager.GREEN_CLOSE);
                }
                i++;
                break;
            default:
                break;

        }
    }
    public void printMytips(String str) {
        try {
            {Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();}
        }catch (NullPointerException e){
            e.printStackTrace();
            Log.i(TAG, "printMytips: ");
        }
    }
    BroadcastReceiver chargeBroadCast=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           String acttion= intent.getAction();
           if (acttion.equals(Intent.ACTION_POWER_CONNECTED)){
               printMytips("手机已连接，正在充电");
           }
           else if (acttion.equals(Intent.ACTION_POWER_DISCONNECTED)){
               printMytips("手机拔出充电开关，请注意");
           }

        }
    };
}
