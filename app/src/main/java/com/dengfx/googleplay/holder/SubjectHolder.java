package com.dengfx.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengfx.googleplay.R;
import com.dengfx.googleplay.base.BaseHolder;
import com.dengfx.googleplay.bean.SubjectBean;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.utils.UIUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by 邓FX on 2016/10/18.
 */

public class SubjectHolder extends BaseHolder<SubjectBean> {
    protected ImageView itemSubjectIvIcon;
    protected TextView itemSubjectTvTitle;

    @Override
    public void setData2HolderView(SubjectBean data) {
        itemSubjectTvTitle.setText(data.des);
        Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + data.url).into(itemSubjectIvIcon);
    }

    @Override
    protected View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);
        initView(holderView);
        return holderView;
    }

    private void initView(View rootView) {
        itemSubjectIvIcon = (ImageView) rootView.findViewById(R.id.item_subject_iv_icon);
        itemSubjectTvTitle = (TextView) rootView.findViewById(R.id.item_subject_tv_title);
    }
}
