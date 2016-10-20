package com.dengfx.googleplay.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.dengfx.googleplay.activity.DetailActivity;
import com.dengfx.googleplay.bean.ItemBean;
import com.dengfx.googleplay.holder.BaseHolder;
import com.dengfx.googleplay.holder.ItemHolder;
import com.dengfx.googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by 邓FX on 2016/10/20.
 */

public class ItemAdapter extends SuperBaseAdapter<ItemBean> {
    public ItemAdapter(List<ItemBean> dataSet, AbsListView absListView) {
        super(dataSet, absListView);
    }

    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<ItemBean> dataSet = mDataSet;
//        Toast.makeText(UIUtils.getContext(), dataSet.get(position).name, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("ItenBean", dataSet.get(position));
        UIUtils.getContext().startActivity(intent);
        super.onNormalItemClick(parent, view, position, id);

    }

    @Override
    public BaseHolder getSpecialHolder() {
        return new ItemHolder();
    }

    @Override
    public boolean canLoadMore() {
        return true;
    }
}
