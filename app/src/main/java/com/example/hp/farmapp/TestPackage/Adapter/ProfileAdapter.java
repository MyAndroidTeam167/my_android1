package com.example.hp.farmapp.TestPackage.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.farmapp.R;

import java.util.List;

import com.example.hp.farmapp.Beans.GetProfile;

/**
 * Created by hp on 9/13/2017.
 */
public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {

    private List<GetProfile> getProfiles;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title, idfornoti;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            //genre = (TextView) view.findViewById(R.id.genre);
            idfornoti = (TextView) view.findViewById(R.id.idfornoti);
        }

    }

    public ProfileAdapter(List<GetProfile> getProfiles)
    {this.getProfiles=getProfiles;}

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_list_row, parent, false);
        MyViewHolder layoutViewHolder=new MyViewHolder(itemView);

return layoutViewHolder;    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GetProfile getProfile = getProfiles.get(position);
        holder.title.setText(getProfile.getActivity());
        //holder.genre.setText(getProfile.getActivitydescription());
        holder.idfornoti.setText("");
    }

    @Override
    public int getItemCount() {
        return getProfiles.size();
    }




}
