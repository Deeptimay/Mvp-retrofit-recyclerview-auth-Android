package com.mobiotics.deeptimay.deeptimaymobiotics.activityRecordList;

import android.content.Context;

import com.mobiotics.deeptimay.deeptimaymobiotics.model.Records;

import java.util.ArrayList;

public class Presenter implements GetDataRecodrs.Presenter, GetDataRecodrs.onGetDataListener {
    private GetDataRecodrs.View mGetDataView;
    private Intractor mIntractor;

    public Presenter(GetDataRecodrs.View mGetDataView) {
        this.mGetDataView = mGetDataView;
        mIntractor = new Intractor(this);
    }

    @Override
    public void getDataFromURL(Context context, String url) {
        mIntractor.initRetrofitCall(context, url);
    }

    @Override
    public void onSuccess(String message, ArrayList<Records> allRecords) {
        mGetDataView.onGetDataSuccess(message, allRecords);
    }

    @Override
    public void onFailure(String message) {
        mGetDataView.onGetDataFailure(message);
    }
}
