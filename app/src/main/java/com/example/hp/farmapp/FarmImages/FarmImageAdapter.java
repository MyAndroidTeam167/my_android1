package com.example.hp.farmapp.FarmImages;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.GetterSetter.Taskdata;
import com.example.hp.farmapp.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hp on 18-04-2018.
 */

public class FarmImageAdapter extends RecyclerView.Adapter<com.example.hp.farmapp.FarmImages.FarmImageAdapter.ViewHolder> {
    private String[] mDataSet;
    Context context;
    List<FarmImageGetterSetter> farmImages;

    public FarmImageAdapter(List<FarmImageGetterSetter> farmImages, Context context) {
        this.context = context;
        this.farmImages = farmImages;
        Log.d("Data:", "TaskRecyclerAdapter :" + farmImages);
    }

    public com.example.hp.farmapp.FarmImages.FarmImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contents_for_farm_images, parent, false);
        com.example.hp.farmapp.FarmImages.FarmImageAdapter.ViewHolder vh = new com.example.hp.farmapp.FarmImages.FarmImageAdapter.ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(final com.example.hp.farmapp.FarmImages.FarmImageAdapter.ViewHolder holder, int position) {
        FarmImageGetterSetter farm_images = farmImages.get(position);
       /* if (farm_images.getTaskTitle() != null) {
            holder.tvtitle.setText(farm_images.getTaskTitle());
        }*/
      /* if(farm_images.getFarm_image_link()!=null){
           holder.farm_image.setImageResource();
       }*/
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.addfarm)
                .error(R.drawable.addfarmnew);

        if (farm_images.getFarm_image_link() != null) {
            if (farm_images.getFarm_image_link().equals("null")) {
                holder.farm_image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_person_green_24dp));
            } else {
                Uri uriprofile = Uri.parse(farm_images.getFarm_image_link());
                Glide.with(context).load(uriprofile).apply(options).into(holder.farm_image);

            }
        }
    }

    @Override
    public void onBindViewHolder(com.example.hp.farmapp.FarmImages.FarmImageAdapter.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public int getItemCount() {
        return farmImages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView farm_image;

        public ViewHolder(View v) {
            super(v);
            farm_image = (ImageView) v.findViewById(R.id.activity_image);
        }
    }


}
