package com.gddiyi.aomwify.presenter;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileIoPrensenter {
    public String ReadFile(String path) {
        File file = new File(path);
        if (file.exists()){
            Log.i("fileExist", "ReadFile: ");
        }else {
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

    public void writeFile(String filePath, String sets) {
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
            Log.e("IO", "writeFile: "+e.toString());
        }
    }
}
