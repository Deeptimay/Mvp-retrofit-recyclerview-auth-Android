package com.mobiotics.deeptimay.deeptimaymobiotics.networking;

import com.mobiotics.deeptimay.deeptimaymobiotics.model.Records;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIService {

    @GET
    Call<ArrayList<Records>> getRecords(@Url String url);

}
