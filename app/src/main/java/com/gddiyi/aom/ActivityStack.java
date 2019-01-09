package com.gddiyi.aom;

import android.app.Activity;

import java.util.Stack;

public  class  ActivityStack extends Stack<Activity> {
    static ActivityStack instance;
    private  ActivityStack(){}
    static ActivityStack getInstance(){
        if (instance==null){
        instance=new ActivityStack();
        }

        return instance;

    }


}
