package com.dengfx.googleplay.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.dengfx.googleplay.base.BaseFragment;
import com.dengfx.googleplay.base.LoadingPager;
import com.dengfx.googleplay.protocol.RecommendProtocol;
import com.dengfx.googleplay.utils.UIUtils;
import com.dengfx.googleplay.view.flyInOut.ShakeListener;
import com.dengfx.googleplay.view.flyInOut.StellarMap;

import java.util.List;
import java.util.Random;

public class RecommendFragment extends BaseFragment {
    private List<String> mDataSet;
    private ShakeListener mShakeListener;
    private StellarMap mStellarMap;
    private RecommendAdapter mAdapter;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    public View initSuccessView() {
        mStellarMap = new StellarMap(UIUtils.getContext());
        mAdapter = new RecommendAdapter();
        mStellarMap.setAdapter(mAdapter);
        mStellarMap.setGroup(0, true);//展示首页
        mStellarMap.setRegularity(15, 20);
        mShakeListener = new ShakeListener(UIUtils.getContext());
        mShakeListener.setOnShakeListener(new MyShakeListener());
        return mStellarMap;
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        RecommendProtocol recommendProtocol = new RecommendProtocol();
        try {
            mDataSet = recommendProtocol.loadData(getUrl("recommend", 0));
            if (mDataSet != null && mDataSet.size() != 0) {
                return LoadingPager.LoadedResult.RESULT_SUCCESS;
            } else {
                return LoadingPager.LoadedResult.RESULT_EMPTY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.RESULT_ERROR;
        }
    }

    @Override
    public void onResume() {
        if (mShakeListener != null) {
            mShakeListener = null;
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mShakeListener != null) {
            mShakeListener = null;
        }
        super.onPause();
    }

    private class MyShakeListener implements ShakeListener.OnShakeListener {

        @Override
        public void onShake() {
            int currentGroup = mStellarMap.getCurrentGroup();
            currentGroup = currentGroup == mAdapter.getGroupCount() - 1 ? 0 : currentGroup + 1;
            mStellarMap.setGroup(currentGroup, true);
        }
    }

    private class RecommendAdapter implements StellarMap.Adapter {
        public static final int PAGESIZE = 15;
        int size = mDataSet.size();

        @Override
        public int getGroupCount() {
            return size % PAGESIZE == 0 ? size % PAGESIZE : size % PAGESIZE + 1;
        }

        @Override
        public int getCount(int group) {
            return size % PAGESIZE == 0 ? PAGESIZE : (group == getGroupCount() - 1 ? size % PAGESIZE : PAGESIZE);
        }

        @Override
        public View getView(int group, int position, View convertView) {
            TextView textView = new TextView(UIUtils.getContext());
            textView.setText(mDataSet.get(group * PAGESIZE + position));
            Random random = new Random();
            textView.setTextSize(random.nextInt(12) + 5);
            textView.setTextColor(Color.argb(255, random.nextInt(170) + 30, random.nextInt(170) + 30, random.nextInt(170) + 30));
            return textView;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }
    }
}
