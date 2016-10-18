package com.dengfx.googleplay.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.dengfx.googleplay.bean.ItemBean;
import com.dengfx.googleplay.holder.ItemHolder;

import java.util.List;

/**
 * Created by 邓FX on 2016/10/13.
 */

public class HomeAdapter_v1 extends MyBaseAdapter<ItemBean> {

    public HomeAdapter_v1(List<ItemBean> dataSet) {
        super(dataSet);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        if (convertView == null) {
            itemHolder = new ItemHolder();
            convertView = itemHolder.mItemView;
        }
        itemHolder = (ItemHolder) convertView.getTag();
        itemHolder.setData(getItem(position));
        return convertView;
    }
}
