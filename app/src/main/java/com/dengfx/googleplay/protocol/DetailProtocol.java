package com.dengfx.googleplay.protocol;

import com.dengfx.googleplay.base.BaseProtocol;
import com.dengfx.googleplay.bean.DetailBean;

public class DetailProtocol extends BaseProtocol<DetailBean> {

    @Override
    public DetailBean parseJson2Obj(String json) {
        return mGson.fromJson(json, DetailBean.class);
    }
}
