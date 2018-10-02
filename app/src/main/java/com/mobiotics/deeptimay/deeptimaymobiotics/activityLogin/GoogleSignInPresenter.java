package com.mobiotics.deeptimay.deeptimaymobiotics.activityLogin;

import android.content.Intent;


public interface GoogleSignInPresenter {
    void createGoogleClient(LoginActivity loginView);

    void onStart();

    void signIn(LoginActivity loginView);

    void onActivityResult(LoginActivity loginView, int requestCode, int resultCode, Intent data);

    void onStop();

    void onDestroy();

}
