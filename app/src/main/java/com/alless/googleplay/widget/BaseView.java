package com.alless.googleplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/30.
 */

public abstract class BaseView extends RelativeLayout {
    public BaseView(Context context) {
        this(context,null);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected  void init(){
        View.inflate(getContext(), getLayoutId(), this);
        ButterKnife.bind(this);
    }

    protected abstract int getLayoutId();
}
