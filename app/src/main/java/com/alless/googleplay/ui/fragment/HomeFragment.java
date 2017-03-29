package com.alless.googleplay.ui.fragment;

import android.view.View;

import com.alless.googleplay.app.Constant;
import com.alless.googleplay.bean.HomeBean;
import com.alless.googleplay.network.HeiMaRetrofit;
import com.leon.loopviewpagerlib.FunBanner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/3/26.
 */

public class HomeFragment extends BaseAppListFragment {
    private List<String> mPicture;
    @Override
    protected View onCreateHeaderView() {
        return new FunBanner.Builder(getContext())
                .setHeightWidthRatio(0.377f)
                .setEnableAutoLoop(true)
                .setImageUrlHost(Constant.URL_IMAGE)
                .setImageUrls(mPicture)
                .build();
    }

    @Override
    protected void onStartLoadData() {
        Call<HomeBean> homeBeanCall = HeiMaRetrofit.getInstance().getApi().listHome(0);
        homeBeanCall.enqueue(new Callback<HomeBean>() {
            @Override
            public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {
                getDataList().addAll(response.body().getList());
                mPicture = response.body().getPicture();
                onDataLoadSuccess();
            }

            @Override
            public void onFailure(Call<HomeBean> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStartLoadMore() {
        Call<HomeBean> homeBeanCall = HeiMaRetrofit.getInstance().getApi().listHome(getDataList().size());
        homeBeanCall.enqueue(new Callback<HomeBean>() {
            @Override
            public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {
                getDataList().addAll(response.body().getList());
                getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<HomeBean> call, Throwable t) {

            }
        });

    }
}
