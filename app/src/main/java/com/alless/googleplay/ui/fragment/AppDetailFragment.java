package com.alless.googleplay.ui.fragment;

import android.view.View;

import com.alless.googleplay.R;
import com.alless.googleplay.bean.AppDetailBean;
import com.alless.googleplay.network.HeiMaRetrofit;
import com.alless.googleplay.widget.AppDetailDesView;
import com.alless.googleplay.widget.AppDetailGalleryView;
import com.alless.googleplay.widget.AppDetailInfoView;
import com.alless.googleplay.widget.AppDetailSecurityView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/3/30.
 */

public class AppDetailFragment extends BaseFragment {
    private static final String TAG = "AppDetailFragment";


    private AppDetailBean mAppDetailBean;

    @Override
    protected void onStartLoadData() {
        String packageName = getActivity().getIntent().getStringExtra("package_Name");
        Call<AppDetailBean> appDetail = HeiMaRetrofit.getInstance().getApi().getAppDetail(packageName);
        appDetail.enqueue(new Callback<AppDetailBean>() {
            @Override
            public void onResponse(Call<AppDetailBean> call, Response<AppDetailBean> response) {
                mAppDetailBean = response.body();
                onDataLoadSuccess();
            }

            @Override
            public void onFailure(Call<AppDetailBean> call, Throwable t) {

            }
        });
    }

    @Override
    protected View onCreateContentView() {
        View view = View.inflate(getContext(), R.layout.app_detail_content, null);
        //应用信息
        AppDetailInfoView appDetailInfoView = (AppDetailInfoView) view.findViewById(R.id.app_detail_info);
        appDetailInfoView.bingView(mAppDetailBean);
        //安全信息
        AppDetailSecurityView appDetailSecurityView = (AppDetailSecurityView) view.findViewById(R.id.app_detail_security);
        appDetailSecurityView.bindView(mAppDetailBean);
        //图片展示
        AppDetailGalleryView appDetailGalleryView = (AppDetailGalleryView) view.findViewById(R.id.app_detail_gallery);
        appDetailGalleryView.bindView(mAppDetailBean);
        //应用描述
        AppDetailDesView appDetailDesView = (AppDetailDesView) view.findViewById(R.id.app_detail_des);
        appDetailDesView.bindView(mAppDetailBean);

        return view;

    }
}
