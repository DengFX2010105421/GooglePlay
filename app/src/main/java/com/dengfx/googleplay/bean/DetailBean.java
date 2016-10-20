package com.dengfx.googleplay.bean;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 邓FX on 2016/10/20.
 */

public class DetailBean implements Serializable {

    public long id;
    public String name;
    public String packageName;
    public String iconUrl;
    public float stars;
    public String downloadNum;
    public String version;
    public String date;
    public long size;
    public String downloadUrl;
    public String des;
    public String author;
    public List<String> screen;
    public List<SafeBean> safe;

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

    @Override
    public String toString() {
        return "DetailBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", stars=" + stars +
                ", downloadNum='" + downloadNum + '\'' +
                ", version='" + version + '\'' +
                ", date='" + date + '\'' +
                ", size=" + size +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", des='" + des + '\'' +
                ", author='" + author + '\'' +
                ", screen=" + screen +
                ", safe=" + safe +
                '}';
    }

    public static class SafeBean {
        public String safeUrl;
        public String safeDesUrl;
        public String safeDes;
        public int safeDesColor;

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

        @Override
        public String toString() {
            return "SafeBean{" +
                    "safeUrl='" + safeUrl + '\'' +
                    ", safeDesUrl='" + safeDesUrl + '\'' +
                    ", safeDes='" + safeDes + '\'' +
                    ", safeDesColor=" + safeDesColor +
                    '}';
        }
    }
}
