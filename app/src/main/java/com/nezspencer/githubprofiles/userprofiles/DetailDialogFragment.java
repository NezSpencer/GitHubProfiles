package com.nezspencer.githubprofiles.userprofiles;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nezspencer.githubprofiles.R;
import com.nezspencer.githubprofiles.api.UserResponseItems;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nezspencer on 3/9/17.
 */

public class DetailDialogFragment extends AppCompatDialogFragment {

    @Bind(R.id.dialog_image_profile)ImageView userImage;
    @Bind(R.id.dialog_button_share)Button shareButton;
    @Bind(R.id.dialog_profile_link)TextView profileLink;
    @Bind(R.id.dialog_text_username)TextView username;

    private static UserResponseItems profileData;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


    public static DetailDialogFragment getInstance(UserResponseItems data)
    {
        profileData=data;
        return new DetailDialogFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_profile_detail,container,false);
        ButterKnife.bind(this,view);
        setProfileData();
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setCancelable(true);
        return view;
    }

    public void setProfileData(){
        Glide.with(getActivity()).load(profileData.getAvatar_url())
                .into(userImage);

        profileLink.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Medium.ttf"));
        username.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Medium.ttf"));

        profileLink.setText(profileData.getUrl());
        username.setText(profileData.getLogin());
        shareButton.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Medium.ttf"));
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent=new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,"Check out this awesome developer " +
                        "@"+profileData.getLogin()+", "+profileData.getUrl());
                startActivityForResult(shareIntent,9);
            }
        });
    }


}
