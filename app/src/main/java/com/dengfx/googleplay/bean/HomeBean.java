package com.dengfx.googleplay.bean;

import java.util.ArrayList;

public class HomeBean implements java.io.Serializable {
    public static final long serialVersionUID = 6467266433487696898L;
    public ArrayList<ItemBean> list;
    public ArrayList<String> picture;

    @Override
    public String toString() {
        return "HomeBean{" +
                "list=" + list +
                ", picture=" + picture +
                '}';
    }
}
