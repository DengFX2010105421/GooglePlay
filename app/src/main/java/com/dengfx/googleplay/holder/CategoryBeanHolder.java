package com.dengfx.googleplay.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dengfx.googleplay.R;
import com.dengfx.googleplay.bean.CategoryBean;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.utils.UIUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by 邓FX on 2016/10/19.
 */
public class CategoryBeanHolder extends BaseHolder<CategoryBean> {

    protected TextView tvTitle;
    protected ImageView ivPicture1;
    protected TextView tvName1;
    protected LinearLayout categoryItem1;
    protected ImageView ivPicture2;
    protected TextView tvName2;
    protected LinearLayout categoryItem2;
    protected ImageView ivPicture3;
    protected TextView tvName3;
    protected LinearLayout categoryItem3;
    private LinearLayout mLlIconcItem;

    @Override
    public void setData2HolderView(CategoryBean categoryBean) {
        setTitle(categoryBean.title, tvTitle, mLlIconcItem);
        setData2Icon(categoryBean.name1, categoryBean.url1, tvName1, ivPicture1);
        setData2Icon(categoryBean.name2, categoryBean.url2, tvName2, ivPicture2);
        setData2Icon(categoryBean.name3, categoryBean.url3, tvName3, ivPicture3);
    }

    private void setTitle(String title, TextView tvTitle, LinearLayout llIconcItem) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
            llIconcItem.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.GONE);
            llIconcItem.setVisibility(View.VISIBLE);
        }
    }

    private void setData2Icon(String name, String url, TextView tvName, ImageView ivPicture) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(url)) {
            tvName.setText(name);
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + url).into(ivPicture);
            ViewParent parent = tvName.getParent();
            ((ViewGroup) parent).setVisibility(View.VISIBLE);
        } else {
            ViewParent parent = tvName.getParent();
            ((ViewGroup) parent).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected View initHolderView() {
        View itemCategoryIcons = View.inflate(UIUtils.getContext(), R.layout.item_category, null);
        initView(itemCategoryIcons);
        return itemCategoryIcons;
    }

    private void initView(View rootView) {
        mLlIconcItem = (LinearLayout) rootView.findViewById(R.id.llIconcItem);
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        ivPicture1 = (ImageView) rootView.findViewById(R.id.iv_picture1);
        tvName1 = (TextView) rootView.findViewById(R.id.tv_name1);
        categoryItem1 = (LinearLayout) rootView.findViewById(R.id.category_item_1);
        ivPicture2 = (ImageView) rootView.findViewById(R.id.iv_picture2);
        tvName2 = (TextView) rootView.findViewById(R.id.tv_name2);
        categoryItem2 = (LinearLayout) rootView.findViewById(R.id.category_item_2);
        ivPicture3 = (ImageView) rootView.findViewById(R.id.iv_picture3);
        tvName3 = (TextView) rootView.findViewById(R.id.tv_name3);
        categoryItem3 = (LinearLayout) rootView.findViewById(R.id.category_item_3);
    }
}
