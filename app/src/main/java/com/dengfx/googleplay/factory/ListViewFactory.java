package com.dengfx.googleplay.factory;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

import com.dengfx.googleplay.utils.UIUtils;

/**
 * Created by 邓FX on 2016/10/17.
 */

public class ListViewFactory {
    public static ListView createListView() {
        ListView listView = new ListView(UIUtils.getContext());
        listView.setDividerHeight(0);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setFastScrollEnabled(true);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        return listView;
    }
}
