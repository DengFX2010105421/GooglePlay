package com.dengfx.googleplay.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dengfx.googleplay.utils.UIUtils;

/**
 * Created by 邓FX on 2016/10/12.
 */

public abstract class BaseFragment extends Fragment {

    public LoadingPager.LoadedResult[] loadedResults = {LoadingPager.LoadedResult.RESULT_ERROR, LoadingPager.LoadedResult.RESULT_EMPTY, LoadingPager.LoadedResult.RESULT_SUCCESS};

    public LoadingPager getLoadingPager() {
        return mLoadingPager;
    }

    private LoadingPager mLoadingPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

        return mLoadingPager;
    }

//    public LoadingPager.LoadedResult checkResult(Object resObj) {
//        if (resObj == null) {
//            return LoadingPager.LoadedResult.RESULT_EMPTY;
//        }
//
//        if (resObj instanceof List && ((List) resObj).size() == 0) {
//            return LoadingPager.LoadedResult.RESULT_EMPTY;
//        }
//
//        if (resObj instanceof Map && ((Map) resObj).size() == 0) {
//            return LoadingPager.LoadedResult.RESULT_EMPTY;
//        }
//        return LoadingPager.LoadedResult.RESULT_SUCCESS;
//    }

    public abstract View initSuccessView();

    public abstract LoadingPager.LoadedResult initData();


}














































































