package com.dengfx.googleplay.protocol;

import com.dengfx.googleplay.bean.ItemBean;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by 邓FX on 2016/10/17.
 */

public class GameProtocol extends BaseProtocol<List<ItemBean>> {
    @Override
    public List<ItemBean> parseJson2Obj(String json) {
        return mGson.fromJson(json, new TypeToken<List<ItemBean>>() {
        }.getType());
    }
}
