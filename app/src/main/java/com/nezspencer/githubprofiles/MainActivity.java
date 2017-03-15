package com.nezspencer.githubprofiles;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.nezspencer.githubprofiles.userprofiles.ProfileListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;
    @Bind(R.id.main_toolbar)Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        instance=this;

        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle(getString(R.string.app_name));
            applyFontForToolbarTitle(toolbar);
        }






        if (getResources().getConfiguration().smallestScreenWidthDp >= 600)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


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

    public static void applyFontForToolbarTitle(Toolbar toolbar){


        for(int i = 0; i < toolbar.getChildCount(); i++){
            View view = toolbar.getChildAt(i);
            if(view instanceof TextView){
                TextView tv = (TextView) view;
                Typeface titleFont = Typeface.createFromAsset(instance.getAssets(), "Roboto-Bold.ttf");
                if(tv.getText().equals(toolbar.getTitle())){
                    tv.setTypeface(titleFont);
                    break;
                }
            }
        }
    }


}
