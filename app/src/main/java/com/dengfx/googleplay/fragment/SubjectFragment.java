package com.dengfx.googleplay.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import com.dengfx.googleplay.adapter.SubjectAdapter;
import com.dengfx.googleplay.base.BaseFragment;
import com.dengfx.googleplay.base.LoadingPager;
import com.dengfx.googleplay.bean.SubjectBean;
import com.dengfx.googleplay.factory.ListViewFactory;
import com.dengfx.googleplay.protocol.SubjectProtocol;

import java.util.List;

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
                return mSubjectProtocol.loadData(getUrl("subject", mDataSet.size()));
            }
        });
        return listView;
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        mSubjectProtocol = new SubjectProtocol();
        try {
            mDataSet = mSubjectProtocol.loadData(getUrl("subject", 0));
            if (mDataSet != null && mDataSet.size() != 0) {
                return LoadingPager.LoadedResult.RESULT_SUCCESS;
            } else {
                return LoadingPager.LoadedResult.RESULT_EMPTY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.RESULT_ERROR;
        }
    }
}
