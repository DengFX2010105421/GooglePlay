package com.dengfx.googleplay.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dengfx.googleplay.MyApplication;
import com.dengfx.googleplay.R;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.utils.UIUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 邓FX on 2016/10/18.
 */

public class HomePicturesHolder extends BaseHolder<List<String>> {

    protected ViewPager itemHomePicturePager;
    protected LinearLayout itemHomePictureContainerIndicator;

    private AutoScrollask mAutoScrollask;

    @Override
    public void setData2HolderView(final List<String> pictureUrls) {
        final int size = pictureUrls.size();
        itemHomePicturePager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return pictureUrls == null ? 0 : size * 1000 * 1000;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(UIUtils.getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                String pictureUrl = Constants.URLS.IMGBASEURL + pictureUrls.get(position % size);
                Picasso.with(UIUtils.getContext()).load(pictureUrl).into(imageView);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        itemHomePicturePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < size; i++) {
                    itemHomePictureContainerIndicator.getChildAt(i).setSelected(i == position % size);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (mAutoScrollask == null) {
                            mAutoScrollask = new AutoScrollask();
                        }
                        MyApplication.mMainThreadHandler.postDelayed(mAutoScrollask, 2000);
                        break;

                    case ViewPager.SCROLL_STATE_DRAGGING:
                        MyApplication.mMainThreadHandler.removeCallbacks(mAutoScrollask);
                        break;
                }
            }
        });

        itemHomePicturePager.setOnTouchListener(new View.OnTouchListener() {
            float downX, downY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        MyApplication.mMainThreadHandler.removeCallbacks(mAutoScrollask);
                        //=================================================
                        downX = event.getRawX();
                        downY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        MyApplication.mMainThreadHandler.removeCallbacks(mAutoScrollask);
                        //=================================================
                        float moveX = event.getRawX();
                        float moveY = event.getRawY();

                        float deltaX = moveX - downX;
                        float deltaY = moveY - downY;
                        itemHomePicturePager.getParent().requestDisallowInterceptTouchEvent(Math.abs(deltaX) > Math.abs(deltaY));
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mAutoScrollask == null) {
                            mAutoScrollask = new AutoScrollask();
                        }
                        MyApplication.mMainThreadHandler.post(mAutoScrollask);
                        break;
                }
                return false;
            }
        });

        initIndicator(size);

        itemHomePicturePager.setCurrentItem(size * 1000 * 500);

        mAutoScrollask = new AutoScrollask();
        MyApplication.mMainThreadHandler.postDelayed(mAutoScrollask, 2000);
    }

    private void initIndicator(int size) {
        int _6dp = UIUtils.dip2px(6);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(_6dp, _6dp);
        params.leftMargin = _6dp;
        params.bottomMargin = _6dp;
        for (int i = 0; i < size; i++) {
            ImageView indicator = new ImageView(UIUtils.getContext());
            indicator.setImageResource(R.drawable.selector_indicator);
            itemHomePictureContainerIndicator.addView(indicator, params);
        }
        itemHomePictureContainerIndicator.getChildAt(0).setSelected(true);
    }

    @Override
    protected View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_home_pictures, null);
        initView(holderView);
        return holderView;
    }

    private void initView(View rootView) {
        itemHomePicturePager = (ViewPager) rootView.findViewById(R.id.item_home_picture_pager);
        itemHomePictureContainerIndicator = (LinearLayout) rootView.findViewById(R.id.item_home_picture_container_indicator);
    }

    private class AutoScrollask implements Runnable {

        @Override
        public void run() {
            itemHomePicturePager.setCurrentItem(itemHomePicturePager.getCurrentItem() + 1);
        }
    }
}
