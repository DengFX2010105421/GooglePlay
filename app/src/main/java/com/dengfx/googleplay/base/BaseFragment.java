package com.dengfx.googleplay.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.utils.HttpUtils;
import com.dengfx.googleplay.utils.UIUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 邓FX on 2016/10/12.
 */

public abstract class BaseFragment extends Fragment {

    public LoadingPager.LoadedResult[] loadedResults = {LoadingPager.LoadedResult.RESULT_ERROR, LoadingPager.LoadedResult.RESULT_EMPTY, LoadingPager.LoadedResult.RESULT_SUCCESS};
    private LoadingPager mLoadingPager;

    public LoadingPager getLoadingPager() {
        return mLoadingPager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLoadingPager == null)
            mLoadingPager = new LoadingPager(UIUtils.getContext()) {
                @Override
                protected LoadedResult initData() {
                    return BaseFragment.this.initData();
                }

                @Override
                protected View initSuccessView() {
                    return BaseFragment.this.initSuccessView();
                }
            };
        //mLoadingPager.triggerLoadData();
        // 不在这里初始化数据，而在与ViewPager相关的mMainTag设置监听初始化

        //低版本兼容
        ViewParent parent = mLoadingPager.getParent();
        if (parent != null && parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(mLoadingPager);
        }

        return mLoadingPager;
    }

    @NonNull
    public String getUrl(String pagename, int index) {
        Map<String, Object> params = new HashMap<>();
        params.put("index", index);
        return Constants.URLS.BASEURL + pagename + "?" + HttpUtils.getUrlParamsByMap(params);
    }

    public abstract View initSuccessView();

    public abstract LoadingPager.LoadedResult initData();
}














































































