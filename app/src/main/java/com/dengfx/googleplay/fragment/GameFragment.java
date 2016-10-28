package com.dengfx.googleplay.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import com.dengfx.googleplay.adapter.ItemAdapter;
import com.dengfx.googleplay.base.BaseFragment;
import com.dengfx.googleplay.base.LoadingPager;
import com.dengfx.googleplay.bean.ItemBean;
import com.dengfx.googleplay.factory.ListViewFactory;
import com.dengfx.googleplay.holder.ItemHolder;
import com.dengfx.googleplay.download.DownloadInfo;
import com.dengfx.googleplay.download.DownloadManager;
import com.dengfx.googleplay.protocol.GameProtocol;

import java.util.List;

public class GameFragment extends BaseFragment {

    private List<ItemBean> mDataSet;
    private GameProtocol mGameProtocol;
    private ItemAdapter mItemAdapter;

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Override
    public View initSuccessView() {
        ListView listView = ListViewFactory.createListView();
        mItemAdapter = new ItemAdapter(mDataSet, listView) {
            @Override
            public List onLoadMore() throws Exception {
                SystemClock.sleep(2000);
                return mGameProtocol.loadData(getUrl("game", mDataSet.size()));
            }
        };
        listView.setAdapter(mItemAdapter);
        return listView;
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        mGameProtocol = new GameProtocol();
        try {
            mDataSet = mGameProtocol.loadData(getUrl("game", 0));
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