package com.dengfx.googleplay.adapter;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.dengfx.googleplay.bean.SubjectBean;
import com.dengfx.googleplay.holder.BaseHolder;
import com.dengfx.googleplay.holder.SubjectHolder;
import com.dengfx.googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by 邓FX on 2016/10/18.
 */

public class SubjectAdapter extends SuperBaseAdapter<SubjectBean> {
    public SubjectAdapter(List<SubjectBean> dataSet, AbsListView absListView) {
        super(dataSet, absListView);
    }

    @Override
    public BaseHolder getSpecialHolder() {
        return new SubjectHolder();
    }

    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<SubjectBean> dataSet = mDataSet;
        Toast.makeText(UIUtils.getContext(), dataSet.get(position).des, Toast.LENGTH_SHORT).show();
        super.onNormalItemClick(parent, view, position, id);
    }

    @Override
    public boolean canLoadMore() {
        return true;
    }
}
