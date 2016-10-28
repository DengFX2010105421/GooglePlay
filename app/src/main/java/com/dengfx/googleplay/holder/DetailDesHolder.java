package com.dengfx.googleplay.holder;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dengfx.googleplay.R;
import com.dengfx.googleplay.base.BaseHolder;
import com.dengfx.googleplay.bean.DetailBean;
import com.dengfx.googleplay.utils.UIUtils;

/**
 * Created by 邓FX on 2016/10/20.
 */
public class DetailDesHolder extends BaseHolder<DetailBean> {
    private TextView mAppDetailDesTvDes;
    private TextView mAppDetailDesTvAuthor;
    private ImageView mAppDetailDesIvArrow;

    private boolean isOpenedState = true;
    private int mAppDetailDesTvHeight;

    @Override
    public void setData2HolderView(DetailBean detailBean) {
        mAppDetailDesTvAuthor.setText(detailBean.author);
        mAppDetailDesTvDes.setText(detailBean.des);
        mAppDetailDesTvDes.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                changeViewHeight(false);
                mAppDetailDesTvDes.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void changeViewHeight(boolean isOpenWithAnimation) {
        if (mAppDetailDesTvHeight == 0) {
            mAppDetailDesTvHeight = mAppDetailDesTvDes.getMeasuredHeight();
        }
        int appDetailDesTv7LinesHeight = getLinesHeight(7, mData.des);
        int start = isOpenedState ? mAppDetailDesTvHeight : appDetailDesTv7LinesHeight;
        int end = !isOpenedState ? mAppDetailDesTvHeight : appDetailDesTv7LinesHeight;

        if (isOpenWithAnimation) {
            doAnimation(start, end, isOpenedState);
        } else {
            mAppDetailDesTvDes.setHeight(end);
        }
        isOpenedState = !isOpenedState;
    }

    private int getLinesHeight(int lines, String content) {
        TextView textView = new TextView(UIUtils.getContext());
        textView.setLines(lines);
        textView.setText(content);
        textView.measure(0, 0);
        return textView.getMeasuredHeight();
    }

    private void doAnimation(int start, int end, boolean isOpenedState) {
        ObjectAnimator arrowAnimator = isOpenedState ?
                ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 180, 0) :
                ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 0, 180);
        arrowAnimator.start();

        ObjectAnimator desTvAnimator = ObjectAnimator.ofInt(mAppDetailDesTvDes, "height", start, end);
        desTvAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewParent parent = mAppDetailDesTvDes.getParent();
                while (true) {
                    parent = parent.getParent();
                    if (parent == null) {
                        break;
                    }

                    if (parent instanceof ScrollView) {
                        ((ScrollView) parent).fullScroll(View.FOCUS_DOWN);
                        break;
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        desTvAnimator.start();
    }

    @Override
    protected View initHolderView() {
        View detailDesHolderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_des, null);
        initView(detailDesHolderView);
        detailDesHolderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeViewHeight(true);
            }
        });
        return detailDesHolderView;
    }

    private void initView(View rootView) {
        mAppDetailDesTvDes = (TextView) rootView.findViewById(R.id.app_detail_des_tv_des);
        mAppDetailDesTvAuthor = (TextView) rootView.findViewById(R.id.app_detail_des_tv_author);
        mAppDetailDesIvArrow = (ImageView) rootView.findViewById(R.id.app_detail_des_iv_arrow);
    }
}
