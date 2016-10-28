package com.dengfx.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.text.format.Formatter;


import com.dengfx.googleplay.R;
import com.dengfx.googleplay.base.BaseHolder;
import com.dengfx.googleplay.bean.DetailBean;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.utils.UIUtils;
import com.squareup.picasso.Picasso;


/**
 * Created by 邓FX on 2016/10/20.
 */
public class DetailInfoHolder extends BaseHolder<DetailBean> {
    private ImageView mAppDetailInfoIvIcon;
    private TextView mAppDetailInfoTvName;
    private RatingBar mAppDetailInfoRbStar;
    private TextView mAppDetailInfoTvDownloadnum;
    private TextView mAppDetailInfoTvVersion;
    private TextView mAppDetailInfoTvTime;
    private TextView mAppDetailInfoTvSize;

    @Override
    public void setData2HolderView(DetailBean detailBean) {
        mAppDetailInfoTvName.setText(detailBean.name);
        mAppDetailInfoTvTime.setText(UIUtils.getResources().getString(R.string.detail_date, detailBean.date));
        mAppDetailInfoTvDownloadnum.setText(UIUtils.getResources().getString(R.string.detail_downloadnum, detailBean.downloadNum));
        mAppDetailInfoTvSize.setText(UIUtils.getResources().getString(R.string.detail_size, Formatter.formatFileSize(UIUtils.getContext(), detailBean.size)));
        mAppDetailInfoTvVersion.setText(UIUtils.getResources().getString(R.string.detail_version, detailBean.version));
        mAppDetailInfoRbStar.setRating(detailBean.stars);
        Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + detailBean.iconUrl).into(mAppDetailInfoIvIcon);
    }

    @Override
    protected View initHolderView() {
        View detailInfoHolderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_info, null);
        initView(detailInfoHolderView);
        return detailInfoHolderView;
    }

    private void initView(View rootView) {
        mAppDetailInfoIvIcon = (ImageView) rootView.findViewById(R.id.app_detail_info_iv_icon);
        mAppDetailInfoTvName = (TextView) rootView.findViewById(R.id.app_detail_info_tv_name);
        mAppDetailInfoRbStar = (RatingBar) rootView.findViewById(R.id.app_detail_info_rb_star);
        mAppDetailInfoTvDownloadnum = (TextView) rootView.findViewById(R.id.app_detail_info_tv_downloadnum);
        mAppDetailInfoTvVersion = (TextView) rootView.findViewById(R.id.app_detail_info_tv_version);
        mAppDetailInfoTvTime = (TextView) rootView.findViewById(R.id.app_detail_info_tv_time);
        mAppDetailInfoTvSize = (TextView) rootView.findViewById(R.id.app_detail_info_tv_size);
    }
}
