package com.dengfx.googleplay.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 邓FX on 2016/10/20.
 */

public class DetailBean implements Serializable {

    @SerializedName("id")
    public int mId;
    @SerializedName("name")
    public String mName;
    @SerializedName("packageName")
    public String mPackageName;
    @SerializedName("iconUrl")
    public String mIconUrl;
    @SerializedName("stars")
    public int mStars;
    @SerializedName("downloadNum")
    public String mDownloadNum;
    @SerializedName("version")
    public String mVersion;
    @SerializedName("date")
    public String mDate;
    @SerializedName("size")
    public int mSize;
    @SerializedName("downloadUrl")
    public String mDownloadUrl;
    @SerializedName("des")
    public String mDes;
    @SerializedName("author")
    public String mAuthor;
    @SerializedName("screen")
    public List<String> mScreen;
    /**
     * safeUrl : app/com.itheima.www/safeIcon0.jpg
     * safeDesUrl : app/com.itheima.www/safeDesUrl0.jpg
     * safeDes : 已通过安智市场安全检测，请放心使用
     * safeDesColor : 0
     */

    @SerializedName("safe")
    public List<SafeBean> mSafe;

    public static DetailBean objectFromData(String str) {

        return new Gson().fromJson(str, DetailBean.class);
    }

    public static DetailBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), DetailBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static class SafeBean {
        @SerializedName("safeUrl")
        public String mSafeUrl;
        @SerializedName("safeDesUrl")
        public String mSafeDesUrl;
        @SerializedName("safeDes")
        public String mSafeDes;
        @SerializedName("safeDesColor")
        public int mSafeDesColor;

        public static SafeBean objectFromData(String str) {

            return new Gson().fromJson(str, SafeBean.class);
        }

        public static SafeBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), SafeBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public String getSafeUrl() {
            return mSafeUrl;
        }
    }
}
