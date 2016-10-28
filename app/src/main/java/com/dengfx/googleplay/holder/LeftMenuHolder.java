package com.dengfx.googleplay.holder;

import android.view.View;

import com.dengfx.googleplay.R;
import com.dengfx.googleplay.base.BaseHolder;
import com.dengfx.googleplay.utils.UIUtils;

/**
 * 创建者     伍碧林
 * 版权       传智播客.黑马程序员
 * 描述
 */
public class LeftMenuHolder extends BaseHolder<Object> {
    @Override
    public void setData2HolderView(Object data) {

    }

    @Override
    public View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.menu_view, null);
        return holderView;
    }


}
