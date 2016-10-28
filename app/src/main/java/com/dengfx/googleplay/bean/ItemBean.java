package com.dengfx.googleplay.bean;

import java.io.Serializable;

public class ItemBean implements Serializable {
    public static final long serialVersionUID = -5845410257288382236L;
    public long id;
    public String name;
    public String packageName;
    public String iconUrl;
    public float stars;
    public long size;
    public String downloadUrl;
    public String des;

//    public ItemBean(JSONObject jsonObject){
//        try {
//            this.id = jsonObject.getLong("id");
//            this.name = jsonObject.getString("name");
//            this.packageName = jsonObject.getString("packageName");
//            this.iconUrl = jsonObject.getString("iconUrl");
//            this.stars = (float) jsonObject.getDouble("stars");
//            this.size = jsonObject.getLong("size");
//            this.downloadUrl = jsonObject.getString("downloadUrl");
//            this.des = jsonObject.getString("des");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", stars=" + stars +
                ", size=" + size +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}
