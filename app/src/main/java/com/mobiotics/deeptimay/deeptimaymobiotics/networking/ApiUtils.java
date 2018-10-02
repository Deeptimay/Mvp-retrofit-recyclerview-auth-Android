package com.mobiotics.deeptimay.deeptimaymobiotics.networking;

public class ApiUtils {

    private static final String BASE_URL = "https://google.com";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
