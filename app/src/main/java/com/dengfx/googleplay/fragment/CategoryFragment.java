package com.dengfx.googleplay.fragment;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.dengfx.googleplay.adapter.CategoryAdapter;
import com.dengfx.googleplay.base.LoadingPager;
import com.dengfx.googleplay.bean.CategoryBean;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.factory.ListViewFactory;
import com.dengfx.googleplay.protocol.CategoryProtocol;
import com.dengfx.googleplay.utils.HttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryFragment extends BaseFragment {
    private List<CategoryBean> mDataSet;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View initSuccessView() {
        ListView listView = ListViewFactory.createListView();
        listView.setAdapter(new CategoryAdapter(mDataSet, listView));
        return listView;
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        CategoryProtocol categoryProtocol = new CategoryProtocol();
        try {
            List<CategoryBean> categoryBeanList = categoryProtocol.loadData(getUrl(0));
            System.out.print(categoryBeanList);
            if (categoryBeanList != null && categoryBeanList.size() != 0) {
                mDataSet = categoryBeanList;
                return LoadingPager.LoadedResult.RESULT_SUCCESS;
            } else {
                return LoadingPager.LoadedResult.RESULT_EMPTY;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.RESULT_ERROR;
        }
    }

    @NonNull
    private String getUrl(int index) {
        Map<String, Object> params = new HashMap<>();
        params.put("index", index);
        return Constants.URLS.BASEURL + "category?" + HttpUtils.getUrlParamsByMap(params);
    }
}