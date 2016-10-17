package com.dengfx.googleplay.proxy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 邓FX on 2016/10/13.
 */

public class ThreadPoolProxy {
    ThreadPoolExecutor mPoolExecutor;
    private int mCorePoolSize;
    private int mMaximumPoolSize;

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize) {
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
    }

    public void initThreadPoolExecutor (){
        if (mPoolExecutor == null || mPoolExecutor.isShutdown() || mPoolExecutor.isTerminated()){
            synchronized (this.getClass()){
                if (mPoolExecutor == null || mPoolExecutor.isShutdown() || mPoolExecutor.isTerminated()){
                    long keepAliveTime = 0;
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
                    mPoolExecutor = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize,keepAliveTime,unit,workQueue,threadFactory,handler);
                }
            }
        }
    }

    public Future<?> submit(Runnable task){
        initThreadPoolExecutor();
        Future<?> future = mPoolExecutor.submit(task);
        return future;
    }
    public void execute(Runnable task){
        initThreadPoolExecutor();
        mPoolExecutor.execute(task);
    }
    public void remove(Runnable task){
        initThreadPoolExecutor();
        mPoolExecutor.remove(task);
    }


}
