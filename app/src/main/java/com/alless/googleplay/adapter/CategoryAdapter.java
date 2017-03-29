package com.alless.googleplay.adapter;

import android.content.Context;

import com.alless.googleplay.bean.CategoryItemBean;
import com.alless.googleplay.widget.CategoryItemView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/27.
 */

public class CategoryAdapter extends BaseListAdapter<CategoryItemBean> {

    public CategoryAdapter(Context context, List<CategoryItemBean> dataList) {
        super(context, dataList);
    }

    /**
     * 根据position位置获取对应的数据,刷新item
     * @param holder
     * @param position
     */
    @Override
    void onBindViewHolder(ViewHolder holder, int position) {
        ((CategoryItemView)holder.mView).bindView(getDataList().get(position));
    }

    @Override
    ViewHolder onCreateViewHolder(int position) {
        return new ViewHolder(new CategoryItemView(getContext()));
    }
}
