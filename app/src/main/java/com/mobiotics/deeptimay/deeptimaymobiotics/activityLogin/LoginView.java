package com.mobiotics.deeptimay.deeptimaymobiotics.activityLogin;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public interface LoginView {
    void specifyGoogleSignIn(GoogleSignInOptions gso);

    void startProfileActivity();

    Context getContext();
}
