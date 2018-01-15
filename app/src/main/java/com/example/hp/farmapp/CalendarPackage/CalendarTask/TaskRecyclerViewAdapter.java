package com.example.hp.farmapp.CalendarPackage.CalendarTask;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.hp.farmapp.CalendarPackage.CalendarTask.GetterSetter.Taskdata;
import com.example.hp.farmapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 15/12/17.
 */

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>  {
    private String[] mDataSet;
    Context context;
    List<Taskdata> taskdatum;

    public TaskRecyclerViewAdapter(List<Taskdata> taskdatum,Context context){
        this.context=context;
        this.taskdatum=taskdatum;
        Log.d("Data:","TaskRecyclerAdapter :"+taskdatum);
    }

    public TaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_single_text_view,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    public void onBindViewHolder(final ViewHolder holder, int position){
        Taskdata taskdata=taskdatum.get(position);
        if(taskdata.getTaskTitle()!=null){
            holder.tvtitle.setText(taskdata.getTaskTitle());
        }


        if(taskdata.getImgBgLink()!=null){
            String image_link = taskdata.getImgBgLink();
            String isDone = taskdata.getIsDone();
            Log.e("isDone",isDone);
            String taskDate = taskdata.getTaskDate();
            String date_today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            if(isDone.equals("Y")){
                holder.taskInnerLinearLayout.setBackgroundColor(Color.parseColor("#3CB371"));
            }else{
                if(taskDate.compareTo(date_today)<0){
                    holder.taskInnerLinearLayout.setBackgroundColor(Color.parseColor("#FF6347"));
                }else{
                    holder.taskInnerLinearLayout.setBackgroundColor(Color.parseColor("#79CDCD"));
                }
            }
            Uri uri = Uri.parse(image_link);
//            Context context = holder.task_recycler_single_view_relative.getContext();
//            Picasso.with(context).load(uri).into(holder.taskLinearLayout);



            Picasso.with(holder.imageView.getContext()).load(uri).into(holder.imageView);
           // holder.imageView.setAlpha(200);
           /* Picasso.with(context).load(uri).into(new Target() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    holder.task_recycler_single_view_relative.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });*/
//            ImageView imageView;
//            imageView = null;

//            Drawable drawable =imageView.getDrawable();
//            holder.taskLinearLayout.setBackground(drawable);
        }
        if(taskdata.getTaskDate()!=null){
            holder.tvdate.setText(taskdata.getTaskDate());
        }
        if(taskdata.getTaskDescription()!=null){
            holder.tvdescripion.setText(taskdata.getTaskDescription());
        }
        if(taskdata.getTaskId()!=null){

        }
        if(taskdata.getTaskStatus()!=null){

        }



       // holder.mtextView.setText(mDataSet[position]);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public int getItemCount(){
        return taskdatum.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvtitle,tvdescripion,tvdate;
        public ToggleButton mtoggleButton;
        public LinearLayout taskLinearLayout,taskInnerLinearLayout;
        public RelativeLayout task_recycler_single_view_relative;
        public ImageView imageView;


        public ViewHolder(View v){
            super(v);
            tvtitle = (TextView)v.findViewById(R.id.task_title);
            tvdescripion=(TextView)v.findViewById(R.id.task_description);
            tvdate=(TextView)v.findViewById(R.id.task_date);

            taskInnerLinearLayout = (LinearLayout)v.findViewById(R.id.recycler_inner_linear);
//            taskLinearLayout=(LinearLayout)v.findViewById(R.id.task_linear_layout);
            //task_recycler_single_view_relative=(RelativeLayout)v.findViewById(R.id.recycler_single_view_relative);
            imageView=(ImageView)v.findViewById(R.id.recycler_single_view_relative);

           /* mtoggleButton = (ToggleButton)v.findViewById(R.id.toggleButton2);
            mtoggleButton.setText("Task Not Done");
            mtoggleButton.setTextOff("Task Not Done");
            mtoggleButton.setTextOn("Task is Done");*/

        }
    }


}
