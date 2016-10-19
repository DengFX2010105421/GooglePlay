package com.dengfx.googleplay.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 邓FX on 2016/10/18.
 */

public class SubjectBean {

    /**
     * des : 一周新锐游戏精选
     * url : image/recommend_01.jpg
     */

    @SerializedName("des")
    public String des;
    @SerializedName("url")
    public String url;

    public static SubjectBean objectFromData(String str) {
        return new Gson().fromJson(str, SubjectBean.class);
    }

    public static SubjectBean objectFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), SubjectBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
