package com.dengfx.googleplay.protocol;

import android.text.TextUtils;

import com.dengfx.googleplay.bean.CategoryBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 邓FX on 2016/10/18.
 */

public class CategoryProtocol extends BaseProtocol<List<CategoryBean>> {
    @Override
    public List<CategoryBean> parseJson2Obj(String json) {
        List<CategoryBean> categoryBeanList = new ArrayList<>();
        try {
            JSONArray rootArray = new JSONArray(json);
            for (int i = 0; i < rootArray.length(); i++) {
                JSONObject jsonObject = rootArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                if (!TextUtils.isEmpty(title)) {
                    categoryBeanList.add(new CategoryBean(title, true, "", "", "", "", "", ""));
                }
                JSONArray infos = jsonObject.getJSONArray("infos");
                for (int j = 0; j < infos.length(); j++) {
                    JSONObject infosObject = infos.getJSONObject(j);
                    categoryBeanList.add(new CategoryBean("", false,
                            infosObject.getString("name1"),
                            infosObject.getString("name2"),
                            infosObject.getString("name3"),
                            infosObject.getString("url1"),
                            infosObject.getString("url2"),
                            infosObject.getString("url3")));
                }
            }
//            LogUtils.s(categoryBeanList.toString());
            return categoryBeanList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
