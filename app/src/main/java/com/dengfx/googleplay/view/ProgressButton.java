package com.dengfx.googleplay.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.Button;


/**
 * Created by 邓FX on 2016/10/21.
 */

public class ProgressButton extends Button {

    private boolean enableProgress = true;
    private long mMax = 100;
    private long mProgress;
    private ColorDrawable mColorDrawable;


    public ProgressButton(Context context) {
        super(context);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setText("下载");
    }

    public void setEnableProgress(boolean enableProgress) {
        this.enableProgress = enableProgress;
    }

    public void setMax(long mMax) {
        this.mMax = mMax;
    }

    public void setProgress(long mProgress) {
        this.mProgress = mProgress;
        invalidate();
    }

    public void setmColorDrawable(ColorDrawable mColorDrawable) {
        this.mColorDrawable = mColorDrawable;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (enableProgress) {
            if (mColorDrawable == null) {
                mColorDrawable = new ColorDrawable(Color.RED);
            }
            int right = (int) (mProgress * 1.0f / mMax * getMeasuredWidth() + 0.5f);
            mColorDrawable.setBounds(0, 0, right, getBottom());
            mColorDrawable.draw(canvas);
        }
        super.onDraw(canvas);
    }
}
