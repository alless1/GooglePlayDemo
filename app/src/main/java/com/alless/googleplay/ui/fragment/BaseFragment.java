package com.alless.googleplay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.alless.googleplay.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/26.
 */

public abstract class BaseFragment extends Fragment {
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.retry)
    Button mRetry;
    @BindView(R.id.loading_error)
    LinearLayout mLoadingError;
    @BindView(R.id.fragment_content)
    FrameLayout mFragmentContent;

    private static final String TAG = "BaseFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, null);
        ButterKnife.bind(this,view);
        return view;
    }

    //布局加载完成
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startLoadData();
    }

    /**
     * 子类必须实现该方法来创建自己的视图
     */
    protected abstract View onCreateContentView();

    protected abstract void startLoadData();

    //数据加载成功,更新界面
    public void onDataLoadSuccess() {
        mProgressBar.setVisibility(View.GONE);
        mFragmentContent.addView(onCreateContentView());
    }

    //数据加载失败的界面
    public void onDataLoadFailed() {
        Log.d(TAG, "onDataLoadFailed: mProgressBar:"+mProgressBar);
        mProgressBar.setVisibility(View.GONE);
        mLoadingError.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.retry)
    public void onClick() {
        mLoadingError.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        startLoadData();
    }
}
