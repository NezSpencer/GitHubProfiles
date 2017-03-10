package com.nezspencer.githubprofiles;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.nezspencer.githubprofiles.userprofiles.ProfileListFragment;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;



        if (getResources().getConfiguration().smallestScreenWidthDp >= 600)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String auth= PreferenceManager.getDefaultSharedPreferences(this)
                .getString(Constants.KEY_AUTH,null);


        /*if (auth==null)
        {
            //unauthenticated account

            loadFragment(new SignInFragment());
        }
        else{
            //authenticated
            loadFragment(new ProfileListFragment());
        }*/
        loadFragment(new ProfileListFragment());
    }

    public static void loadFragment(Fragment fragment){
        instance.getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main,fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
