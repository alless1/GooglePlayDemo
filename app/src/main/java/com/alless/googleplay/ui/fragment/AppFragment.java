package com.alless.googleplay.ui.fragment;

import com.alless.googleplay.bean.AppListItemBean;
import com.alless.googleplay.network.HeiMaRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/3/26.
 */

public class AppFragment extends BaseAppListFragment {

    @Override
    protected void onStartLoadData() {
        Call<List<AppListItemBean>> listCall = HeiMaRetrofit.getInstance().getApi().listApp(0);
        listCall.enqueue(new Callback<List<AppListItemBean>>() {
            @Override
            public void onResponse(Call<List<AppListItemBean>> call, Response<List<AppListItemBean>> response) {
                getDataList().addAll(response.body());
                onDataLoadSuccess();
            }

            @Override
            public void onFailure(Call<List<AppListItemBean>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStartLoadMore() {
        Call<List<AppListItemBean>> listCall = HeiMaRetrofit.getInstance().getApi().listApp(getDataList().size());
        listCall.enqueue(new Callback<List<AppListItemBean>>() {
            @Override
            public void onResponse(Call<List<AppListItemBean>> call, Response<List<AppListItemBean>> response) {
                getDataList().addAll(response.body());
                getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AppListItemBean>> call, Throwable t) {

            }
        });
    }


}
