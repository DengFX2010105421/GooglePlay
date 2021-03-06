package com.dengfx.googleplay.utils;

import android.content.Context;
import android.content.res.Resources;

import com.dengfx.googleplay.MyApplication;

public class UIUtils {
    /**
     * 得到上下文
     */
    public static Context getContext() {
        return MyApplication.mContext;
    }

    /**
     * 得到Resource对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中的字符串信息
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 得到String.xml中的字符串数组信息
     */
    public static String[] getStrings(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到Color.xml中的颜色信息
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 得到应用程序包名
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    public static int dip2px(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);
    }

    public static int px2dip(int px) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }
}
