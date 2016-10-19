package com.dengfx.googleplay.bean;

/**
 * Created by 邓FX on 2016/10/18.
 */

public class CategoryBean {

    public String title;
    public boolean isTitle;
    public String name1;
    public String name2;
    public String name3;
    public String url1;
    public String url2;
    public String url3;

    public CategoryBean(String title, boolean isTitle,
                        String name1, String name2, String name3,
                        String url1, String url2, String url3) {
        this.title = title;
        this.isTitle = isTitle;
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
        this.url1 = url1;
        this.url2 = url2;
        this.url3 = url3;
    }

    @Override
    public String toString() {
        return "CategoryBean{" +
                "title='" + title + '\'' +
                ", isTitle=" + isTitle +
                ", name1='" + name1 + '\'' +
                ", name2='" + name2 + '\'' +
                ", name3='" + name3 + '\'' +
                ", url1='" + url1 + '\'' +
                ", url2='" + url2 + '\'' +
                ", url3='" + url3 + '\'' +
                '}';
    }
}
