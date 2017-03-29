package com.alless.googleplay.ui.fragment;

import android.widget.BaseAdapter;

import com.alless.googleplay.adapter.SubjectAdapter;
import com.alless.googleplay.bean.SubjectItemBean;
import com.alless.googleplay.network.HeiMaRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/3/26.
 */

public class SubjectFragment extends BaseLoadMoreListFragment {

    private List<SubjectItemBean> mDataList;

    @Override
    protected void onStartLoadData() {
        Call<List<SubjectItemBean>> listCall = HeiMaRetrofit.getInstance().getApi().listSubject(0);
        listCall.enqueue(new Callback<List<SubjectItemBean>>() {
            @Override
            public void onResponse(Call<List<SubjectItemBean>> call, Response<List<SubjectItemBean>> response) {
                mDataList = response.body();
                onDataLoadSuccess();
            }

            @Override
            public void onFailure(Call<List<SubjectItemBean>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStartLoadMore() {
        Call<List<SubjectItemBean>> listCall = HeiMaRetrofit.getInstance().getApi().listSubject(mDataList.size());
        listCall.enqueue(new Callback<List<SubjectItemBean>>() {
            @Override
            public void onResponse(Call<List<SubjectItemBean>> call, Response<List<SubjectItemBean>> response) {
                mDataList.addAll(response.body());
                getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SubjectItemBean>> call, Throwable t) {

            }
        });
    }

    @Override
    public BaseAdapter onCreateAdapter() {
        return  new SubjectAdapter(getContext(),mDataList);
    }
}
