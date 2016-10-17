package com.dengfx.googleplay.factory;

import android.support.v4.app.Fragment;

import com.dengfx.googleplay.base.BaseFragment;
import com.dengfx.googleplay.fragment.AppFragment;
import com.dengfx.googleplay.fragment.CategoryFragment;
import com.dengfx.googleplay.fragment.GameFragment;
import com.dengfx.googleplay.fragment.HomeFragment;
import com.dengfx.googleplay.fragment.HotFragment;
import com.dengfx.googleplay.fragment.RecommendFragment;
import com.dengfx.googleplay.fragment.SubjectFragment;

import java.util.HashMap;

public class FragmentFactory {

    private static final int FRAGMENT_HOME = 0;     //首页
    private static final int FRAGMENT_APP = 1;      //应用
    private static final int FRAGMENT_GAME = 2;     //游戏
    private static final int FRAGMENT_SUBJECT = 3;  //专题
    private static final int FRAGMENT_RECOMMEND = 4;//推荐
    private static final int FRAGMENT_CATEGORY = 5; //分类
    private static final int FRAGMENT_HOT = 6;      //排行

    public static HashMap<Integer,BaseFragment> mCachedFragments = new HashMap<>();

    public static Fragment createFragment(int position) {
        BaseFragment fragment = null;

        if (mCachedFragments.containsKey(position)){
            fragment = mCachedFragments.get(position);
        }

        switch (position) {
            case FRAGMENT_HOME:
                fragment = HomeFragment.newInstance();
                break;
            case FRAGMENT_APP:
                fragment = AppFragment.newInstance();
                break;
            case FRAGMENT_GAME:
                fragment = GameFragment.newInstance();
                break;
            case FRAGMENT_SUBJECT:
                fragment = SubjectFragment.newInstance();
                break;
            case FRAGMENT_RECOMMEND:
                fragment = RecommendFragment.newInstance();
                break;
            case FRAGMENT_CATEGORY:
                fragment = CategoryFragment.newInstance();
                break;
            case FRAGMENT_HOT:
                fragment = HotFragment.newInstance();
                break;
        }
        mCachedFragments.put(position,fragment);
        return fragment;
    }
}
