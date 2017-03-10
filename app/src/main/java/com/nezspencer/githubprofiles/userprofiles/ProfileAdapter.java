package com.nezspencer.githubprofiles.userprofiles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nezspencer.githubprofiles.MyApplication;
import com.nezspencer.githubprofiles.R;
import com.nezspencer.githubprofiles.api.UserResponseItems;

import java.util.List;

/**
 * Created by nezspencer on 3/5/17.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileHolder> {

    private Context context;
    private List<UserResponseItems> profileList;

    public ProfileAdapter(Context context) {
        this.context=context;
        profileList= MyApplication.ITEMS;
    }

    @Override
    public ProfileHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.profile_list_item,parent,false);
        return new ProfileHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProfileHolder holder, int position) {

        holder.profileData=profileList.get(position);

        Glide.with(context).load(holder.profileData.getAvatar_url())
                .into(holder.userImageThumbnail);

        holder.usernameView.setText(holder.profileData.getLogin());
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileListFragment.showDetailDialog(holder.profileData);
            }
        });
    }

    /**clears all data in the adapter list and notifies adapter*/
    public void clearData(){
        profileList.clear();
        notifyDataSetChanged();
    }
    /**This allows list of data to be added at once to the adapter list*/
    public void addAll(List<UserResponseItems> itemList){
        profileList.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    class ProfileHolder extends RecyclerView.ViewHolder{
        TextView usernameView;
        RelativeLayout itemLayout;
        ImageView userImageThumbnail;
        UserResponseItems profileData;

        public ProfileHolder(View itemView) {
            super(itemView);
            usernameView = (TextView)itemView.findViewById(R.id.profileitem_username);
            userImageThumbnail=(ImageView)itemView.findViewById(R.id.profileitem_image_thumb);
            itemLayout=(RelativeLayout)itemView.findViewById(R.id.profileitem_layout);
        }

    }
}
