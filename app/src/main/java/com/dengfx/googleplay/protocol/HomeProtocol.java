package com.dengfx.googleplay.protocol;

import com.dengfx.googleplay.bean.HomeBean;

public class HomeProtocol extends BaseProtocol<HomeBean> {

    @Override
    public HomeBean parseJson2Obj(String json) {
        return mGson.fromJson(json, HomeBean.class);
    }
}
