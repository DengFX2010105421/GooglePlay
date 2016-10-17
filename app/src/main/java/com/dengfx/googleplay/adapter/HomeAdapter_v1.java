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
//        ViewHolder viewHolder;
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            convertView = viewHolder.mItemView;
//        }
//        viewHolder = (ViewHolder) convertView.getTag();
//        viewHolder.setData((String) getItem(position));
//        return convertView;
//==========================================================
        ItemHolder itemHolder;
        if (convertView == null) {
            itemHolder = new ItemHolder();
            convertView = itemHolder.mItemView;
        }
        itemHolder = (ItemHolder) convertView.getTag();
        itemHolder.setData(getItem(position));
        return convertView;
    }

/*    private static class ViewHolder {
        private View mItemView;
        private TextView mTextView1;
        private TextView mTextView2;
        private String mData;

        ViewHolder() {
            mItemView = View.inflate(UIUtils.getContext(), R.layout.item_temp, null);
            mTextView1 = (TextView) mItemView.findViewById(R.id.tmp_tv_1);
            mTextView2 = (TextView) mItemView.findViewById(R.id.tmp_tv_2);
            mItemView.setTag(this);
        }

        void setData(String data) {
            mData = data;
            if (mTextView1 != null)
                mTextView1.setText("===头" + data + "===");
            if (mTextView2 != null)
                mTextView2.setText("===尾" + data + "===");
        }

        public String getData() {
            return mData;
        }
    }*/
}
