package com.dengfx.googleplay.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import com.dengfx.googleplay.adapter.ItemAdapter;
import com.dengfx.googleplay.base.BaseFragment;
import com.dengfx.googleplay.base.LoadingPager;
import com.dengfx.googleplay.bean.HomeBean;
import com.dengfx.googleplay.bean.ItemBean;
import com.dengfx.googleplay.factory.ListViewFactory;
import com.dengfx.googleplay.holder.HomePicturesHolder;
import com.dengfx.googleplay.holder.ItemHolder;
import com.dengfx.googleplay.download.DownloadInfo;
import com.dengfx.googleplay.download.DownloadManager;
import com.dengfx.googleplay.protocol.HomeProtocol;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment {

    private List<ItemBean> mDataSet;
    private ArrayList<String> mPictureUrls;
    private HomeProtocol mHomeProtocol;
    private ItemAdapter mItemAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        mHomeProtocol = new HomeProtocol();
        try {
            HomeBean homeBean = mHomeProtocol.loadData(getUrl("home", 0));
            ArrayList<ItemBean> list = homeBean.list;
            mPictureUrls = homeBean.picture;
            if (list != null && list.size() != 0) {
                mDataSet = list;
                return LoadingPager.LoadedResult.RESULT_SUCCESS;
            } else {
                return LoadingPager.LoadedResult.RESULT_EMPTY;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            return LoadingPager.LoadedResult.RESULT_ERROR;
        }
    }

    @Override
    public View initSuccessView() {
        ListView listView = ListViewFactory.createListView();
        HomePicturesHolder homePicturesHolder = new HomePicturesHolder();
        homePicturesHolder.setData(mPictureUrls);
        listView.addHeaderView(homePicturesHolder.mItemView);
        mItemAdapter = new ItemAdapter(mDataSet, listView) {
            @Override
            public List onLoadMore() throws Exception {
                SystemClock.sleep(2000);
                return mHomeProtocol.loadData(getUrl("home", mDataSet.size())).list;
            }
        };
        listView.setAdapter(mItemAdapter);
        return listView;
    }

    @Override
    public void onResume() {
        //listview-->adapter
        if (mItemAdapter != null) {
            List<ItemHolder> itemHolders = mItemAdapter.mItemHolders;
            if (itemHolders != null && itemHolders.size() != 0) {
                for (ItemHolder itemHolder : itemHolders) {
                    //添加观察者到观察者集合中
                    DownloadManager.getInstance().addDownloadInfoObserver(itemHolder);

                    //手动发布最新的状态
                    DownloadInfo downLoadInfo = DownloadManager.getInstance().getDownloadInfo(itemHolder.mData);
                    DownloadManager.getInstance().notifyDownloadInfoObservers(downLoadInfo);
                }
            }
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mItemAdapter != null) {
            List<ItemHolder> itemHolders = mItemAdapter.mItemHolders;
            if (itemHolders != null && itemHolders.size() != 0) {
                for (ItemHolder itemHolder : itemHolders) {
                    //从观察者集合中移除观察者
                    DownloadManager.getInstance().removeDownloadInfoObserver(itemHolder);
                }
            }
        }
        super.onPause();
    }
}
