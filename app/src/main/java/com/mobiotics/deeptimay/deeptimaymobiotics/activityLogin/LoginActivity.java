package com.mobiotics.deeptimay.deeptimaymobiotics.activityLogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.mobiotics.deeptimay.deeptimaymobiotics.R;
import com.mobiotics.deeptimay.deeptimaymobiotics.database.MobioticsDb;
import com.mobiotics.deeptimay.deeptimaymobiotics.activityRecordList.RecordListActivity;
import com.mobiotics.deeptimay.deeptimaymobiotics.utils.MyUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private GoogleSignInPresenter signInGooglePresenter;

    @BindView(R.id.sign_in_button)
    SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String srt = MyUtilities.getSharedPref(LoginActivity.this, "dashboard", "");
        if (srt.equalsIgnoreCase("dashboard")) {
            Intent intent = new Intent(LoginActivity.this, RecordListActivity.class);
            startActivity(intent);
            finish();

        } else {
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);

            signInButton.setSize(SignInButton.SIZE_STANDARD);

            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signInGooglePresenter.signIn(LoginActivity.this);
                }
            });
            signInGooglePresenter = new GoogleSignIn(this);
            signInGooglePresenter.createGoogleClient(this);

            MobioticsDb mobioticsDb = new MobioticsDb(LoginActivity.this);
            mobioticsDb.deleteAllRecord();
        }
    }

    protected void onStart() {
        super.onStart();
        signInGooglePresenter.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        signInGooglePresenter.onActivityResult(LoginActivity.this, requestCode, resultCode, data);
    }

    @Override
    public void specifyGoogleSignIn(GoogleSignInOptions gso) {
        signInButton.setScopes(gso.getScopeArray());
    }


    @Override
    public void startProfileActivity() {
        Intent goToProfile = new Intent(LoginActivity.this, RecordListActivity.class);
        startActivity(goToProfile);
        finish();
    }


    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }


    @Override
    public void onBackPressed() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (signInGooglePresenter != null)
            signInGooglePresenter.onDestroy();
    }
}
