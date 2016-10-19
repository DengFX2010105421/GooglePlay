package com.dengfx.googleplay.holder;

import android.view.View;

/**
 * Created by 邓FX on 2016/10/13.
 */

public abstract class BaseHolder<T> {
    public View mItemView;
    public T mData;

    public BaseHolder() {
        mItemView = initHolderView();
        mItemView.setTag(this);
    }

    public void setData(T data) {
        mData = data;
        setData2HolderView(mData);
    }

    public abstract void setData2HolderView(T data);

    protected abstract View initHolderView();
}
