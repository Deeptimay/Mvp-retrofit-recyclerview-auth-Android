package com.mobiotics.deeptimay.deeptimaymobiotics.activityRecordList;

import android.content.Context;

import com.mobiotics.deeptimay.deeptimaymobiotics.model.Records;

import java.util.ArrayList;

public interface GetDataRecodrs {
    interface View {
        void onGetDataSuccess(String message, ArrayList<Records> recordsArrayList);

        void onGetDataFailure(String message);
    }

    interface Presenter {
        void getDataFromURL(Context context, String url);
    }

    interface Interactor {
        void initRetrofitCall(Context context, String url);

    }

    interface onGetDataListener {
        void onSuccess(String message, ArrayList<Records> recordsArrayList);

        void onFailure(String message);
    }
}
