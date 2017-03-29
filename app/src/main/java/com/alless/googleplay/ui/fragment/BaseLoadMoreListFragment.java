package com.alless.googleplay.ui.fragment;

import android.widget.AbsListView;

/**
 * 首页，应用，游戏，专题都能够滚动到底部加载更多，都显示一个加载进度条。 所以BaseLoadMoreListFragment封装了滚动到列表底部处罚加载更多的逻辑
 */

public abstract class BaseLoadMoreListFragment extends BaseListFragment {

    @Override
    protected void initListView() {
        super.initListView();
        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    if(view.getLastVisiblePosition()==getLoadMorePosition()){
                        onStartLoadMore();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private int getLoadMorePosition() {
        return getAdapter().getCount() - 1 + getListView().getHeaderViewsCount();
    }
    /**
     * 子类实现加载更多数据
     */
    protected abstract void onStartLoadMore();


}
