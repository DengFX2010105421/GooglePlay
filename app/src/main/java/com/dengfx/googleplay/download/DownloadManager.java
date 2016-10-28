package com.dengfx.googleplay.download;

import com.dengfx.googleplay.bean.ItemBean;
import com.dengfx.googleplay.proxy.ThreadPoolProxyFactory;
import com.dengfx.googleplay.utils.CommonUtils;
import com.dengfx.googleplay.utils.UIUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 邓FX on 2016/10/22.
 */

public class DownloadManager {

    public static final int STATE_UNDOWNLOAD = 0;//未下载
    public static final int STATE_DOWNLOADING = 1;//下载中
    public static final int STATE_PAUSE_DOWNLOAD = 2;//暂停下载
    public static final int STATE_WAITING_DOWNLOAD = 3;//等待下载
    public static final int STATE_DOWNLOAD_FAILED = 4;//下载失败
    public static final int STATE_DOWNLOADED = 5;//下载完成
    public static final int STATE_INSTALLED = 6;//已安装
    private static DownloadManager instance;
    private Map<String, DownloadInfo> downLoadInfosCachedMap = new HashMap<>();
    private List<DownloadInfoObserver> mDownloadInfoObservers = new ArrayList<>();

    private DownloadManager() {
    }

    public static DownloadManager getInstance() {
        if (instance == null) synchronized (DownloadManager.class) {
            if (instance == null)
                instance = new DownloadManager();
        }
        return instance;
    }

    public void download(DownloadInfo downloadInfo) {
        //=================当前状态：未下载=========================
        downloadInfo.currentState = DownloadManager.STATE_UNDOWNLOAD;
        notifyDownloadInfoObservers(downloadInfo);
        //========================================================

        //=================当前状态：等待中=========================
        downloadInfo.currentState = DownloadManager.STATE_WAITING_DOWNLOAD;
        notifyDownloadInfoObservers(downloadInfo);
        //========================================================

        DownloadTask downloadTask = new DownloadTask(downloadInfo);
        ThreadPoolProxyFactory.getDownloadThreadPoolProxy().submit(downloadTask);
        downLoadInfosCachedMap.put(downloadInfo.packageName, downloadInfo);

        downloadInfo.downloadTask = downloadTask;
    }

    public void pause(DownloadInfo downloadInfo) {
        //=================当前状态：下载暂停=========================
        downloadInfo.currentState = DownloadManager.STATE_PAUSE_DOWNLOAD;
        notifyDownloadInfoObservers(downloadInfo);
        //========================================================
    }

    public DownloadInfo getDownloadInfo(ItemBean itemBean) {

        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.packageName = itemBean.packageName;
        downloadInfo.downloadUrl = itemBean.downloadUrl;
        downloadInfo.size = itemBean.size;

        if (CommonUtils.isInstalled(UIUtils.getContext(), itemBean.packageName)) {
            downloadInfo.currentState = STATE_INSTALLED;
            return downloadInfo;
        }

        File saveFile = downloadInfo.getApkSavedFile();
        if (saveFile.exists() && saveFile.length() == itemBean.size) {
            downloadInfo.currentState = STATE_DOWNLOADED;
            return downloadInfo;
        }

        if (downLoadInfosCachedMap.containsKey(itemBean.packageName)) {
            return downLoadInfosCachedMap.get(itemBean.packageName);
        }

        downloadInfo.currentState = STATE_UNDOWNLOAD;
        return downloadInfo;
    }

    public void cancel(DownloadInfo downloadInfo) {
        //=================当前状态：未下载=========================
        downloadInfo.currentState = DownloadManager.STATE_UNDOWNLOAD;
        notifyDownloadInfoObservers(downloadInfo);
        ThreadPoolProxyFactory.getDownloadThreadPoolProxy().remove(downloadInfo.downloadTask);
        //========================================================
    }

    public synchronized void addDownloadInfoObserver(DownloadInfoObserver observer) {
        if (observer == null)
            throw new NullPointerException();
        if (!mDownloadInfoObservers.contains(observer)) {
            mDownloadInfoObservers.add(observer);
        }
    }

    public synchronized void removeDownloadInfoObserver(DownloadInfoObserver observer) {
        mDownloadInfoObservers.remove(observer);
    }

    public void notifyDownloadInfoObservers(DownloadInfo downloadInfo) {
        for (DownloadInfoObserver observer : mDownloadInfoObservers) {
            observer.onDownloadInfoChanged(downloadInfo);
        }
    }

    public interface DownloadInfoObserver {
        void onDownloadInfoChanged(DownloadInfo downloadInfo);
    }

    class DownloadTask implements Runnable {

        private DownloadInfo mDownloadInfo;

        DownloadTask(DownloadInfo downloadInfo) {
            mDownloadInfo = downloadInfo;
        }

        @Override
        public void run() {
            //=================当前状态：下载中=========================
            mDownloadInfo.currentState = DownloadManager.STATE_DOWNLOADING;
            notifyDownloadInfoObservers(mDownloadInfo);
            //========================================================
//            mDownloadInfo.range = mDownloadInfo.getRange();
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().get().url(mDownloadInfo.getFullDownloadUrl()).build();
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    boolean isPaused = false;
                    try (InputStream inputStream = response.body().byteStream();
                         FileOutputStream fileOutputStream = new FileOutputStream(mDownloadInfo.getApkSavedFile(), true)) {
                        int length = 0;
                        byte[] buffer = new byte[1024];
                        while ((length = inputStream.read(buffer)) != -1) {
                            if (mDownloadInfo.currentState == STATE_PAUSE_DOWNLOAD) {
                                isPaused = true;
                                break;
                            }
                            fileOutputStream.write(buffer, 0, length);
                            //=================当前状态：下载中=========================
                            mDownloadInfo.currentState = DownloadManager.STATE_DOWNLOADING;
//                            mDownloadInfo.range += length;
                            notifyDownloadInfoObservers(mDownloadInfo);
                            //========================================================
                            if (mDownloadInfo.getApkSavedFile().length() == mDownloadInfo.size) {
                                //下载完成,提前跳出while循环
                                break;
                            }
                        }
                    }
                    if (!isPaused) {
                        //=================当前状态：下载完成=========================
                        mDownloadInfo.currentState = DownloadManager.STATE_DOWNLOADED;
                        notifyDownloadInfoObservers(mDownloadInfo);
                        //========================================================
                    }
                } else {
                    //=================当前状态：下载失败=========================
                    mDownloadInfo.currentState = DownloadManager.STATE_DOWNLOAD_FAILED;
                    notifyDownloadInfoObservers(mDownloadInfo);
                    //========================================================
                }
            } catch (IOException e) {
                e.printStackTrace();
                //=================当前状态：下载失败=========================
                mDownloadInfo.currentState = DownloadManager.STATE_DOWNLOAD_FAILED;
                notifyDownloadInfoObservers(mDownloadInfo);
                //========================================================
            } finally {
                downLoadInfosCachedMap.put(mDownloadInfo.packageName, mDownloadInfo);
            }
        }
    }

}






















