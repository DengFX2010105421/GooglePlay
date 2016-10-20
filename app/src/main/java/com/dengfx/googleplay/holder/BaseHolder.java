package com.dengfx.googleplay.holder;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by 邓FX on 2016/10/13.
 */

public abstract class BaseHolder<T> {
    public View mItemView;
    public T mData;

    public BaseHolder() {
        mItemView = initHolderView();
        mItemView.setTag(this);
        mItemView.setScaleX(0.6f);
        mItemView.setScaleY(0.5f);
        ViewCompat.animate(mItemView).scaleX(1).scaleY(1).setDuration(400).setInterpolator(new OvershootInterpolator(4)).start();
    }

    public void setData(T data) {
        mData = data;
        setData2HolderView(mData);
    }

    public abstract void setData2HolderView(T data);

    protected abstract View initHolderView();
}
