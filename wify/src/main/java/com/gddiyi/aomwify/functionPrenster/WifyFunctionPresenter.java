package com.gddiyi.aomwify.functionPrenster;

import android.content.Context;

/**
 *
 * 管理接口
 */
public interface WifyFunctionPresenter {
    void openWify();
    void closeWify();
    void connectWify();
    boolean checkIsConnected();
    void setContext(Context context);
    void setWifyPassWord();

}
