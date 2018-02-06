package com.example.hp.farmapp.CalendarPackage.ChatBox;

/**
 * Created by user on 3/2/18.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.farmapp.R;

import java.util.List;

/**
 * Created by user on 2/2/18.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    String countryList[];
    int flags[];
    LayoutInflater inflter;
    List<GetSet> listData;

    public CustomAdapter(Context applicationContext, List<GetSet> listData) {
        this.context = applicationContext;
        this.listData = listData;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        Log.e("checkArray",String.valueOf(listData.size()));

        return listData.size();

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GetSet getSet = listData.get(i);
//        view = inflter.inflate(R.layout.list_item_chat);
        view = inflter.inflate(R.layout.list_item_chat, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.listItemLL);
        TextView comment = (TextView) view.findViewById(R.id.label);
        TextView dateTime = (TextView) view.findViewById(R.id.tvDateTime);
        TextView commentBy = (TextView)view.findViewById(R.id.commentBy);
        ImageView leftImage = (ImageView)view.findViewById(R.id.imageViewLeft);
        ImageView rightImage = (ImageView)view.findViewById(R.id.imageViewRight);

        String commentMsg = getSet.getMessage();
        String date = getSet.getDate();
        String byFarmer = getSet.getByFarmer();
        String byInspector = getSet.getByInspector();
        Log.e("checkArray",byFarmer+" "+byInspector);
        String byAdmin = getSet.getByAdmin();
        if (byFarmer.equals("Y")) {
//            linearLayout.setBackgroundColor(Color.parseColor("#dddddd"));
            linearLayout.setBackgroundResource(R.drawable.rounded_corner);
            commentBy.setVisibility(View.GONE);
            /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(60, 0, 0, 0);
            linearLayout.setLayoutParams(params);*/
//            leftImage.setVisibility(View.GONE);
            leftImage.setVisibility(View.INVISIBLE);
//            linearLayout.setMinimumWidth(350);
        }
        else if(byInspector.equals("Y")){
//            linearLayout.setBackgroundColor(Color.parseColor("#dddddd"));
            linearLayout.setBackgroundResource(R.drawable.rounded_corner_2);
            commentBy.setText("Inspector");
            rightImage.setVisibility(View.GONE);
        }
        else{
//            linearLayout.setBackgroundColor(Color.parseColor("#dddddd"));
            linearLayout.setBackgroundResource(R.drawable.rounded_corner_2);
            commentBy.setText("Admin");
            rightImage.setVisibility(View.GONE);
        }
        comment.setText(commentMsg);
        dateTime.setText(date);
        return view;
    }
}
