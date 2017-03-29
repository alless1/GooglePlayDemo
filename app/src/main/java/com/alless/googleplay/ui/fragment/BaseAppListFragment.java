package com.alless.googleplay.ui.fragment;

import android.widget.BaseAdapter;

import com.alless.googleplay.adapter.AppListAdapter;
import com.alless.googleplay.bean.AppListItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 由于首页，游戏，应用3个页面的item长得一样，说明adpater一样，可以抽取一个公共的adapter
 * 数据列表也一样，所以也可以抽取数据列表
 */

public abstract class BaseAppListFragment extends BaseLoadMoreListFragment {
    List<AppListItemBean> mDataList = new ArrayList<AppListItemBean>();
    @Override
    public BaseAdapter onCreateAdapter() {
        return new AppListAdapter(getContext(),mDataList);
    }

    //暴露给子类,让子类能够获取到数据添加到数据集合
    public List<AppListItemBean> getDataList() {
        return mDataList;
    }
}
