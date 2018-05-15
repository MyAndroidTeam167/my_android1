package com.example.hp.farmapp.CalendarPackage.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hp.farmapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by hp on 09-04-2018.
 */

public class SliderPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<Bitmap> image_arraylist;

    public SliderPagerAdapter(Activity activity, ArrayList<Bitmap> image_arraylist) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.layout_slider, container, false);
        ImageView im_slider = (ImageView) view.findViewById(R.id.im_slider);
        im_slider.setImageBitmap(image_arraylist.get(position));
       /* Picasso.with(activity.getApplicationContext())
                .load(String.valueOf(image_arraylist.get(position)))
                *//*.placeholder(R.mipmap.)*//* // optional
                *//*.error(R.mipmap.ic_launcher)*//*         // optional
                .into(im_slider);*/
                Log.e("Slider","coming here");


        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}