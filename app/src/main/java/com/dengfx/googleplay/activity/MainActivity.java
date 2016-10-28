package com.dengfx.googleplay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.astuetz.PagerSlidingTabStripExtends;
import com.dengfx.googleplay.R;
import com.dengfx.googleplay.animation.DepthPageTransformer;
import com.dengfx.googleplay.animation.ZoomOutPageTransformer;
import com.dengfx.googleplay.factory.FragmentFactory;
import com.dengfx.googleplay.holder.LeftMenuHolder;
import com.dengfx.googleplay.utils.UIUtils;

public class MainActivity extends AppCompatActivity {

    protected PagerSlidingTabStripExtends mMainTabs;
    protected ViewPager mMainViewpager;
    protected DrawerLayout mMainDrawerLayout;
    protected FrameLayout mFlLeftMenu;
    private ActionBarDrawerToggle mToggle;
    private String[] mMainTitles;

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
        initActionBar();
        initActionBarDrawerToggle();
        initData();
        initListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mMainTabs = (PagerSlidingTabStripExtends) findViewById(R.id.main_tabs);
        mMainDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawerLayout);
        mFlLeftMenu = (FrameLayout) findViewById(R.id.flLeftMenu);
        mMainViewpager = (ViewPager) findViewById(R.id.main_viewpager);

        mMainViewpager.setPageTransformer(true, new DepthPageTransformer());
        mMainViewpager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    private void initActionBar() {
        ActionBar supportActionBar = getSupportActionBar();//v4包中

        supportActionBar.setTitle("GooglePlay");

        supportActionBar.setIcon(R.drawable.ic_launcher);
        supportActionBar.setLogo(R.mipmap.ic_action_call);

        //显示logo/icon(图标)
        supportActionBar.setDisplayShowHomeEnabled(false);//默认是false,默认是隐藏图标
        //修改icon和logo显示的优先级
        supportActionBar.setDisplayUseLogoEnabled(true);//默认是false,默认是没用logo,用的icon

        //显示回退部分
        supportActionBar.setDisplayHomeAsUpEnabled(true);//默认是false,默认隐藏了回退部分
    }

    private void initActionBarDrawerToggle() {
        mToggle = new ActionBarDrawerToggle(this, mMainDrawerLayout, R.string.open, R.string.close);
        mToggle.syncState();
        mMainDrawerLayout.setDrawerListener(mToggle);
    }

    private void initData() {
        //填充左菜单具体内容
        LeftMenuHolder leftMenuHolder = new LeftMenuHolder();
        mFlLeftMenu.addView(leftMenuHolder.mItemView);

        mMainTitles = UIUtils.getStrings(R.array.main_titles);
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
//        MainFragmentStatePagerAdapter adapter = new MainFragmentStatePagerAdapter(getSupportFragmentManager());
        mMainViewpager.setAdapter(adapter);
        mMainTabs.setViewPager(mMainViewpager);
    }

    private void initListener() {
        final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                FragmentFactory.mCachedFragments.get(position).getLoadingPager().triggerLoadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        mMainTabs.setOnPageChangeListener(onPageChangeListener);

        mMainViewpager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onPageChangeListener.onPageSelected(0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mMainViewpager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.createFragment(position);
        }

        @Override
        public int getCount() {
            return mMainTitles == null ? 0 : mMainTitles.length;
        }

        /**
         * 必须覆写一个方法:getPageTitle
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mMainTitles[position];
        }
    }

    class MainFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        public MainFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {//指定Position所对应的页面的Fragment内容
            return FragmentFactory.createFragment(position);
        }

        @Override
        public int getCount() {//决定ViewPager页数的总和
            return mMainTitles == null ? 0 : mMainTitles.length;
        }

        /**
         * 必须覆写一个方法:getPageTitle
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mMainTitles[position];
        }
    }
}
