package com.gddiyi.aom.view;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.gddiyi.aom.R;

import com.gddiyi.aom.constant.VSConstances;
import com.gddiyi.aom.presenter.WifiAutoConnectManager;
import com.gddiyi.aom.wifypresenter.WifyPresenterImpl;


import java.util.ArrayList;
import java.util.List;

public class WifyFragment extends Fragment implements
        CompoundButton.OnCheckedChangeListener, View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int UPDATE_WIFY = 666;
    Context mContext;
    ListView listView;
    Switch radioButton;
    String TAG = "TestWify";
    WifiManager mWifiManager;
    ArrayList<ScanResult> wifiListAdapter = new ArrayList<>();
    WifiAutoConnectManager wac;
    WifiInfo wifiInfo;
    TextView textView;
    TextView connectedTextView,availableWifyTextView;
    String[] wifyList;
    ArrayAdapter<String> adapter;
    int l=0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mWifiManager = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wac = new WifiAutoConnectManager(mWifiManager);
        wifiInfo = mWifiManager.getConnectionInfo();
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
        MyWifyBrocastReceiver myWifyBrocastReceiver = new MyWifyBrocastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        mContext.registerReceiver(myWifyBrocastReceiver, intentFilter);
        wac.mHandler = new Handler(mContext.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.i(TAG, "handleMessage: " + msg.obj);
                Log.i(TAG, "handleMessage: what"+msg.what);
                switch (msg.what){
                    case UPDATE_WIFY:
                        wifiListAdapter = (ArrayList<ScanResult>) mWifiManager.getScanResults();
                        wifyList = new String[wifiListAdapter.size()];
                        for (ScanResult scanResult : wifiListAdapter) {
                            Log.i(TAG, "handleMessage 123" + scanResult.SSID);
                            wifyList[l++] = scanResult.SSID;
                        }
                        l=0;
                        if (wifyList != null) {
                            adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, wifyList);
                            listView.setAdapter(adapter);
                            String connectedWIfy=mWifiManager.getConnectionInfo().getSSID();
                            if (connectedWIfy!=null && hasWifyConnected()){
                            connectedTextView.setText(getString(R.string.connected)+"       "+connectedWIfy);}
                        }
                        break;
                    case VSConstances.CONNECTED_SUCEESS:
                        Intent intent=new Intent(mContext,MainActivity.class);
                        startActivity(intent);
                        break;
                    default:break;
                }

            }
        };
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.wifylayout, container, false);
        listView = myView.findViewById(R.id.wifyList);
        radioButton = myView.findViewById(R.id.openOrClose);
        radioButton.setOnCheckedChangeListener(this);
        textView = myView.findViewById(R.id.fresh);
        connectedTextView=myView.findViewById(R.id.connected);
        availableWifyTextView=myView.findViewById(R.id.availableWify);
        textView.setOnClickListener(this);
        radioButton.setChecked(true);
        mWifiManager.startScan();
        //if (mWifiManager.isWifiEnabled())
        wac.mHandler.sendEmptyMessageDelayed(UPDATE_WIFY,2100);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
        textView.setText("刷新");
        return myView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isChecked()) {
            mWifiManager.setWifiEnabled(true);
            listView.setVisibility(View.VISIBLE);
            connectedTextView.setVisibility(View.VISIBLE);
            textView.setClickable(true);
            availableWifyTextView.setVisibility(View.VISIBLE);
            textView.setText("刷新");
            wac.mHandler.sendEmptyMessageDelayed(UPDATE_WIFY,2000);
            Log.i(TAG, "onCheckedChanged:is check ");
        } else {
            mWifiManager.setWifiEnabled(false);
            textView.setClickable(false);
            listView.setVisibility(View.INVISIBLE);
            connectedTextView.setVisibility(View.INVISIBLE);
            listView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setText("请打开wify开关");
            connectedTextView.setText(getString(R.string.connectedwify));
            availableWifyTextView.setVisibility(View.INVISIBLE);
            Log.i(TAG, "onCheckedChanged: not check");
        }
    }
    @Override
    public void onClick(View v) {
        mWifiManager.startScan();
        wac.mHandler.sendEmptyMessageDelayed(UPDATE_WIFY,3000);
        Toast.makeText(mContext.getApplicationContext(),getString(R.string.freshTip),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: position" + position);
        if (mWifiManager.isWifiEnabled()){
            inputPassWord(position);
        }else {
            printMytips("请打开wify按钮");
        }

    }

    public class MyWifyBrocastReceiver extends BroadcastReceiver {
        WifiManager wifiManager;
        ArrayList<ScanResult> wifiListAdapter = new ArrayList<>();

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.i(TAG, "onReceive:action " + action);
            wifiManager = WifyPresenterImpl.getmWifiManager();
            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
//             wifi已成功扫描到可用wifi。
                List<ScanResult> scanResults = wifiManager.getScanResults();
            }
            Log.i(TAG, "onReceive:SSID " + wifiListAdapter.get(0).SSID);
        }
    }
    public void inputPassWord(final int wifyPosition) {
        final EditText inputedit=new EditText(mContext);
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(mContext);
        alertdialog.setTitle("请输入wify密码");
        alertdialog.setView(inputedit);
        alertdialog.setIcon(R.drawable.wify);
        inputedit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        alertdialog.setNegativeButton("取消",null);
        alertdialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String editTextString = inputedit.getText().toString();
                String SSID=wifyList[wifyPosition];
                if (!editTextString.equals("")) {
                    try{
                        wac.connect(SSID, editTextString,
                                "myAdmin".equals("")? WifiAutoConnectManager.WifiCipherType.WIFICIPHER_NOPASS: WifiAutoConnectManager.WifiCipherType.WIFICIPHER_WPA);
                    }
                    catch (Exception e){
                      printMytips("请检查网络或密码错误");
                    }
                }
            }
        });
        alertdialog.show();
    }

    public void printMytips(String str) {
        try {
            if(mContext!=null){
                Toast.makeText(mContext.getApplicationContext(), str, Toast.LENGTH_SHORT).show();}
        }catch (NullPointerException e){
            e.printStackTrace();
            Log.i(TAG, "printMytips: ");
        }
    }
 public boolean hasWifyConnected(){
     ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
     NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
     if(netInfo != null && netInfo.isAvailable()){
         if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
             //WiFi网络
             return true;
         } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {

         } else {
             //网络错误
         }
     }else{
         //网络错误
     }
     return false;
 }
}
