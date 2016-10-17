package com.dengfx.googleplay.fragment;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.dengfx.googleplay.adapter.HomeAdapter;
import com.dengfx.googleplay.base.BaseFragment;
import com.dengfx.googleplay.base.LoadingPager;
import com.dengfx.googleplay.bean.HomeBean;
import com.dengfx.googleplay.bean.ItemBean;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.protocol.HomeProtocol;
import com.dengfx.googleplay.utils.HttpUtils;
import com.dengfx.googleplay.utils.UIUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends BaseFragment {

    private ArrayList<ItemBean> mDataSet;
    private HomeProtocol mHomeProtocol;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        mHomeProtocol = new HomeProtocol();
        try {
            HomeBean homeBean = mHomeProtocol.loadData(getUrl(0));
            ArrayList<ItemBean> list = homeBean.list;
            if (list != null && list.size() != 0) {
                mDataSet = list;
                return LoadingPager.LoadedResult.RESULT_SUCCESS;
            } else {
                return LoadingPager.LoadedResult.RESULT_EMPTY;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            return LoadingPager.LoadedResult.RESULT_ERROR;
        }
    }

    @NonNull
    private String getUrl(int index) {
        Map<String, Object> params = new HashMap<>();
        params.put("index", index);
        return Constants.URLS.BASEURL + "home?" + HttpUtils.getUrlParamsByMap(params);
    }

    @Override
    public View initSuccessView() {
        ListView listView = new ListView(UIUtils.getContext());
        listView.setFastScrollEnabled(true);
        listView.setAdapter(new HomeAdapter(mDataSet, listView) {

            @Override
            public boolean canLoadMore() {
                return true;
            }

            @Override
            public List onLoadMore() throws Exception {
                SystemClock.sleep(2000);
                HomeBean moreHomeBean = mHomeProtocol.loadData(getUrl(mDataSet.size()));
                if (moreHomeBean != null) {
                    return moreHomeBean.list;
                }
                return super.onLoadMore();
            }
        });
        return listView;
    }
}