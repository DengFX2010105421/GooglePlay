package com.dengfx.googleplay.holder;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dengfx.googleplay.R;
import com.dengfx.googleplay.base.BaseHolder;
import com.dengfx.googleplay.bean.DetailBean;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.utils.UIUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 邓FX on 2016/10/20.
 */
public class DetailSafeHolder extends BaseHolder<DetailBean> {
    protected ImageView appDetailSafeIvArrow;
    protected LinearLayout appDetailSafePicContainer;
    protected LinearLayout appDetailSafeDesContainer;

    private boolean isOpenedState = true;

    @Override
    public void setData2HolderView(DetailBean detailBean) {
        List<DetailBean.SafeBean> safeBeans = detailBean.safe;
        for (int i = 0; i < safeBeans.size(); i++) {
            DetailBean.SafeBean safeBean = safeBeans.get(i);
            String safeDes = safeBean.safeDes;
            int safeDesColor = safeBean.safeDesColor;
            String safeDesUrl = safeBean.safeDesUrl;
            String safeUrl = safeBean.safeUrl;

            ImageView imageView = new ImageView(UIUtils.getContext());
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + safeUrl).into(imageView);
            appDetailSafePicContainer.addView(imageView);

            LinearLayout line = new LinearLayout(UIUtils.getContext());

            TextView tvDesNote = new TextView(UIUtils.getContext());
            tvDesNote.setText(safeDes);
            tvDesNote.setSingleLine();
            tvDesNote.setTextColor(UIUtils.getColor(safeDesColor == 0 ? R.color.app_detail_safe_normal : R.color.app_detail_safe_warning));

            ImageView ivDesIcon = new ImageView(UIUtils.getContext());
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + safeDesUrl).into(ivDesIcon);

            line.addView(ivDesIcon);
            line.addView(tvDesNote);

            appDetailSafeDesContainer.addView(line);
        }
        changeViewHeight(false);
    }

    @Override
    protected View initHolderView() {
        View detailSafeHolderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_safe, null);
        initView(detailSafeHolderView);
        detailSafeHolderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeViewHeight(true);
            }
        });
        return detailSafeHolderView;
    }

    private void changeViewHeight(boolean isOpenWithAnimation) {
        int start = 0, end = 0;
        if (isOpenedState) {
            start = appDetailSafeDesContainer.getMeasuredHeight();
        } else {
            appDetailSafeDesContainer.measure(0, 0);
            end = appDetailSafeDesContainer.getMeasuredHeight();
        }

        if (isOpenWithAnimation) {
            doAnimation(start, end, isOpenedState);
        } else {
            ViewGroup.LayoutParams layoutParams = appDetailSafeDesContainer.getLayoutParams();
            layoutParams.height = end;
            appDetailSafeDesContainer.setLayoutParams(layoutParams);
        }
        isOpenedState = !isOpenedState;
    }

    private void doAnimation(int start, int end, boolean isOpenedState) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams layoutParams = appDetailSafeDesContainer.getLayoutParams();
                layoutParams.height = (int) animation.getAnimatedValue();
                appDetailSafeDesContainer.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.start();
        ObjectAnimator objectAnimator = isOpenedState ?
                ObjectAnimator.ofFloat(appDetailSafeIvArrow, "rotation", 180, 0) :
                ObjectAnimator.ofFloat(appDetailSafeIvArrow, "rotation", 0, 180);
        objectAnimator.start();
    }

    private void initView(View rootView) {
        appDetailSafeIvArrow = (ImageView) rootView.findViewById(R.id.app_detail_safe_iv_arrow);
        appDetailSafePicContainer = (LinearLayout) rootView.findViewById(R.id.app_detail_safe_pic_container);
        appDetailSafeDesContainer = (LinearLayout) rootView.findViewById(R.id.app_detail_safe_des_container);
    }
}
