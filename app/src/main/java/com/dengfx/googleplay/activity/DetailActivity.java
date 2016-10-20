package com.dengfx.googleplay.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.dengfx.googleplay.R;
import com.dengfx.googleplay.base.LoadingPager;
import com.dengfx.googleplay.bean.DetailBean;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.holder.DetailInfoHolder;
import com.dengfx.googleplay.holder.DetailSafeHolder;
import com.dengfx.googleplay.protocol.DetailProtocol;
import com.dengfx.googleplay.utils.HttpUtils;
import com.dengfx.googleplay.utils.LogUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends Activity {

    protected FrameLayout mDetailFlDownload;
    protected FrameLayout mDetailFlInfo;
    protected FrameLayout mDetailFlSafe;
    protected FrameLayout mDetailFlPic;
    protected FrameLayout mDetailFlDes;

    private LoadingPager mLoadingPager;
    private String mPackageName;
    private DetailBean mDetailBean;

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
        triggerLoadData();
    }

    private void triggerLoadData() {
        mLoadingPager.triggerLoadData();
    }

    private View initSuccessView() {
        View successView = View.inflate(this, R.layout.layout_detail, null);
        initView(successView);
        //-----------------------------------------------------
        DetailInfoHolder detailInfoHolder = new DetailInfoHolder();
        mDetailFlInfo.addView(detailInfoHolder.mItemView);
        detailInfoHolder.setData(mDetailBean);
        //------------------------------------------------------
        DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
        mDetailFlSafe.addView(detailSafeHolder.mItemView);
        detailSafeHolder.setData(mDetailBean);
        //-------------------------------------------------------
//        DetailPicHolder detailPicHolder = new DetailPicHolder();
//        mDetailFlPic.addView(detailPicHolder.mItemView);
//        detailPicHolder.setData(mDetailBean);
//        //-------------------------------------------------------
//        DetailDesHolder detailDesHolder = new DetailDesHolder();
//        mDetailFlDes.addView(detailDesHolder.mItemView);
//        detailDesHolder.setData(mDetailBean);
        //-------------------------------------------------------
//        DetailDownloadHolder detailDownloadHolder = new DetailDownloadHolder();
//        mDetailFlDownload.addView(detailDownloadHolder.mItemView);
//        detailDownloadHolder.setData(mDetailBean);

        return successView;
    }

    private LoadingPager.LoadedResult initData() {
        DetailProtocol detailProtocol = new DetailProtocol();
        try {
            mDetailBean = detailProtocol.loadData(getUrl(mPackageName));
            return mDetailBean != null ? LoadingPager.LoadedResult.RESULT_SUCCESS : LoadingPager.LoadedResult.RESULT_EMPTY;
        } catch (IOException e1) {
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
        String url = Constants.URLS.BASEURL + "detail?" + HttpUtils.getUrlParamsByMap(params);
        LogUtils.e("===========" + url + "=============");
        return url;
    }

    private void initView(View rootView) {
        mDetailFlInfo = (FrameLayout) rootView.findViewById(R.id.detail_fl_info);
        mDetailFlSafe = (FrameLayout) rootView.findViewById(R.id.detail_fl_safe);
        mDetailFlPic = (FrameLayout) rootView.findViewById(R.id.detail_fl_pic);
        mDetailFlDes = (FrameLayout) rootView.findViewById(R.id.detail_fl_des);
        mDetailFlDownload = (FrameLayout) rootView.findViewById(R.id.detail_fl_download);
    }
}
