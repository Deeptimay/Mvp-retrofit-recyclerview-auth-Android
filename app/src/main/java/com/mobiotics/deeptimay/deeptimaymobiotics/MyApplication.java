package com.mobiotics.deeptimay.deeptimaymobiotics;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApplication extends Application {

    public static final String TAG = MyApplication.class
            .getSimpleName();
    private static MyApplication mInstance;

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

}