package com.dengfx.googleplay.fragment;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.dengfx.googleplay.base.BaseFragment;
import com.dengfx.googleplay.base.LoadingPager;
import com.dengfx.googleplay.bean.CategoryBean;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.protocol.CategoryProtocol;
import com.dengfx.googleplay.utils.HttpUtils;
import com.dengfx.googleplay.utils.UIUtils;

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
        TextView successView = new TextView(UIUtils.getContext());
        successView.setGravity(Gravity.CENTER);
        successView.setText(this.getClass().getSimpleName());
        successView.setTextColor(Color.RED);
        return successView;
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        CategoryProtocol categoryProtocol = new CategoryProtocol();
        try {
            List<CategoryBean> categoryBeanList = categoryProtocol.loadData(getUrl(0));
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