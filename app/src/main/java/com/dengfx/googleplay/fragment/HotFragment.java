package com.dengfx.googleplay.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.dengfx.googleplay.base.LoadingPager;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.protocol.HotProtocol;
import com.dengfx.googleplay.utils.HttpUtils;
import com.dengfx.googleplay.utils.UIUtils;
import com.dengfx.googleplay.view.FlowLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HotFragment extends BaseFragment {
    private List<String> mDataSet;

    public static HotFragment newInstance() {
        return new HotFragment();
    }

    @Override
    public View initSuccessView() {
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
        for (int i = 0; i < mDataSet.size(); i++) {
            String data = mDataSet.get(i);
            TextView tv = new TextView(UIUtils.getContext());
            tv.setText(data);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.WHITE);
            int _5dp = UIUtils.dip2px(10);
            tv.setPadding(_5dp, _5dp, _5dp, _5dp);

            Random random = new Random();
            GradientDrawable normalBG = new GradientDrawable();
            normalBG.setCornerRadius(_5dp * 2);
            normalBG.setColor(Color.argb(255, random.nextInt(170) + 30, random.nextInt(170) + 30, random.nextInt(170) + 30));

            GradientDrawable pressedBG = new GradientDrawable();
            pressedBG.setCornerRadius(_5dp * 2);
            pressedBG.setColor(Color.DKGRAY);

            StateListDrawable selectorBG = new StateListDrawable();
            selectorBG.addState(new int[]{android.R.attr.state_pressed}, pressedBG);
            selectorBG.addState(new int[]{-android.R.attr.state_pressed}, normalBG);
            tv.setBackground(selectorBG);

            tv.setClickable(true);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), data, Toast.LENGTH_SHORT).show();
                }
            });

            flowLayout.addView(tv);
        }

        scrollView.setPadding(5, 5, 5, 5);
        scrollView.addView(flowLayout);
        return scrollView;
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        HotProtocol hotProtocol = new HotProtocol();
        try {
            mDataSet = hotProtocol.loadData(getUrl(0));
            if (mDataSet != null && mDataSet.size() != 0) {
                return LoadingPager.LoadedResult.RESULT_SUCCESS;
            } else {
                return LoadingPager.LoadedResult.RESULT_EMPTY;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.RESULT_ERROR;
        }
    }

    @NonNull
    private String getUrl(int index) {
        Map<String, Object> params = new HashMap<>();
        params.put("index", index);
        return Constants.URLS.BASEURL + "hot?" + HttpUtils.getUrlParamsByMap(params);
    }
}
