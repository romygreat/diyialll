package com.gddiyi.aomwify;

import android.content.Context;

public interface WifyFunctionPresenter {
    void openWify();
    void closeWify();
    void connectWify();
    boolean checkIsConnected();
    void setContext(Context context);

}
