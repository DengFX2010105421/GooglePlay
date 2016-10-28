package com.dengfx.googleplay.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dengfx.googleplay.R;


/**
 * Created by 邓FX on 2016/10/21.
 */

public class ProgressCircle extends LinearLayout {

    private ImageView mCircle;
    private TextView mTextView;

    private boolean enableProgress = true;
    private long mMax = 100;
    private long mProgress;
    private Paint mPaint;


    public ProgressCircle(Context context) {
        this(context, null);
    }

    public ProgressCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(getContext(), R.layout.inflate_progressview, this);
        mCircle = (ImageView) view.findViewById(R.id.ivIcon);
        mTextView = (TextView) view.findViewById(R.id.tvNote);
    }

    public void setCircle(int resId) {
        mCircle.setImageResource(resId);
    }

    public void setText(String content) {
        mTextView.setText(content);
    }

    public void setEnableProgress(boolean enableProgress) {
        this.enableProgress = enableProgress;
    }

    public void setMax(long max) {
        mMax = max;
    }

    public void setProgress(long progress) {
        mProgress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (enableProgress) {
            if (mPaint == null) {
                mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                mPaint.setColor(Color.RED);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(3);
            }

            //画矩形
            int left = mCircle.getLeft();
            int right = mCircle.getRight();
            int top = mCircle.getTop();
            int bottom = mCircle.getBottom();
            RectF rectangle = new RectF(left, top, right, bottom);

            float startAngle = -90;
            //扫过的弧度
            float sweepAngle = mProgress * 1.0f / mMax * 360;
            canvas.drawArc(rectangle, startAngle, sweepAngle, false, mPaint);
        }
    }

}
