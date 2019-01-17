package com.gddiyi.aom.netutils;

import android.content.Context;
import android.content.res.Resources;

import com.gddiyi.aom.R;

public class ADFilterUtil {
    public static  boolean booleanhasAd(Context context, String url){
            Resources res= context.getResources();
            String[]adUrls =res.getStringArray(R.array.adBlockUrl);
            for(String adUrl :adUrls){
                if(url.contains(adUrl)){
                    return true;
                }
            }
            return false;
        }
    }

