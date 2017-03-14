package com.nezspencer.githubprofiles.userprofiles;

import android.util.Log;

import com.nezspencer.githubprofiles.MyApplication;
import com.nezspencer.githubprofiles.api.GithubApi;
import com.nezspencer.githubprofiles.api.UserResponse;
import com.nezspencer.githubprofiles.api.UserResponseItems;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observer;
import rx.Subscription;

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
                Log.e("ERROR",e.getMessage());
            }

            @Override
            public void onNext(List<UserResponseItems> userResponseItemses) {

                profileListInterface.refreshAdapter(userResponseItemses);
            }
        };
    }

    public void fetchUserData(String accountType, String location, String language, String
            oauTH, final int currentPage){
        profileListInterface.showLoadingProgress();

        /**RXJava crashes unpredictably so Retrofit was modified to return a Call object instead
         * of observable */
       /* subscription=retrofit.create(GithubApi.class).getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<UserResponse, List<UserResponseItems>>() {
                    @Override
                    public List<UserResponseItems> call(UserResponse userResponse) {
                        Log.e("Show",""+userResponse.getTotal_count());
                        return Arrays.asList(userResponse.getItems());

                    }
                })
                .subscribe(profileListObserver);*/

       retrofit.create(GithubApi.class).getGitHubUsers(""+currentPage)
               .enqueue(new Callback<UserResponse>() {
           @Override
           public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

               profileListInterface.stopLoadingProgress();
               if (currentPage==1)
               profileListInterface.refreshAdapter(Arrays.asList(response.body().getItems()));
               else
               {
                   profileListInterface.addToAdapterList(Arrays.asList(response.body().getItems()));
                   profileListInterface.onLoadSuccessful();
               }
           }

           @Override
           public void onFailure(Call<UserResponse> call, Throwable t) {
               if (t!=null && t.getMessage()!=null)
               Log.e("Error",t.getMessage());
               profileListInterface.showErrorMessage("Error! retry");
               profileListInterface.stopLoadingProgress();

           }
       });

        //profileListInterface.getCurrentSubscription(subscription);


    }
}
