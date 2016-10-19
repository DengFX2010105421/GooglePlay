package com.dengfx.googleplay.adapter;

import android.widget.AbsListView;

import com.dengfx.googleplay.bean.CategoryBean;
import com.dengfx.googleplay.holder.BaseHolder;
import com.dengfx.googleplay.holder.CategoryBeanHolder;

import java.util.List;

/**
 * Created by 邓FX on 2016/10/19.
 */
public class CategoryAdapter extends SuperBaseAdapter<CategoryBean> {

    public CategoryAdapter(List<CategoryBean> dataSet, AbsListView absListView) {
        super(dataSet, absListView);
    }

    @Override
    public BaseHolder getSpecialHolder() {
        return new CategoryBeanHolder();
    }
}
