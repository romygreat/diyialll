package com.gddiyi.aom.presenter;

import android.util.Log;

import com.gddiyi.aom.model.MyThreadPool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import rx.functions.Func0;

public class FileIoPrensenter {
    public String ReadFile(final String path) {
        String laststr = null;
        final Future<String> result = MyThreadPool.getExeCutor().submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                File file = new File(path);
                if (file.exists()) {
                    Log.i("fileExist", "ReadFile: ");
                } else {
                    Log.i("fileExist", "ReadFile:not  ");
                }
                BufferedReader reader = null;
                String laststr = "";
                try {
                    reader = new BufferedReader(new FileReader(file));
                    String tempString = null;
                    int line = 1;
                    //一次读入一行，直到读入null为文件结束
                    while ((tempString = reader.readLine()) != null) {
                        //显示行号
                        System.out.println("line " + line + ": " + tempString);
                        laststr = tempString;
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e1) {
                        }
                    }
                }
                return laststr;
            }
        });

        try {
            Log.i("future", "ReadFile: " + result.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            laststr = result.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("future", "ReadFile: " + laststr);
        return laststr;
    }

    public void writeFile(final String filePath, final String sets) {
        MyThreadPool.getExeCutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("writeFile", "writeFile: ");
                    FileWriter fw = new FileWriter(filePath);
                    PrintWriter out = new PrintWriter(fw);
                    out.write(sets);
                    out.println();
                    fw.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "writeFile: " + e.toString());
                }
            }
        });

    }
}
