package com.dengfx.googleplay.config;


import com.dengfx.googleplay.utils.LogUtils;

public class Constants {
    /*
    LogUtils.LEVEL_ALL:打开日志(显示所有的日志输出)
    LogUtils.LEVEL_OFF:关闭日志(屏蔽所有的日志输出)
     */
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;
    public static final long PROTOCOL_TIMEOUT = 2 * 60 * 60 * 1000;

    public static final class URLS {

        public static final String BASEURL = "http://192.168.11.18:8080/GooglePlayServer/";
        public static final String IMGBASEURL = BASEURL + "image?name=";
    }

}
