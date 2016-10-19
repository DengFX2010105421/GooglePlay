package com.dengfx.googleplay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dengfx.googleplay.R;
import com.dengfx.googleplay.bean.ItemBean;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.utils.HttpUtils;
import com.dengfx.googleplay.utils.UIUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ItemHolder extends BaseHolder<ItemBean> {
    private ImageView itemAppinfoIvIcon;
    private TextView itemAppinfoTvTitle;
    private RatingBar itemAppinfoRbStars;
    private TextView itemAppinfoTvSize;
    private TextView itemAppinfoTvDes;

    @Override
    public void setData2HolderView(ItemBean data) {
        //http://localhost:8080/GooglePlayServer/image?name=app/com.itheima.www/icon.jpg
        Map<String, Object> params = new HashMap<>();
        params.put("name", data.iconUrl);
        String iconUrl = Constants.URLS.BASEURL + "image?" + HttpUtils.getUrlParamsByMap(params);
        Picasso picasso = Picasso.with(UIUtils.getContext());
        picasso.setIndicatorsEnabled(true);
        picasso.load(iconUrl).into(itemAppinfoIvIcon);
        itemAppinfoTvTitle.setText(data.name);
        itemAppinfoRbStars.setRating(data.stars);
        itemAppinfoTvSize.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        itemAppinfoTvDes.setText(data.des);
    }

    @Override
    protected View initHolderView() {
        View itemView = View.inflate(UIUtils.getContext(), R.layout.item_home, null);
        initView(itemView);
        return itemView;
    }

    private void initView(View rootView) {
        itemAppinfoIvIcon = (ImageView) rootView.findViewById(R.id.item_appinfo_iv_icon);
        itemAppinfoTvTitle = (TextView) rootView.findViewById(R.id.item_appinfo_tv_title);
        itemAppinfoRbStars = (RatingBar) rootView.findViewById(R.id.item_appinfo_rb_stars);
        itemAppinfoTvSize = (TextView) rootView.findViewById(R.id.item_appinfo_tv_size);
        itemAppinfoTvDes = (TextView) rootView.findViewById(R.id.item_appinfo_tv_des);
    }
}
