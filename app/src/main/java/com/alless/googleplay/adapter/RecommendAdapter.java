package com.alless.googleplay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.alless.googleplay.widget.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/3/27.
 */

public class RecommendAdapter implements StellarMap.Adapter {
    private static final int PAGE_SIZE = 15;
    private Context mContext;
    private List<String> mDataList;

    public RecommendAdapter(Context context, List<String> dataList) {

        mContext = context;
        mDataList = dataList;
    }

    /**
     * 返回组(页面)个数
     * @return
     */
    @Override
    public int getGroupCount() {
        int count = mDataList.size() / PAGE_SIZE;
        return count % PAGE_SIZE==0?count:++count;
    }

    @Override
    public int getCount(int group) {
        //有余数且为最后一个元素返回的个数
        if(mDataList.size()%PAGE_SIZE!=0){
            if(group==getGroupCount()-1) {
                return mDataList.size() % PAGE_SIZE;
            }
        }
        //正常情况返回个数
        return PAGE_SIZE;
    }

    @Override
    public View getView(int group, int position, View convertView) {
        if (convertView == null) {
            convertView = new TextView(mContext);
        }
        TextView tv = (TextView) convertView;
        int pos = group*PAGE_SIZE + position;
        tv.setText(mDataList.get(pos));
        tv.setTextColor(getArgb());
        int size = 15+ new Random().nextInt(10);
        tv.setTextSize(size);
        return tv;
    }

    private int getArgb() {
        int alpha = 255;
        int red = 30 + new Random().nextInt(200);
        int green = 30 + new Random().nextInt(200);
        int blue = 30 + new Random().nextInt(200);
        return Color.argb(alpha,red,green,blue);
    }

    @Override
    public int getNextGroupOnPan(int group, float degree) {
        return 0;
    }

    @Override
    public int getNextGroupOnZoom(int group, boolean isZoomIn) {
        if (isZoomIn) {
            return (group+1)%getGroupCount();
        }else{
            return (group-1+getGroupCount())%getGroupCount();
        }
    }
}
