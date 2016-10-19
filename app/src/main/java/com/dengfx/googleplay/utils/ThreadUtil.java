package com.dengfx.googleplay.utils;

import android.os.Handler;

import com.dengfx.googleplay.MyApplication;

/**
 * Created by DengFX on 2016/10/7.
 */

public class ThreadUtil {
    private static Handler handler = MyApplication.mMainThreadHandler;

    public static void runInThread(Runnable r) {
        new Thread(r).start();
    }

    public static void runUIThread(Runnable r) {
        handler.post(r);//new Message  sendMessage-->handleMessage(); r.run();
    }
}
