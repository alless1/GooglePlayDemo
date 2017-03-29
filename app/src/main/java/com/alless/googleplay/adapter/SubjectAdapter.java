package com.alless.googleplay.adapter;

import android.content.Context;

import com.alless.googleplay.bean.SubjectItemBean;
import com.alless.googleplay.widget.SubjectListItemView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */
public class SubjectAdapter extends BaseLoadMoreListAdapter<SubjectItemBean> {


    public SubjectAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    protected ViewHolder onCreateNormalViewHolder() {
        return new ViewHolder(new SubjectListItemView(getContext()));
    }

    @Override
    protected void onBindNormalViewHolder(ViewHolder viewHolder, int position) {
        ((SubjectListItemView)viewHolder.mView).bindView(getDataList().get(position));
    }
}
