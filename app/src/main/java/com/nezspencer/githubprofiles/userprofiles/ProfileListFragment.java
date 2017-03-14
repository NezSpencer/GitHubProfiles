package com.nezspencer.githubprofiles.userprofiles;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nezspencer.githubprofiles.MainActivity;
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
    //private Subscription subscription1;
    private static final String oauTH="19a66c9e6b3c846fce3184ad8f1b168ae5f64f9c";

    private ProfileListPresenter presenter;
    private static ProfileListFragment listFragment;
    private MainActivity mainActivity;
    private LinearLayoutManager linearLayoutManager;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private static int pageToLoad=1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile_list,container,false);

        listFragment=this;
        ButterKnife.bind(this,view);
        presenter=new ProfileListPresenter(this);
        /*oauTH= PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(Constants.KEY_AUTH,"none");*/
        initializeAdapter();
        presenter.fetchUserData("user","lagos","java",oauTH,0);
        return view;
    }

    public void initializeAdapter(){
        profileAdapter=new ProfileAdapter(getActivity());
        linearLayoutManager=new LinearLayoutManager(mainActivity);
        profileRecycler.setNestedScrollingEnabled(true);
        profileRecycler.setLayoutManager(linearLayoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.fetchUserData("user","lagos","java",oauTH,0);
            }
        });

        profileRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {


                visibleItemCount = profileRecycler.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                Log.i("Yaeye!", "still scrolling 1");
                if (dy>0){
                    Log.i("Yaeye!", "still scolling");
                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + visibleThreshold)) {
                        // End has been reached

                        Log.i("Yaeye!", "end called");

                        int page=pageToLoad+1;
                        // Do something
                        presenter.fetchUserData("user","lagos","java",oauTH,page);
                        loading = true;
                    }
                }

            }
        });
        /*scrollListener=new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.fetchUserData("user","lagos","java",oauTH,page);
                Log.e("loading", " more data");
            }
        };

        profileRecycler.addOnScrollListener(scrollListener);*/

        profileRecycler.setAdapter(profileAdapter);

    }

    @Override
    public void refreshAdapter(List<UserResponseItems>items) {
        MyApplication.addItems(items);
        profileAdapter.clearData();
        profileAdapter.addAll(items);

    }

    @Override
    public void addToAdapterList(List<UserResponseItems> items) {
        profileAdapter.addAll(items);
    }

    @Override
    public void showLoadingProgress() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void stopLoadingProgress() {
        refreshLayout.setRefreshing(false);
        loading=false;
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(mainActivity,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadSuccessful() {
        pageToLoad++;
    }

    @Override
    public void getCurrentSubscription(Subscription subscription) {
        //subscription1=subscription;
    }

    @Override
    public void onStop() {
        super.onStop();
       /* if (subscription1 !=null && !subscription1.isUnsubscribed())
            subscription1.unsubscribe();*/
    }

    public static void showDetailDialog(UserResponseItems items){
        DetailDialogFragment.getInstance(items)
                .show(listFragment.getFragmentManager(),"detailDialog");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity=(MainActivity)activity;
    }
}
