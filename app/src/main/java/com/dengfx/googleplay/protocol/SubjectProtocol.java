package com.dengfx.googleplay.protocol;

import com.dengfx.googleplay.bean.SubjectBean;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by 邓FX on 2016/10/18.
 */

public class SubjectProtocol extends BaseProtocol<List<SubjectBean>> {
    @Override
    public List<SubjectBean> parseJson2Obj(String json) {
        return mGson.fromJson(json, new TypeToken<List<SubjectBean>>() {
        }.getType());
    }
}
