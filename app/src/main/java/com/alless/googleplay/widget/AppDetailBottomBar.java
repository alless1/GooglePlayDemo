package com.alless.googleplay.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.alless.googleplay.R;

/**
 * Created by Administrator on 2017/3/31.
 */

public class AppDetailBottomBar extends BaseView {
    public AppDetailBottomBar(Context context) {
        super(context);
    }

    public AppDetailBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_app_detail_bottom_bar;
    }
}
