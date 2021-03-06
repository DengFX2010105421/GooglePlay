package com.dengfx.googleplay.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dengfx.googleplay.MyApplication;
import com.dengfx.googleplay.holder.LoadMoreHolder;
import com.dengfx.googleplay.proxy.ThreadPoolProxyFactory;

import java.util.List;

import static com.dengfx.googleplay.holder.LoadMoreHolder.LOADMORE_ERROR;
import static com.dengfx.googleplay.holder.LoadMoreHolder.LOADMORE_LOADING;
import static com.dengfx.googleplay.holder.LoadMoreHolder.LOADMORE_NONE;

/**
 * Created by 邓FX on 2016/10/13.
 */

public abstract class SuperBaseAdapter<T> extends MyBaseAdapter implements AdapterView.OnItemClickListener {

    public static final int VIEWTYPE_LOADMORE = 0;
    public static final int VIEWTYPE_NORMAL = 1;
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

        if (currentItemViewType == VIEWTYPE_LOADMORE) {
            if (canLoadMore()) {
                mState = LOADMORE_LOADING;
                trigger2LoadMoreData();
            } else {
                mState = LOADMORE_NONE;
            }
            mLoadMoreHolder.setData(mState);
        } else {
            Object data = mDataSet.get(position);
            baseHolder.setData(data);
        }
        return baseHolder.mItemView;
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
        //处理头布局
        if (mAbsListView instanceof ListView) {
            position -= ((ListView) mAbsListView).getHeaderViewsCount();
        }

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
        return position == getCount() - 1 ? VIEWTYPE_LOADMORE : /*VIEWTYPE_NORMAL*/ getNormalItemViewType(position);
    }

    public int getNormalItemViewType(int position) {
        return VIEWTYPE_NORMAL;
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
