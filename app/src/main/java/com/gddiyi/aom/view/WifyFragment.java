package com.gddiyi.aom.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import android.text.TextUtils;
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
    TextView connectedTextView, availableWifyTextView;
    String[] wifyList;
    ArrayAdapter<String> adapter;
    SharedPreferences mSharedPreferences;
    String removeWify;
    MyWifyBrocastReceiver myWifyBrocastReceiver;
    int l = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        FirstBootActivity firstBootActivity = (FirstBootActivity) context;
        removeWify = firstBootActivity.removewify;
        mSharedPreferences = mContext.getSharedPreferences(getString(R.string.diyi), Context.MODE_PRIVATE);
        mWifiManager = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wac = new WifiAutoConnectManager(mWifiManager);
        wifiInfo = mWifiManager.getConnectionInfo();
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
        myWifyBrocastReceiver = new MyWifyBrocastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        mContext.registerReceiver(myWifyBrocastReceiver, intentFilter);

        wac.mHandler = new Handler(mContext.getMainLooper()) {
            String connectedWIfy;
            @Override
            public void handleMessage(Message msg) {
                Log.i(TAG, "handleMessage: " + msg.obj);
                Log.i(TAG, "handleMessage: what" + msg.what);
                switch (msg.what) {
                    case UPDATE_WIFY:
                        wifiListAdapter = (ArrayList<ScanResult>) mWifiManager.getScanResults();
                        wifyList = new String[wifiListAdapter.size()];
                        for (ScanResult scanResult : wifiListAdapter) {
                            Log.i(TAG, "handleMessage SSID" + scanResult.SSID);
                            if (!"".equals(scanResult.SSID)) {
                                wifyList[l++] = scanResult.SSID;
                            }
                        }
                        l = 0;
                        if (wifyList != null) {
                            adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, wifyList);
                            listView.setAdapter(adapter);
                            connectedWIfy = wac.getConnectedWify();
                            Log.i(TAG, "handleMessage:connect " + connectedWIfy);
                            if (connectedWIfy != null && hasWifyConnected()) {
                                 //这里存在比较多的if判断语句，目的不让报错
                                if (mContext != null && (connectedTextView!=null)
                                        && WifyFragment.this.isAdded() &&(WifyFragment.this.isVisible())) {
                                    try {
                                        connectedTextView.setText(getString(R.string.connected) + "       " + connectedWIfy);
                                    } catch (Exception e) {
                                    } finally {

                                    }

                                } else {

                                    wac.mHandler.sendEmptyMessage(1005);
                                }
                            }
                        }
                        break;

                    case VSConstances.CONNECTED_SUCEESS:
                        String SSID1 = mSharedPreferences.getString(getString(R.string.SSID), "..");
                        Log.i(TAG, "handleMessage: " + SSID1);
                        if (connectedWIfy.equals(SSID1)) {
                            printMytips("即将进入主界面");
                            sendEmptyMessageDelayed(1005, 1500);
                        }
                        break;
                    case 1005: {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(intent);
                        intent.putExtra("reload","reload");
                        ((FirstBootActivity) mContext).finish();
                        //WifyFragment.this.onDestroy();
                    }
                    case 1006:
                        printMytips("请检查网络或密码错误");
                    break;
                    default:
                        break;
                }

            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        wac.mHandler.sendEmptyMessageDelayed(UPDATE_WIFY, 2100);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.wifylayout, container, false);
        listView = myView.findViewById(R.id.wifyList);
        radioButton = myView.findViewById(R.id.openOrClose);
        radioButton.setOnCheckedChangeListener(this);
        textView = myView.findViewById(R.id.fresh);
        connectedTextView = myView.findViewById(R.id.connected);
        availableWifyTextView = myView.findViewById(R.id.availableWify);
        textView.setOnClickListener(this);
        radioButton.setChecked(true);
        mWifiManager.startScan();
        //if (mWifiManager.isWifiEnabled())
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
        textView.setText(getString(R.string.fresh));

        return myView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isChecked()) {
            mWifiManager.setWifiEnabled(true);
            listView.setVisibility(View.VISIBLE);
            connectedTextView.setVisibility(View.VISIBLE);
            textView.setClickable(true);
            availableWifyTextView.setVisibility(View.VISIBLE);
            textView.setText(getString(R.string.fresh));
            wac.mHandler.sendEmptyMessageDelayed(UPDATE_WIFY, 2000);
            //有时候2秒时间加载的wify已连接显示为无，需要再一次刷新
            wac.mHandler.sendEmptyMessageDelayed(UPDATE_WIFY, 6000);
            Log.i(TAG, "onCheckedChanged:is check ");
        } else {
            mWifiManager.setWifiEnabled(false);
            textView.setClickable(false);
            listView.setVisibility(View.INVISIBLE);
            connectedTextView.setVisibility(View.INVISIBLE);
            listView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setText(getString(R.string.openorclosewify));
            connectedTextView.setText(getString(R.string.connectedwify));
            availableWifyTextView.setVisibility(View.INVISIBLE);
            Log.i(TAG, "onCheckedChanged: not check");
        }
    }

    @Override
    public void onClick(View v) {
        mWifiManager.startScan();
        wac.mHandler.sendEmptyMessageDelayed(UPDATE_WIFY, 3000);
        Toast.makeText(mContext.getApplicationContext(), getString(R.string.freshTip), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: position" + position);
        if (mWifiManager.isWifiEnabled()) {
            inputPassWord(position);
        } else {
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
//                Log.i(TAG, "onReceive: "+);
            }

        }
    }

    public void inputPassWord(final int wifyPosition) {
        final EditText inputedit = new EditText(mContext);
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(mContext);
        alertdialog.setTitle(getString(R.string.wifytitle));
        alertdialog.setView(inputedit);
        alertdialog.setIcon(R.drawable.wify);
        inputedit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

        alertdialog.setNegativeButton(getString(R.string.cancel), null);
        alertdialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String editTextString = inputedit.getText().toString();
                String SSID = wifyList[wifyPosition];
                //轻量级数据存储
                Log.i(TAG, "onClick: SSID==" + SSID);
                if (!TextUtils.isEmpty(editTextString)) {
                    try {
                        wac.connect(SSID, editTextString,
                                "myAdmin".equals("") ? WifiAutoConnectManager.
                                        WifiCipherType.WIFICIPHER_NOPASS : WifiAutoConnectManager.WifiCipherType.WIFICIPHER_WPA);
                        setSharePreference(SSID);
                        Log.i(TAG, "onClick: editString==" + editTextString);
                        printMytips("正准备连接网络，请耐心等待");
                    } catch (Exception e) {
                        printMytips("请检查网络或密码错误");
                    }
                } else

                {
//                    Log.i(TAG, "onClick: SSID" + SSID);
//                    wac.connect(SSID, "gddiyi2018",
//                            "myAdmin".equals("") ? WifiAutoConnectManager.
//                                    WifiCipherType.WIFICIPHER_NOPASS : WifiAutoConnectManager.WifiCipherType.WIFICIPHER_WPA);
//
//                    Intent intent = new Intent(mContext, MainActivity.class);
//                    mContext.startActivity(intent);
                    printMytips("密码错误，请重新连接");
                }
            }
        });
        alertdialog.show();
    }

    public void printMytips(String str) {
        try {
            if (mContext != null) {
                Toast.makeText(mContext.getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.i(TAG, "printMytips: ");
        }
    }

    public boolean hasWifyConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isAvailable()) {
            if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                //WiFi网络
                return true;
            } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {

            } else {
                //网络错误
            }
        } else {
            //网络错误
        }
        return false;
    }

    public void setSharePreference(String SSID) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (SSID != null) {
            editor.putString(getString(R.string.SSID), SSID);
        }
        editor.commit();//提交修改
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mContext.unregisterReceiver(myWifyBrocastReceiver);

    }
}
