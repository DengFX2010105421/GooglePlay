package com.dengfx.googleplay.fragment;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.dengfx.googleplay.base.BaseFragment;
import com.dengfx.googleplay.base.LoadingPager;
import com.dengfx.googleplay.utils.UIUtils;

import java.util.Random;

public class CategoryFragment extends BaseFragment {

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View initSuccessView() {
        TextView successView = new TextView(UIUtils.getContext());
        successView.setGravity(Gravity.CENTER);
        successView.setText(this.getClass().getSimpleName());
        successView.setTextColor(Color.RED);
        return successView;
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        SystemClock.sleep(2000);
        return loadedResults[new Random().nextInt(3)];
    }
}