package com.dengfx.googleplay.holder;

import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dengfx.googleplay.MyApplication;
import com.dengfx.googleplay.R;
import com.dengfx.googleplay.base.BaseHolder;
import com.dengfx.googleplay.bean.ItemBean;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.download.DownloadInfo;
import com.dengfx.googleplay.download.DownloadManager;
import com.dengfx.googleplay.utils.CommonUtils;
import com.dengfx.googleplay.utils.HttpUtils;
import com.dengfx.googleplay.utils.UIUtils;
import com.dengfx.googleplay.view.ProgressCircle;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ItemHolder extends BaseHolder<ItemBean> implements DownloadManager.DownloadInfoObserver {
    private ProgressCircle mProgressCircle;
    private ImageView itemAppinfoIvIcon;
    private TextView itemAppinfoTvTitle;
    private RatingBar itemAppinfoRbStars;
    private TextView itemAppinfoTvSize;
    private TextView itemAppinfoTvDes;

    @Override
    public void setData2HolderView(ItemBean itemBean) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", itemBean.iconUrl);
        String iconUrl = Constants.URLS.BASEURL + "image?" + HttpUtils.getUrlParamsByMap(params);
        Picasso picasso = Picasso.with(UIUtils.getContext());
        picasso.setIndicatorsEnabled(true);
        picasso.load(iconUrl).into(itemAppinfoIvIcon);
        itemAppinfoTvTitle.setText(itemBean.name);
        itemAppinfoRbStars.setRating(itemBean.stars);
        itemAppinfoTvSize.setText(Formatter.formatFileSize(UIUtils.getContext(), itemBean.size));
        itemAppinfoTvDes.setText(itemBean.des);

        mProgressCircle.setText("下载");
        mProgressCircle.setCircle(R.drawable.ic_download);
        mProgressCircle.setProgress(0);

        DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(itemBean);
        refreshProgressCircle(downloadInfo);
        mProgressCircle.setOnClickListener(new View.OnClickListener() {
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
    protected View initHolderView() {
        View itemView = View.inflate(UIUtils.getContext(), R.layout.item_home, null);
        initView(itemView);
        return itemView;
    }

    private void initView(View rootView) {
        itemAppinfoIvIcon = (ImageView) rootView.findViewById(R.id.item_appinfo_iv_icon);
        itemAppinfoTvTitle = (TextView) rootView.findViewById(R.id.item_appinfo_tv_title);
        itemAppinfoRbStars = (RatingBar) rootView.findViewById(R.id.item_appinfo_rb_stars);
        itemAppinfoTvSize = (TextView) rootView.findViewById(R.id.item_appinfo_tv_size);
        itemAppinfoTvDes = (TextView) rootView.findViewById(R.id.item_appinfo_tv_des);
        mProgressCircle = (ProgressCircle) rootView.findViewById(R.id.progressCircle);
    }

    @Override
    public void onDownloadInfoChanged(DownloadInfo downloadInfo) {
        if (!TextUtils.equals(mData.packageName, downloadInfo.packageName)) {
            return;
        }
        MyApplication.mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                refreshProgressCircle(downloadInfo);
            }
        });

    }

    private void refreshProgressCircle(DownloadInfo downloadInfo) {

//        mProgressCircle.setBackgroundResource(R.drawable.selector_app_detail_bottom_normal);
        switch (downloadInfo.currentState) {
            case DownloadManager.STATE_UNDOWNLOAD:
                mProgressCircle.setText("下载");
                mProgressCircle.setCircle(R.drawable.ic_download);
                break;

            case DownloadManager.STATE_DOWNLOADING:
                mProgressCircle.setEnableProgress(true);
                mProgressCircle.setCircle(R.drawable.ic_pause);
                int progress = (int) (downloadInfo.getRange() * 1.0f / downloadInfo.size * 100 + .5f);
                mProgressCircle.setText(progress + "%");
                mProgressCircle.setMax(downloadInfo.size);
                mProgressCircle.setProgress(downloadInfo.getRange());
                break;

            case DownloadManager.STATE_PAUSE_DOWNLOAD:
                mProgressCircle.setText("继续");
                mProgressCircle.setCircle(R.drawable.ic_resume);
                break;

            case DownloadManager.STATE_WAITING_DOWNLOAD:
                mProgressCircle.setText("等待");
                mProgressCircle.setCircle(R.drawable.ic_pause);
                break;

            case DownloadManager.STATE_DOWNLOAD_FAILED:
                mProgressCircle.setText("重试");
                mProgressCircle.setCircle(R.drawable.ic_redownload);
                break;

            case DownloadManager.STATE_DOWNLOADED:
                mProgressCircle.setEnableProgress(false);
                mProgressCircle.setText("安装");
                mProgressCircle.setCircle(R.drawable.ic_install);
                break;

            case DownloadManager.STATE_INSTALLED:
                mProgressCircle.setText("打开");
                mProgressCircle.setCircle(R.drawable.ic_install);
                break;
        }
    }
}
