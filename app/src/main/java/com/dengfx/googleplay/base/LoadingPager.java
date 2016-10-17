package com.dengfx.googleplay.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.dengfx.googleplay.MyApplication;
import com.dengfx.googleplay.R;
import com.dengfx.googleplay.proxy.ThreadPoolProxyFactory;
import com.dengfx.googleplay.utils.UIUtils;

/**
 * Created by 邓FX on 2016/10/12.
 */


public abstract class LoadingPager extends FrameLayout {

    public static final int STATE_LOADING = 0;

    public static final int STATE_ERROR = 1;
    public static final int STATE_EMPTY = 2;
    public static final int STATE_SUCCESS = 3;

    public static int CURRENT_STATE = STATE_LOADING;

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;
    private LoadDataTask mLoadDataTask;

    public LoadingPager(Context context) {
        super(context);
        initCommonViews();
    }

    private void initCommonViews() {

        mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
        this.addView(mLoadingView);

        mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
        this.addView(mErrorView);

        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerLoadData();
            }
        });

        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
        this.addView(mEmptyView);
        refreshViewByState();
    }

    public void triggerLoadData() {
        if (CURRENT_STATE != STATE_SUCCESS && mLoadDataTask == null){
            CURRENT_STATE = STATE_LOADING;
            refreshViewByState();
            mLoadDataTask = new LoadDataTask();
            //new Thread(mLoadDataTask).start();
            ThreadPoolProxyFactory.getNormalThreadPoolProxy().submit(mLoadDataTask);
        }
    }

    private void refreshViewByState() {
        mLoadingView.setVisibility(CURRENT_STATE == STATE_LOADING ? VISIBLE : GONE);
        mErrorView.setVisibility(CURRENT_STATE == STATE_ERROR ? VISIBLE : GONE);
        mEmptyView.setVisibility(CURRENT_STATE == STATE_EMPTY ? VISIBLE : GONE);

        if (CURRENT_STATE == STATE_SUCCESS && mSuccessView == null) {
            mSuccessView = initSuccessView();
            this.addView(mSuccessView);
        }

        if (mSuccessView != null) {
            mSuccessView.setVisibility(CURRENT_STATE == STATE_SUCCESS ? VISIBLE : GONE);
        }
    }

    private class LoadDataTask implements Runnable {
        @Override
        public void run() {
            CURRENT_STATE = initData().getState();
            MyApplication.mMainThreadHandler.post(new Runnable() {
                @Override
                public void run() {refreshViewByState();}
            });
            mLoadDataTask = null;
        }
    }

    protected abstract LoadedResult initData();

    protected abstract View initSuccessView();

    public enum LoadedResult {
        RESULT_SUCCESS(STATE_SUCCESS), RESULT_ERROR(STATE_ERROR),RESULT_EMPTY(STATE_EMPTY);

        public int mState;

        LoadedResult(int state) {
            mState = state;
        }

        public int getState() {
            return mState;
        }
    }

}
