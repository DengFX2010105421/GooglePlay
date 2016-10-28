package com.dengfx.googleplay.holder;

import android.text.TextUtils;
import android.view.View;

import com.dengfx.googleplay.MyApplication;
import com.dengfx.googleplay.R;
import com.dengfx.googleplay.base.BaseHolder;
import com.dengfx.googleplay.bean.DetailBean;
import com.dengfx.googleplay.download.DownloadInfo;
import com.dengfx.googleplay.download.DownloadManager;
import com.dengfx.googleplay.utils.CommonUtils;
import com.dengfx.googleplay.utils.UIUtils;
import com.dengfx.googleplay.view.ProgressButton;

/**
 * Created by 邓FX on 2016/10/20.
 */
public class DetailDownloadHolder extends BaseHolder<DetailBean> implements DownloadManager.DownloadInfoObserver {
    private ProgressButton mAppDetailDownloadBtn;

    @Override
    public void setData2HolderView(DetailBean data) {
        DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(data);

        refreshProgressButton(downloadInfo);

    }

    @Override
    protected View initHolderView() {
        View detailDownloadHolderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_download, null);
        initView(detailDownloadHolderView);
        return detailDownloadHolderView;
    }

    private void initView(View rootView) {
//        Button appDetailDownloadBtnFavo = (Button) rootView.findViewById(R.id.app_detail_download_btn_favo);
//        Button appDetailDownloadBtnShare = (Button) rootView.findViewById(R.id.app_detail_download_btn_share);
        mAppDetailDownloadBtn = (ProgressButton) rootView.findViewById(R.id.app_detail_download_btn_download);
        mAppDetailDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(mData);
                switch (downloadInfo.currentState) {
                    case DownloadManager.STATE_UNDOWNLOAD:
                        doDownload(downloadInfo);
                        break;

                    case DownloadManager.STATE_DOWNLOADING:
                        doPause(downloadInfo);
                        break;

                    case DownloadManager.STATE_PAUSE_DOWNLOAD:
                        doDownload(downloadInfo);
                        break;

                    case DownloadManager.STATE_WAITING_DOWNLOAD:
                        doCancle(downloadInfo);
                        break;

                    case DownloadManager.STATE_DOWNLOAD_FAILED:
                        doDownload(downloadInfo);
                        break;

                    case DownloadManager.STATE_DOWNLOADED:
                        doInstall(downloadInfo);
                        break;

                    case DownloadManager.STATE_INSTALLED:
                        doOpen(downloadInfo);
                        break;
                }
            }
        });
    }

    private void doOpen(DownloadInfo downloadInfo) {
        CommonUtils.openApp(UIUtils.getContext(), downloadInfo.packageName);
    }

    private void doInstall(DownloadInfo downloadInfo) {
        CommonUtils.installApp(UIUtils.getContext(), downloadInfo.getApkSavedFile());
    }

    private void doCancle(DownloadInfo downloadInfo) {
        DownloadManager.getInstance().cancel(downloadInfo);
    }

    private void doPause(DownloadInfo downloadInfo) {
        DownloadManager.getInstance().pause(downloadInfo);
    }

    private void doDownload(DownloadInfo downloadInfo) {
        DownloadManager.getInstance().download(downloadInfo);
    }

    @Override
    public void onDownloadInfoChanged(DownloadInfo downloadInfo) {
        if (!TextUtils.equals(mData.packageName, downloadInfo.packageName)) {
            return;
        }
        MyApplication.mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                refreshProgressButton(downloadInfo);
            }
        });
    }

    private void refreshProgressButton(DownloadInfo downloadInfo) {

        mAppDetailDownloadBtn.setBackgroundResource(R.drawable.selector_app_detail_bottom_normal);
        switch (downloadInfo.currentState) {
            case DownloadManager.STATE_UNDOWNLOAD:
                mAppDetailDownloadBtn.setText("下载");
                break;

            case DownloadManager.STATE_DOWNLOADING:
                mAppDetailDownloadBtn.setBackgroundResource(R.drawable.selector_app_detail_bottom_downloading);
                int progress = (int) (downloadInfo.getRange() * 1.0f / downloadInfo.size * 100 + .5f);
                mAppDetailDownloadBtn.setText(progress + "%");
                mAppDetailDownloadBtn.setMax(downloadInfo.size);
                mAppDetailDownloadBtn.setProgress(downloadInfo.getRange());
                break;

            case DownloadManager.STATE_PAUSE_DOWNLOAD:
                mAppDetailDownloadBtn.setText("继续下载");
                break;

            case DownloadManager.STATE_WAITING_DOWNLOAD:
                mAppDetailDownloadBtn.setText("等待中");
                break;

            case DownloadManager.STATE_DOWNLOAD_FAILED:
                mAppDetailDownloadBtn.setText("重试");
                break;

            case DownloadManager.STATE_DOWNLOADED:
                mAppDetailDownloadBtn.setEnableProgress(false);
                mAppDetailDownloadBtn.setText("安装");
                break;

            case DownloadManager.STATE_INSTALLED:
                mAppDetailDownloadBtn.setText("打开");
                break;
        }
    }
}
