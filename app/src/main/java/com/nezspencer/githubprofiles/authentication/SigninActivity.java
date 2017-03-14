package com.nezspencer.githubprofiles.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.auth0.android.Auth0;
import com.auth0.android.lock.AuthenticationCallback;
import com.auth0.android.lock.Lock;
import com.auth0.android.lock.LockCallback;
import com.auth0.android.lock.utils.LockException;
import com.auth0.android.result.Credentials;
import com.nezspencer.githubprofiles.Constants;
import com.nezspencer.githubprofiles.MainActivity;

/**
 * Created by nezspencer on 3/9/17.
 */

public class SigninActivity extends AppCompatActivity {

    private Lock lock;
    private LockCallback callback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpAuth0();

        startActivity(lock.newIntent(this));
    }

    public void setUpAuth0(){
        Auth0 auth0 = new Auth0("8kjoHUr3e3i74gchtgs0X7j56r99Chss", "eddyspencer.auth0.com");
        callback=new AuthenticationCallback() {
            @Override
            public void onAuthentication(Credentials credentials) {
                saveToSharedPreferences(credentials.getAccessToken());
                Log.e("EDONE ",credentials.getAccessToken());

                startActivity(new Intent(SigninActivity.this, MainActivity.class));
            }

            @Override
            public void onCanceled() {

            }

            @Override
            public void onError(LockException error) {
                Log.e("error",error.getMessage());
            }
        };

        lock = Lock.newBuilder(auth0, callback)
                /*.allowSignUp(true)
                .allowLogIn(true)
                .loginAfterSignUp(true)*/
                .build(this);
    }

    public void saveToSharedPreferences(String value) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit().putString(Constants.KEY_AUTH,value).apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lock.onDestroy(this);
        lock=null;
    }
}
