package com.dengfx.googleplay.adapter;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 邓FX on 2016/10/13.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected List<T> mDataSet;

    public List<T> getDataSet() {
        return mDataSet;
    }

    public void setDataSet(List<T> dataSet) {
        mDataSet = dataSet;
    }

    public MyBaseAdapter(List<T> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public int getCount() {
        return mDataSet == null? 0 : mDataSet.size();
    }

    @Override
    public T getItem(int position) {
        return mDataSet == null ? null : mDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
