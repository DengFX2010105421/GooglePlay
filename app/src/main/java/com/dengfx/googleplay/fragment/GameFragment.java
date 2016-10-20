package com.dengfx.googleplay.fragment;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.dengfx.googleplay.adapter.ItemAdapter;
import com.dengfx.googleplay.base.LoadingPager;
import com.dengfx.googleplay.bean.ItemBean;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.factory.ListViewFactory;
import com.dengfx.googleplay.protocol.GameProtocol;
import com.dengfx.googleplay.utils.HttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameFragment extends BaseFragment {

    private List<ItemBean> mDataSet;
    private GameProtocol mGameProtocol;

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Override
    public View initSuccessView() {
        ListView listView = ListViewFactory.createListView();
//        listView.setAdapter(new GameAdapter(mDataSet, listView) {
//            @Override
//            public List onLoadMore() throws Exception {
//                SystemClock.sleep(2000);
//                return mGameProtocol.loadData(getUrl(mDataSet.size()));
//            }
//        });
        listView.setAdapter(new ItemAdapter(mDataSet, listView) {
            @Override
            public List onLoadMore() throws Exception {
                SystemClock.sleep(2000);
                return mGameProtocol.loadData(getUrl(mDataSet.size()));
            }
        });
        return listView;
    }

    @NonNull
    private String getUrl(int index) {
        Map<String, Object> params = new HashMap<>();
        params.put("index", index);
        return Constants.URLS.BASEURL + "game?" + HttpUtils.getUrlParamsByMap(params);
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        mGameProtocol = new GameProtocol();
        try {
            List<ItemBean> itemBeanList = mGameProtocol.loadData(getUrl(0));
            if (itemBeanList != null && itemBeanList.size() != 0) {
                mDataSet = itemBeanList;
                return LoadingPager.LoadedResult.RESULT_SUCCESS;
            } else {
                return LoadingPager.LoadedResult.RESULT_EMPTY;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.RESULT_ERROR;
        }
    }
}
