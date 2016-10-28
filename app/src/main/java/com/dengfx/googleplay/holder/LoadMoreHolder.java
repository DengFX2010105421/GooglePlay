package com.dengfx.googleplay.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dengfx.googleplay.R;
import com.dengfx.googleplay.base.BaseHolder;
import com.dengfx.googleplay.utils.UIUtils;

public class LoadMoreHolder extends BaseHolder<Integer> {
    public static final int LOADMORE_LOADING = 0;
    public static final int LOADMORE_ERROR = 1;
    public static final int LOADMORE_NONE = 2;
    protected LinearLayout itemLoadmoreContainerLoading;
    protected TextView itemLoadmoreTvRetry;
    protected LinearLayout itemLoadmoreContainerRetry;

    @Override
    public void setData2HolderView(Integer currentState) {
        itemLoadmoreContainerLoading.setVisibility(View.GONE);
        itemLoadmoreContainerRetry.setVisibility(View.GONE);
        switch (currentState) {
            case LOADMORE_LOADING:
                itemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_ERROR:
                itemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_NONE:
                itemLoadmoreContainerLoading.setVisibility(View.GONE);
                itemLoadmoreContainerRetry.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected View initHolderView() {
        View loadMoreView = View.inflate(UIUtils.getContext(), R.layout.item_loadmore, null);
        initView(loadMoreView);
        return loadMoreView;
    }

    private void initView(View rootView) {
        itemLoadmoreContainerLoading = (LinearLayout) rootView.findViewById(R.id.item_loadmore_container_loading);
        itemLoadmoreTvRetry = (TextView) rootView.findViewById(R.id.item_loadmore_tv_retry);
        itemLoadmoreContainerRetry = (LinearLayout) rootView.findViewById(R.id.item_loadmore_container_retry);
    }
}
