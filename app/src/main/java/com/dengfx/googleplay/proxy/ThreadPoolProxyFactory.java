package com.dengfx.googleplay.proxy;

/**
 * Created by 邓FX on 2016/10/13.
 */

public class ThreadPoolProxyFactory {

    private static ThreadPoolProxy mNormalThreadPoolProxy;
    private static ThreadPoolProxy mDownloadThreadPoolProxy;

    public static ThreadPoolProxy getNormalThreadPoolProxy(){
        if (mNormalThreadPoolProxy == null){
            synchronized (ThreadPoolProxyFactory.class){
                if (mNormalThreadPoolProxy == null){
                    mNormalThreadPoolProxy = new ThreadPoolProxy(5,5);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }

    public static ThreadPoolProxy getDownloadThreadPoolProxy(){
        if (mDownloadThreadPoolProxy == null){
            synchronized (ThreadPoolProxyFactory.class){
                if (mDownloadThreadPoolProxy == null){
                    mDownloadThreadPoolProxy = new ThreadPoolProxy(3,3);
                }
            }
        }
        return mDownloadThreadPoolProxy;
    }
}
