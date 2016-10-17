package com.dengfx.googleplay.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.dengfx.googleplay.MyApplication;
import com.dengfx.googleplay.holder.BaseHolder;
import com.dengfx.googleplay.holder.LoadMoreHolder;
import com.dengfx.googleplay.proxy.ThreadPoolProxyFactory;
import com.dengfx.googleplay.utils.LogUtils;

import java.util.List;

import static com.dengfx.googleplay.holder.LoadMoreHolder.LOADMORE_ERROR;
import static com.dengfx.googleplay.holder.LoadMoreHolder.LOADMORE_LOADING;
import static com.dengfx.googleplay.holder.LoadMoreHolder.LOADMORE_NONE;

/**
 * Created by 邓FX on 2016/10/13.
 */

public abstract class SuperBaseAdapter<T> extends MyBaseAdapter implements AdapterView.OnItemClickListener {

    private static final int VIEWTYPE_LOADMORE = 1;
    private static final int VIEWTYPE_NORMAL = 0;
    private static final int PAGE_SIZE = 20;
    private LoadMoreHolder mLoadMoreHolder;
    private LoadMoreData mLoadMoreDataTask;

    private AbsListView mAbsListView;
    private int mState;

    public SuperBaseAdapter(List<T> dataSet, AbsListView absListView) {
        super(dataSet);
        mAbsListView = absListView;
        mAbsListView.setOnItemClickListener(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder baseHolder = null;
        int currentItemViewType = getItemViewType(position);
        if (convertView == null) {
            switch (currentItemViewType) {
                case VIEWTYPE_LOADMORE:
                    baseHolder = getLoadMoreHolder();
                    break;

                case VIEWTYPE_NORMAL:
                    baseHolder = getSpecialHolder();

                    break;
            }
        } else {
            baseHolder = (BaseHolder) convertView.getTag();
        }
        convertView = baseHolder.mItemView;

        switch (currentItemViewType) {
            case VIEWTYPE_LOADMORE:
                if (canLoadMore()) {
                    mState = LOADMORE_LOADING;
                    trigger2LoadMoreData();
                } else {
                    mState = LOADMORE_NONE;
                }
                mLoadMoreHolder.setData(mState);
                break;

            case VIEWTYPE_NORMAL:
                baseHolder.setData(getItem(position));
                break;
        }
        return convertView;
    }

    private void trigger2LoadMoreData() {
        if (mLoadMoreDataTask == null) {
            mLoadMoreDataTask = new LoadMoreData();
            mLoadMoreHolder.setData(LOADMORE_LOADING);
            ThreadPoolProxyFactory.getNormalThreadPoolProxy().submit(mLoadMoreDataTask);
        }
    }

    public boolean canLoadMore() {
        return false;
    }

    private LoadMoreHolder getLoadMoreHolder() {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (getItemViewType(position)) {
            case VIEWTYPE_LOADMORE:
                if (mState == LOADMORE_ERROR) {
                    trigger2LoadMoreData();
                }
                break;

            case VIEWTYPE_NORMAL:
                onNormalItemClick(parent, view, position, id);
                break;
        }
    }

    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == getCount() - 1 ? VIEWTYPE_LOADMORE : VIEWTYPE_NORMAL;
    }

    public abstract BaseHolder getSpecialHolder();

    public List onLoadMore() throws Exception {
        return null;
    }

    private class LoadMoreData implements Runnable {

        private List mLoadMoreList;

        @Override
        public void run() {
            try {
                mLoadMoreList = onLoadMore();
                if (mLoadMoreList == null) {
                    mState = LoadMoreHolder.LOADMORE_ERROR;
                } else if (mLoadMoreList.size() == PAGE_SIZE) {
                    mState = LoadMoreHolder.LOADMORE_LOADING;
                } else {
                    mState = LoadMoreHolder.LOADMORE_NONE;
                }
            } catch (Exception e) {
                e.printStackTrace();
                mState = LoadMoreHolder.LOADMORE_ERROR;
            }
            MyApplication.mMainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mLoadMoreList != null) {
                        mDataSet.addAll(mLoadMoreList);
                        notifyDataSetChanged();
                    }
                    mLoadMoreHolder.setData(mState);
                }
            });
            mLoadMoreDataTask = null;
        }
    }
}
