package com.alless.googleplay.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/3/27.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mDataList;

    public BaseListAdapter(Context context, List<T> dataList) {

        mContext = context;
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        if(mDataList!=null)
            return mDataList.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder = null;
        if (convertView == null) {
            holder = onCreateViewHolder(position);
            convertView = holder.mView;
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //让子类实现View的绑定
        onBindViewHolder(holder, position);

        return convertView;
    }

    abstract void onBindViewHolder(ViewHolder holder, int position);

    /**
     * 创建一个viewholder 对应的位置item view
     */
    abstract ViewHolder onCreateViewHolder(int position);
    public class ViewHolder{
        View mView;

        public ViewHolder(View view) {
            mView = view;
        }
    }

    protected List<T> getDataList() {
        return mDataList;
    }

    protected Context getContext() {
        return mContext;
    }
}
