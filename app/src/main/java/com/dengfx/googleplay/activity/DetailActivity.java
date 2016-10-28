package com.dengfx.googleplay.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.dengfx.googleplay.R;
import com.dengfx.googleplay.base.LoadingPager;
import com.dengfx.googleplay.bean.DetailBean;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.holder.DetailDesHolder;
import com.dengfx.googleplay.holder.DetailDownloadHolder;
import com.dengfx.googleplay.holder.DetailInfoHolder;
import com.dengfx.googleplay.holder.DetailPicHolder;
import com.dengfx.googleplay.holder.DetailSafeHolder;
import com.dengfx.googleplay.download.DownloadInfo;
import com.dengfx.googleplay.download.DownloadManager;
import com.dengfx.googleplay.protocol.DetailProtocol;
import com.dengfx.googleplay.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    protected FrameLayout mDetailFlDownload;
    protected FrameLayout mDetailFlInfo;
    protected FrameLayout mDetailFlSafe;
    protected FrameLayout mDetailFlPic;
    protected FrameLayout mDetailFlDes;

    private LoadingPager mLoadingPager;
    private String mPackageName;
    private DetailBean mDetailBean;
    private DetailDownloadHolder mDetailDownloadHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLoadingPager = new LoadingPager(this) {
            @Override
            protected LoadedResult initData() {
                return DetailActivity.this.initData();
            }

            @Override
            protected View initSuccessView() {
                return DetailActivity.this.initSuccessView();
            }
        };
        setContentView(mLoadingPager);
        init();
        initActionBar();
        triggerLoadData();
    }

    @Override
    protected void onResume() {
        //动态的添加观察者到观察者集合中
        if (mDetailDownloadHolder != null) {
            DownloadManager.getInstance().addDownloadInfoObserver(mDetailDownloadHolder);

            //手动发布最新的DownLoadInfo
            DownloadInfo downLoadInfo = DownloadManager.getInstance().getDownloadInfo(mDetailBean);
            DownloadManager.getInstance().notifyDownloadInfoObservers(downLoadInfo);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //动态从观察者集合中移除观察者
        if (mDetailDownloadHolder != null) {
            DownloadManager.getInstance().removeDownloadInfoObserver(mDetailDownloadHolder);
        }
    }

    private void triggerLoadData() {
        mLoadingPager.triggerLoadData();
    }

    private View initSuccessView() {
        View successView = View.inflate(this, R.layout.layout_detail, null);
        initView(successView);
        //-----------------------------------------------------
        DetailInfoHolder detailInfoHolder = new DetailInfoHolder();
        ViewCompat.animate(detailInfoHolder.mItemView).rotationX(360)
                .setInterpolator(new OvershootInterpolator(4))
                .setDuration(1000)
                .start();
        mDetailFlInfo.addView(detailInfoHolder.mItemView);
        detailInfoHolder.setData(mDetailBean);
        //------------------------------------------------------
        DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
        mDetailFlSafe.addView(detailSafeHolder.mItemView);
        detailSafeHolder.setData(mDetailBean);
        //-------------------------------------------------------
        DetailPicHolder detailPicHolder = new DetailPicHolder();
        mDetailFlPic.addView(detailPicHolder.mItemView);
        detailPicHolder.setData(mDetailBean);
//        //-----------------------------------------------------
        DetailDesHolder detailDesHolder = new DetailDesHolder();
        mDetailFlDes.addView(detailDesHolder.mItemView);
        detailDesHolder.setData(mDetailBean);
        //-------------------------------------------------------
        mDetailDownloadHolder = new DetailDownloadHolder();
        mDetailFlDownload.addView(mDetailDownloadHolder.mItemView);
        mDetailDownloadHolder.setData(mDetailBean);
        DownloadManager.getInstance().addDownloadInfoObserver(mDetailDownloadHolder);
        //-------------------------------------------------------
        return successView;
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("GooglePlay");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private LoadingPager.LoadedResult initData() {
        DetailProtocol detailProtocol = new DetailProtocol();
        try {
            mDetailBean = detailProtocol.loadData(getUrl(mPackageName));
            return mDetailBean != null ? LoadingPager.LoadedResult.RESULT_SUCCESS : LoadingPager.LoadedResult.RESULT_EMPTY;
        } catch (Exception e1) {
            e1.printStackTrace();
            return LoadingPager.LoadedResult.RESULT_ERROR;
        }
    }

    private void init() {
        mPackageName = getIntent().getStringExtra("packageName");
    }

    @NonNull
    private String getUrl(String packageName) {
        Map<String, Object> params = new HashMap<>();
        params.put("packageName", packageName);
        return Constants.URLS.BASEURL + "detail?" + HttpUtils.getUrlParamsByMap(params);
    }

    private void initView(View rootView) {
        mDetailFlInfo = (FrameLayout) rootView.findViewById(R.id.detail_fl_info);
        mDetailFlSafe = (FrameLayout) rootView.findViewById(R.id.detail_fl_safe);
        mDetailFlPic = (FrameLayout) rootView.findViewById(R.id.detail_fl_pic);
        mDetailFlDes = (FrameLayout) rootView.findViewById(R.id.detail_fl_des);
        mDetailFlDownload = (FrameLayout) rootView.findViewById(R.id.detail_fl_download);
    }
}
