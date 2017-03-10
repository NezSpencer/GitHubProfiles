package com.nezspencer.githubprofiles.DI.component;

import android.content.Context;

import com.nezspencer.githubprofiles.DI.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by nezspencer on 3/6/17.
 */


@Component(modules = ApplicationModule.class)
@Singleton
public interface ApplicationComponent {

    Retrofit getRetrofit();

    Context getContext();
}
