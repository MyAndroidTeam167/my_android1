package com.example.hp.farmapp.TestPackage.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.farmapp.Beans.Profilebeans;
import com.example.hp.farmapp.R;

import java.util.List;

/**
 * Created by hp on 10/29/2017.
 */
public class FarmCalAdapter extends RecyclerView.Adapter<FarmCalAdapter.MyViewHolder> {

    private Context context;
    private List<Profilebeans> listfarmcal;
    private static final String TAG = "ProfileActivity";



    public FarmCalAdapter(List<Profilebeans> listfarmcal, Context context) {
        this.context = context;
        this.listfarmcal = listfarmcal;
        Log.d(TAG, "TimeLineAdapter: "+listfarmcal);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profilefarmcalcontents,null);
        MyViewHolder layoutViewHolder=new MyViewHolder(v);
        return layoutViewHolder;    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Profilebeans listfarmcalcontent=listfarmcal.get(position);

        if(listfarmcalcontent.getDatefarmcal()!=null){
            holder.dateprofile.setText(listfarmcalcontent.getDatefarmcal());
        }

        if(listfarmcalcontent.getActivityfarmcal()!=null){
            holder.activityprofile.setText(listfarmcalcontent.getActivityfarmcal());
        }
        if(listfarmcalcontent.getActivitydescriptionfarmcal()!=null){
            holder.activitydescriptionprofile.setText(listfarmcalcontent.getActivitydescriptionfarmcal());
        }

    }

    @Override
    public int getItemCount() {
        return listfarmcal.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView dateprofile,activityprofile,activitydescriptionprofile;

        public MyViewHolder(View itemView) {
            super(itemView);
            dateprofile=(TextView)itemView.findViewById(R.id.dateforfarmcalcontent);
            activityprofile=(TextView)itemView.findViewById(R.id.activityfarmcalcontent);
            activitydescriptionprofile=(TextView)itemView.findViewById(R.id.activitydescriptionfarmcalcontent);
        }
    }
}
