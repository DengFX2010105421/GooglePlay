package com.dengfx.googleplay.bean;


import java.io.Serializable;
import java.util.List;

public class DetailBean extends ItemBean implements Serializable {

    public String downloadNum;
    public String version;
    public String date;
    public String author;
    public List<String> screen;
    public List<SafeBean> safe;

//    public DetailBean(JSONObject jsonObject){
//        super(jsonObject);
////        this.id = jsonObject.getLong("id");
////        this.name = jsonObject.getString("name");
////        this.packageName = jsonObject.getString("packageName");
////        this.iconUrl = jsonObject.getString("iconUrl");
////        this.stars = (float) jsonObject.getDouble("stars");
////        this.size = jsonObject.getLong("size");
////        this.downloadUrl = jsonObject.getString("downloadUrl");
////        this.des = jsonObject.getString("des");
//        try {
//            this.downloadNum = jsonObject.getString("downloadNum");
//            this.version = jsonObject.getString("version");
//            this.date = jsonObject.getString("date");
//            this.author = jsonObject.getString("author");
//
//            this.screen = new ArrayList<>();
//            JSONArray screenJSONArray = jsonObject.getJSONArray("screen");
//            for (int i = 0; i < screenJSONArray.length(); i++) {
//                String s = (String) screenJSONArray.get(i);
//                screen.add(s);
//            }
//
//            this.safe = new ArrayList<>();
//            JSONArray safeJSONArray = jsonObject.getJSONArray("safe");
//            for (int i = 0; i < safeJSONArray.length(); i++) {
//                JSONObject safeBeanObject = safeJSONArray.getJSONObject(i);
//                safe.add(new SafeBean(safeBeanObject));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//    }

    @Override
    public String toString() {
        return "DetailBean{" +
                "===packageName===" + packageName +
                "downloadNum='" + downloadNum + '\'' +
                ", version='" + version + '\'' +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", screen=" + screen +
                ", safe=" + safe +
                '}';
    }

    public class SafeBean {
        public String safeUrl;
        public String safeDesUrl;
        public String safeDes;
        public int safeDesColor;

//        public SafeBean(JSONObject jsonObject) throws JSONException {
//            this.safeUrl = jsonObject.getString("safeUrl");
//            this.safeDesUrl = jsonObject.getString("safeDesUrl");
//            this.safeDes = jsonObject.getString("safeDes");
//            this.safeDesColor = jsonObject.getInt("safeDesColor");
//        }

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
