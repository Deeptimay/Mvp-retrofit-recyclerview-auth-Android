package com.mobiotics.deeptimay.deeptimaymobiotics.activityRecordList;

import android.content.Context;

import com.mobiotics.deeptimay.deeptimaymobiotics.MyApplication;
import com.mobiotics.deeptimay.deeptimaymobiotics.database.MobioticsDb;
import com.mobiotics.deeptimay.deeptimaymobiotics.networking.APIService;
import com.mobiotics.deeptimay.deeptimaymobiotics.networking.ApiUtils;
import com.mobiotics.deeptimay.deeptimaymobiotics.model.Records;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Intractor implements GetDataRecodrs.Interactor {

    private GetDataRecodrs.onGetDataListener mOnGetDatalistener;

    public Intractor(GetDataRecodrs.onGetDataListener mOnGetDatalistener) {
        this.mOnGetDatalistener = mOnGetDatalistener;
    }

    @Override
    public void initRetrofitCall(Context context, String url) {

        APIService mAPIService = ApiUtils.getAPIService();

        mAPIService.getRecords(url).enqueue(new Callback<ArrayList<Records>>() {
            @Override
            public void onResponse(Call<ArrayList<Records>> call, Response<ArrayList<Records>> response) {
                ArrayList<Records> responseArrayList = (ArrayList<Records>) response.body();
                MobioticsDb mobioticsDb = new MobioticsDb(MyApplication.getInstance());
                mobioticsDb.addRecords(responseArrayList);
                mOnGetDatalistener.onSuccess("List Size: " + responseArrayList.size(), responseArrayList);
            }

            @Override
            public void onFailure(Call<ArrayList<Records>> call, Throwable t) {
                t.getMessage();
            }
        });
    }
}
