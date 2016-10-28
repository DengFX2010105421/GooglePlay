package com.dengfx.googleplay.fragment;

import android.view.View;
import android.widget.ListView;

import com.dengfx.googleplay.adapter.CategoryAdapter;
import com.dengfx.googleplay.base.BaseFragment;
import com.dengfx.googleplay.base.LoadingPager;
import com.dengfx.googleplay.bean.CategoryBean;
import com.dengfx.googleplay.factory.ListViewFactory;
import com.dengfx.googleplay.protocol.CategoryProtocol;

import java.util.List;

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
            mDataSet = categoryProtocol.loadData(getUrl("category", 0));
            if (mDataSet != null && mDataSet.size() != 0) {
                return LoadingPager.LoadedResult.RESULT_SUCCESS;
            } else {
                return LoadingPager.LoadedResult.RESULT_EMPTY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.RESULT_ERROR;
        }
    }
}