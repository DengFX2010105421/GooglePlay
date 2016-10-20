package com.dengfx.googleplay.protocol;

import com.dengfx.googleplay.bean.DetailBean;

public class DetailProtocol extends BaseProtocol<DetailBean> {

    @Override
    public DetailBean parseJson2Obj(String json) {
        DetailBean detailBean = mGson.fromJson(json, DetailBean.class);
        return detailBean;
    }
}
