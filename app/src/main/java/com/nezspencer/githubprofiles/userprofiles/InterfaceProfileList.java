package com.nezspencer.githubprofiles.userprofiles;

import com.nezspencer.githubprofiles.api.UserResponseItems;

import java.util.List;

import rx.Subscription;

/**
 * Created by nezspencer on 3/9/17.
 */

public interface InterfaceProfileList {

    void refreshAdapter(List<UserResponseItems> items);
    void addToAdapterList(List<UserResponseItems> items);
    void showLoadingProgress();
    void stopLoadingProgress();
    void showErrorMessage(String message);
    void onLoadSuccessful();
    void getCurrentSubscription(Subscription subscription);
}
