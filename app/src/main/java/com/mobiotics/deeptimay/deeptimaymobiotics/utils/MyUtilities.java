package com.mobiotics.deeptimay.deeptimaymobiotics.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mobiotics.deeptimay.deeptimaymobiotics.interfaces.Constants;

/**
 * Created by ${Deeptimay} on 15/12/17.
 */

public class MyUtilities {

    public static void setFirstTimeValue(Context context, boolean isSyncOn) {
        SharedPreferences syncPreferences = context.getSharedPreferences(
                "isFirstTimeView", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = syncPreferences.edit();
        prefEditor.putBoolean("isFirstTime", isSyncOn);
        prefEditor.apply();
    }

    public static void setSharedPref(Context context, String key, String value) {
        // save token in preference
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.SharedPref, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        Boolean bool = editor.commit();
    }

    public static void setSharedPref(Context context, String key, boolean value) {
        // save token in preference
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.SharedPref, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setSharedPref(Context context, String key, long value) {
        // save token in preference
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.SharedPref, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static String getSharedPref(Context context, String key,
                                       String defaultVal) {

        String prefToken = defaultVal;
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(
                    Constants.SharedPref, 0);
            prefToken = sharedPreferences.getString(key, defaultVal);

        } catch (Exception ignored) {
        }

        return prefToken;
    }

    public static boolean isContain(Context context, String key,
                                    String defaultVal) {

        boolean prefToken = false;
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(
                    Constants.SharedPref, 0);
            prefToken = sharedPreferences.contains(key);

        } catch (Exception ignored) {
        }

        return prefToken;
    }

    public static long getSharedPref(Context context, String key,
                                     long defaultVal) {
        // get token from preference
//        File file = new File(SHARED_PREF_ADD);

        long prefToken = defaultVal;
//        if (file.exists()) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.SharedPref, 0);
        prefToken = sharedPreferences.getLong(key, defaultVal);
//        }
        return prefToken;
    }

    public static boolean getSharedPref(Context context, String key,
                                        boolean defaultVal) {
        // get token from preference
//        File file = new File(SHARED_PREF_ADD);

        boolean prefToken = defaultVal;
//        if (file.exists()) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.SharedPref, 0);
        prefToken = sharedPreferences.getBoolean(key, defaultVal);
//        }
        return prefToken;
    }

    public static boolean getDefaultSharedPref(Context context, String key,
                                               boolean defaultVal) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, defaultVal);
    }

    public static String getDefaultSharedPref(Context context, String key,
                                              String defaultVal) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, defaultVal);
    }

}
