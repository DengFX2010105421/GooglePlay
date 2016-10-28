package com.dengfx.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dengfx.googleplay.R;
import com.dengfx.googleplay.base.BaseHolder;
import com.dengfx.googleplay.bean.DetailBean;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.utils.UIUtils;
import com.dengfx.googleplay.view.RatioLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 邓FX on 2016/10/20.
 */
public class DetailPicHolder extends BaseHolder<DetailBean> {
    protected LinearLayout mAppDetailPicIvContainer;

    @Override
    public void setData2HolderView(DetailBean detailBean) {
        List<String> screen = detailBean.screen;
        for (int i = 0; i < screen.size(); i++) {

            RatioLayout ratioLayout = new RatioLayout(UIUtils.getContext());
            ratioLayout.setRelative(RatioLayout.RELATIVE2WIDTH);
            ratioLayout.setRatio(5 * 1.0f / 3);

            ImageView imageView = new ImageView(UIUtils.getContext());
//            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int width = UIUtils.getResources().getDisplayMetrics().widthPixels / 3 - UIUtils.dip2px(6);
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            if (i != 0) {
                params.leftMargin = UIUtils.dip2px(4);
            }
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + screen.get(i)).into(imageView);
            ratioLayout.addView(imageView);

            mAppDetailPicIvContainer.addView(ratioLayout, params);
        }

    }

    @Override
    protected View initHolderView() {
        View detailPicHolderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_pic, null);
        initView(detailPicHolderView);
        return detailPicHolderView;
    }

    private void initView(View rootView) {
        mAppDetailPicIvContainer = (LinearLayout) rootView.findViewById(R.id.app_detail_pic_iv_container);
    }
}
