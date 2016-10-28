package com.dengfx.googleplay.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.dengfx.googleplay.activity.DetailActivity;
import com.dengfx.googleplay.base.BaseHolder;
import com.dengfx.googleplay.base.SuperBaseAdapter;
import com.dengfx.googleplay.bean.ItemBean;
import com.dengfx.googleplay.holder.ItemHolder;
import com.dengfx.googleplay.download.DownloadManager;
import com.dengfx.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 邓FX on 2016/10/20.
 */

public class ItemAdapter extends SuperBaseAdapter<ItemBean> {

    //创建一个集合保存所有的ItemHolder(观察者)
    public List<ItemHolder> mItemHolders = new ArrayList<>();

    public ItemAdapter(List<ItemBean> dataSet, AbsListView absListView) {
        super(dataSet, absListView);
    }

    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<ItemBean> dataSet = mDataSet;
        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", dataSet.get(position).packageName);
        UIUtils.getContext().startActivity(intent);
        super.onNormalItemClick(parent, view, position, id);
    }

    @Override
    public BaseHolder getSpecialHolder() {
        ItemHolder itemHolder = new ItemHolder();
        //添加观察者到集合中
        mItemHolders.add(itemHolder);

        DownloadManager.getInstance().addDownloadInfoObserver(itemHolder);
        return itemHolder;
    }

    @Override
    public boolean canLoadMore() {
        return true;
    }
}
