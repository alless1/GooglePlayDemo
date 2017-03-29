package com.alless.googleplay.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/3/27.
 */

public abstract class BaseListFragment extends BaseFragment {

    private ListView mListView;
    private BaseAdapter mAdapter;

    //直接返回设置好的listView 子类调用
    @Override
    protected View onCreateContentView() {
        mListView = new ListView(getContext());
        mAdapter = onCreateAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mOnItemClickListener);
        mListView.setDivider(null);//去掉分割线
        View header = onCreateHeaderView();
        if (header != null) {
            mListView.addHeaderView(header);
        }
        initListView();
        return mListView;
    }

    /**
     * 抽取创建头的方法,子类可以实现
     * @return
     */
    protected View onCreateHeaderView() {
        return null;
    }

    protected void initListView() {

    }

    //由子类去实现具体适配内容
    public abstract BaseAdapter onCreateAdapter() ;

    @Override
    protected void onStartLoadData() {

    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           position = position - getListView().getHeaderViewsCount();
            onListItemClick(position);
        }
    };

    /**
     * 子类实现item点击事件
     */
    protected void onListItemClick(int position) {

    }
    public ListView getListView() {
        return mListView;
    }

    public BaseAdapter getAdapter() {
        return mAdapter;
    }

}
