package com.dengfx.googleplay.fragment;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.dengfx.googleplay.adapter.SubjectAdapter;
import com.dengfx.googleplay.base.BaseFragment;
import com.dengfx.googleplay.base.LoadingPager;
import com.dengfx.googleplay.bean.SubjectBean;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.factory.ListViewFactory;
import com.dengfx.googleplay.protocol.SubjectProtocol;
import com.dengfx.googleplay.utils.HttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectFragment extends BaseFragment {
    private List<SubjectBean> mDataSet;
    private SubjectProtocol mSubjectProtocol;

    public static SubjectFragment newInstance() {
        return new SubjectFragment();
    }

    @Override
    public View initSuccessView() {
        ListView listView = ListViewFactory.createListView();
        listView.setAdapter(new SubjectAdapter(mDataSet, listView) {
            @Override
            public List onLoadMore() throws Exception {
                SystemClock.sleep(2000);
                return mSubjectProtocol.loadData(getUrl(mDataSet.size()));
            }
        });
        return listView;
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        mSubjectProtocol = new SubjectProtocol();
        try {
            List<SubjectBean> subjectBeanList = mSubjectProtocol.loadData(getUrl(0));
            if (subjectBeanList != null && subjectBeanList.size() != 0) {
                mDataSet = subjectBeanList;
                return LoadingPager.LoadedResult.RESULT_SUCCESS;
            } else {
                return LoadingPager.LoadedResult.RESULT_EMPTY;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.RESULT_ERROR;
        }
    }

    @NonNull
    private String getUrl(int index) {
        Map<String, Object> params = new HashMap<>();
        params.put("index", index % 60);
        return Constants.URLS.BASEURL + "subject?" + HttpUtils.getUrlParamsByMap(params);
    }
}
