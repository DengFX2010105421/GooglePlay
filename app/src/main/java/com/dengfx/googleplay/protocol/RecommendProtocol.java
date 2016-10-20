package com.dengfx.googleplay.protocol;

import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by 邓FX on 2016/10/17.
 */

public class RecommendProtocol extends BaseProtocol<List<String>> {
    @Override
    public List<String> parseJson2Obj(String json) {
        return mGson.fromJson(json, new TypeToken<List<String>>() {
        }.getType());
    }
}
