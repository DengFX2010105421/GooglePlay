package com.dengfx.googleplay.adapter;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.dengfx.googleplay.bean.ItemBean;
import com.dengfx.googleplay.holder.BaseHolder;
import com.dengfx.googleplay.holder.ItemHolder;
import com.dengfx.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by 邓FX on 2016/10/13.
 */

public class HomeAdapter extends SuperBaseAdapter<ItemBean> {

    public HomeAdapter(ArrayList<ItemBean> dataSet, AbsListView absListView) {
        super(dataSet, absListView);
    }

    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(UIUtils.getContext(),"条目被点击了",Toast.LENGTH_SHORT).show();
        super.onNormalItemClick(parent, view, position, id);
    }

    @Override
    public BaseHolder getSpecialHolder() {
        return new ItemHolder();
    }


}

