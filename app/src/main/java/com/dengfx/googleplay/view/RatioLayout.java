package com.dengfx.googleplay.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.dengfx.googleplay.R;

/**
 * Created by 邓FX on 2016/10/18.
 */

public class RatioLayout extends FrameLayout {

    public static final int RELATIVE2WIDTH = 0;
    public static final int RELATIVE2HEIGHT = 1;

    private float ratio;

    private int mRelative;

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        ratio = typedArray.getFloat(R.styleable.RatioLayout_ratio, 1 / 2.43f);
        mRelative = typedArray.getInt(R.styleable.RatioLayout_relative, RELATIVE2WIDTH);
        typedArray.recycle();
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public void setRelative(int relative) {
        mRelative = relative;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int layoutWidth = MeasureSpec.getSize(widthMeasureSpec);
        int layoutHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            if (mRelative == RELATIVE2WIDTH) {
                layoutHeight = (int) (layoutWidth * ratio + 0.5f);
            } else if (mRelative == RELATIVE2HEIGHT) {
                layoutHeight = (int) (layoutWidth / ratio + 0.5f);
            }
            setMeasuredDimension(layoutWidth, layoutHeight);

            int childWidth = layoutWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = layoutHeight - getPaddingTop() - getPaddingBottom();

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

        } else if (heightMode == MeasureSpec.EXACTLY) {
            if (mRelative == RELATIVE2WIDTH) {
                layoutWidth = (int) (layoutHeight / ratio + 0.5f);
            } else if (mRelative == RELATIVE2HEIGHT) {
                layoutWidth = (int) (layoutHeight * ratio + 0.5f);
            }
            setMeasuredDimension(layoutWidth, layoutHeight);

            int childWidth = layoutWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = layoutHeight - getPaddingTop() - getPaddingBottom();

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
