package com.gddiyi.aom.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThreadPool {
   static ExecutorService instance;

    public static ExecutorService getExeCutor(){
        if (instance==null)
        {instance= Executors.newFixedThreadPool(5);
        }
        return instance;
    }
}
