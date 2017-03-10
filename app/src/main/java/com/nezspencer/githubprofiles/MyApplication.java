package com.nezspencer.githubprofiles;

import android.app.Application;

import com.nezspencer.githubprofiles.DI.component.ApplicationComponent;
import com.nezspencer.githubprofiles.DI.component.DaggerApplicationComponent;
import com.nezspencer.githubprofiles.DI.module.ApplicationModule;
import com.nezspencer.githubprofiles.api.UserResponseItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nezspencer on 3/7/17.
 */

public class MyApplication extends Application {

    public static final List<UserResponseItems> ITEMS = new ArrayList<UserResponseItems>();
    public static MyApplication appInstance;


    private static final String BASE_URL="https://api.github.com";
    public static ApplicationComponent component;
    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this,BASE_URL))
                .build();
        appInstance=this;
    }

    public static void addItems(List<UserResponseItems> items) {
        ITEMS.clear();
        ITEMS.addAll(items);

    }

    public static MyApplication getAppInstance(){return appInstance;}

    public ApplicationComponent getApplicationComponent(){return  component;}
}
