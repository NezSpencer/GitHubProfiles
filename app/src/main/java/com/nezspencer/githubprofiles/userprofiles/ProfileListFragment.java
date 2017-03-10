package com.nezspencer.githubprofiles.userprofiles;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nezspencer.githubprofiles.Constants;
import com.nezspencer.githubprofiles.MyApplication;
import com.nezspencer.githubprofiles.R;
import com.nezspencer.githubprofiles.api.UserResponseItems;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * The list of user profiles are populated in this fragment
 *
 * Created by nezspencer on 3/5/17.
 */

public class ProfileListFragment extends Fragment implements InterfaceProfileList {

    @Bind(R.id.profile_recycler) RecyclerView profileRecycler;
    @Bind(R.id.profilelist_refresh)SwipeRefreshLayout refreshLayout;
    private ProfileAdapter profileAdapter;
    private Subscription subscription1;
    private String oauTH;

    private ProfileListPresenter presenter;
    private static ProfileListFragment listFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile_list,container,false);

        listFragment=this;
        ButterKnife.bind(this,view);
        presenter=new ProfileListPresenter(this);
        oauTH=PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(Constants.KEY_AUTH,"none");
        initializeAdapter();
        presenter.fetchUserData("user","lagos","java",oauTH);
        return view;
    }

    public void initializeAdapter(){
        profileAdapter=new ProfileAdapter(getActivity());
        profileRecycler.setAdapter(profileAdapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.fetchUserData("user","lagos","java",oauTH);
            }
        });

    }

    @Override
    public void refreshAdapter(List<UserResponseItems>items) {
        MyApplication.addItems(items);
        profileAdapter.clearData();
        profileAdapter.addAll(items);

    }

    @Override
    public void showLoadingProgress() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void stopLoadingProgress() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCurrentSubscription(Subscription subscription) {
        subscription1=subscription;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (subscription1 !=null && !subscription1.isUnsubscribed())
            subscription1.unsubscribe();
    }

    public static void showDetailDialog(UserResponseItems items){
        DetailDialogFragment.getInstance(items)
                .show(listFragment.getFragmentManager(),"detailDialog");
    }
}
