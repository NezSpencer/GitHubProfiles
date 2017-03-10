package com.nezspencer.githubprofiles.userprofiles;

import com.nezspencer.githubprofiles.MyApplication;
import com.nezspencer.githubprofiles.api.GithubApi;
import com.nezspencer.githubprofiles.api.UserResponse;
import com.nezspencer.githubprofiles.api.UserResponseItems;

import java.util.Arrays;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by nezspencer on 3/9/17.
 */

public class ProfileListPresenter {

    private InterfaceProfileList profileListInterface;
    Retrofit retrofit;
    private Subscription subscription;
    private Observer<List<UserResponseItems>> profileListObserver;

    public ProfileListPresenter(InterfaceProfileList profileList) {
        profileListInterface=profileList;

        retrofit= MyApplication.getAppInstance().getApplicationComponent()
                .getRetrofit();

        profileListObserver=new Observer<List<UserResponseItems>>() {
            @Override
            public void onCompleted() {
                profileListInterface.stopLoadingProgress();
            }

            @Override
            public void onError(Throwable e) {
                profileListInterface.stopLoadingProgress();
                profileListInterface.showErrorMessage("Error! retry");
            }

            @Override
            public void onNext(List<UserResponseItems> userResponseItemses) {

                profileListInterface.refreshAdapter(userResponseItemses);
            }
        };
    }

    public void fetchUserData(String accountType, String location, String language,String oauTH){
        profileListInterface.showLoadingProgress();

        subscription=retrofit.create(GithubApi.class).getUsers(accountType,location,language,oauTH)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<UserResponse, List<UserResponseItems>>() {
                    @Override
                    public List<UserResponseItems> call(UserResponse userResponse) {
                        return Arrays.asList(userResponse.getItems());
                    }
                })
                .subscribe(profileListObserver);

        profileListInterface.getCurrentSubscription(subscription);


    }
}
