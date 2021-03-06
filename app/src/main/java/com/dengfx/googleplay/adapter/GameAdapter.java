package com.dengfx.googleplay.adapter;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.dengfx.googleplay.base.SuperBaseAdapter;
import com.dengfx.googleplay.bean.ItemBean;
import com.dengfx.googleplay.base.BaseHolder;
import com.dengfx.googleplay.holder.ItemHolder;
import com.dengfx.googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by 邓FX on 2016/10/18.
 */
public class GameAdapter extends SuperBaseAdapter<ItemBean> {

    public GameAdapter(List<ItemBean> dataSet, AbsListView absListView) {
        super(dataSet, absListView);
    }

    @Override
    public BaseHolder getSpecialHolder() {
        return new ItemHolder();
    }

    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<ItemBean> dataSet = mDataSet;
        Toast.makeText(UIUtils.getContext(), dataSet.get(position).name, Toast.LENGTH_SHORT).show();
        super.onNormalItemClick(parent, view, position, id);
    }

    @Override
    public boolean canLoadMore() {
        return true;
    }
}
