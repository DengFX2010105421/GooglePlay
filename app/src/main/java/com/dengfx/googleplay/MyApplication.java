package com.dengfx.googleplay;

import android.app.Application;
import android.content.Context;
import android.os.*;

import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {

    public static Context mContext;
    public static Handler mMainThreadHandler;
    public static int mMainThreadId;
    private static Map<String, String> mCachedJsonMap = new HashMap<>();

    public static Map<String,String> getCachedJsonMap(){
        return mCachedJsonMap;
    }

    @Override
    public void onCreate() {
        mContext = getApplicationContext();
        mMainThreadHandler = new Handler();
        mMainThreadId = android.os.Process.myTid();
        super.onCreate();
    }



}
