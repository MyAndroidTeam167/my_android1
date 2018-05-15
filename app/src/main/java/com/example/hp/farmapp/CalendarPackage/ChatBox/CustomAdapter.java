package com.example.hp.farmapp.CalendarPackage.ChatBox;

/**
 * Created by user on 3/2/18.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.Utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by user on 2/2/18.
 */

public class CustomAdapter extends BaseAdapter implements MediaPlayer.OnCompletionListener{
    Context context;
    String countryList[];
    int flags[];
    LayoutInflater inflter;
    private Handler mHandler = new Handler();
    private Utilities utils;
    int position;
    List<GetSet> listData;
    MediaPlayer[] player;





    public CustomAdapter(Context applicationContext, List<GetSet> listData) {
        this.context = applicationContext;
        this.listData = listData;
        inflter = (LayoutInflater.from(applicationContext));
        MediaPlayer player[]=new MediaPlayer[listData.size()];
        this.player=player;
        for(int i=0;i<listData.size();i++){
            player[i]=new MediaPlayer();
        }

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

    @SuppressLint("NewApi")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        GetSet getSet = listData.get(i);
//        view = inflter.inflate(R.layout.list_item_chat);
        view = inflter.inflate(R.layout.list_item_chat, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.listItemLL);
        final TextView comment = (TextView) view.findViewById(R.id.label);
        TextView dateTime = (TextView) view.findViewById(R.id.tvDateTime);
        TextView commentBy = (TextView)view.findViewById(R.id.commentBy);
        ImageView leftImage = (ImageView)view.findViewById(R.id.imageViewLeft);
        ImageView rightImage = (ImageView)view.findViewById(R.id.imageViewRight);
        ImageView chat_image=(ImageView)view.findViewById(R.id.chat_image);
        final ImageButton play_img=(ImageButton) view.findViewById(R.id.playButtonn);
        LinearLayout card_chat=(LinearLayout) view.findViewById(R.id.card_view_chat);
        RelativeLayout rel_aud_lay=(RelativeLayout)view.findViewById(R.id.audio_rel_lay);
        //Button butt_chat_play=(Button)view.findViewById(R.id.butt_chat_play);
        RelativeLayout audio_rel_lay=(RelativeLayout)view.findViewById(R.id.audio_rel_lay);
      //  final ImageView butt_stop=(ImageView)view.findViewById(R.id.stop_button);
         final TextView songCurrentDurationLabel;
         final TextView songTotalDurationLabel;
        final SeekBar seekBar_player;
        seekBar_player=(SeekBar)view.findViewById(R.id.seek_player);
        songCurrentDurationLabel = (TextView) view.findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) view.findViewById(R.id.songTotalDurationLabel);

        //Button butt_chat_stop=(Button)view.findViewById(R.id.butt_chat_stop);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.addfarm)
                .error(R.drawable.addfarmnew);

        String commentMsg = getSet.getMessage();
        String date = getSet.getDate();
        String byFarmer = getSet.getByFarmer();
        String byInspector = getSet.getByInspector();
        Log.e("checkArray",byFarmer+" "+byInspector);
        String byAdmin = getSet.getByAdmin();
        String msgType=getSet.getMsgType();
        final String audioReply=getSet.getAudioReply();

        if(msgType.equals("1")) {

          // /* final MediaPlayer*/ player = new MediaPlayer[listData.size()];

            if(!audioReply.equals("")&&!audioReply.equals("null")){
                audio_rel_lay.setVisibility(View.VISIBLE);
                Log.e("String Audio Reply :",audioReply);
            }
            //player[i]= new MediaPlayer();
            utils = new Utilities();
            //seekBar_player.setOnSeekBarChangeListener(this); // Important
            player[i].setOnCompletionListener(this); // Important
            player[i].reset();
            Uri uri[] = new Uri[listData.size()];
            uri[i]=Uri.parse(audioReply.trim());
            seekBar_player.setProgress(0);
            seekBar_player.setMax(100);
            //player = new MediaPlayer();
            player[i].setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                player[i].setDataSource(context, uri[i]);
                player[i].prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

             final Runnable mUpdateTimeTask = new Runnable() {
                public void run() {
                    if(player[i]!=null) {
                        try {
                            long totalDuration = player[i].getDuration();
                            long currentDuration = player[i].getCurrentPosition();

                            // Displaying Total Duration time
                            songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
                            // Displaying time completed playing
                            songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));

                            // Updating progress bar
                            int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
                            //Log.d("Progress", ""+progress);
                            seekBar_player.setProgress(progress);

                            // Running this thread after 100 milliseconds
                            mHandler.postDelayed(this, 100);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            };



            if (byFarmer.equals("Y")) {
                linearLayout.setBackgroundResource(R.drawable.rounded_corner);
                commentBy.setVisibility(View.GONE);

                if(audioReply.trim().equals("")){
                }
                else{
                    audio_rel_lay.setVisibility(View.VISIBLE);

                    seekBar_player.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            mHandler.postDelayed(mUpdateTimeTask, 100);

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                            mHandler.removeCallbacks(mUpdateTimeTask);

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            mHandler.removeCallbacks(mUpdateTimeTask);
                            int totalDuration = player[i].getDuration();
                            int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

                            // forward or backward to certain seconds
                            player[i].seekTo(currentPosition);

                            // update timer progress again
                            //updateProgressBar(i);
                            mHandler.postDelayed(mUpdateTimeTask, 100);

                        }
                    });

                    play_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(player[i].isPlaying()){
                                if(player[i]!=null){
                                    player[i].pause();
                                    // Changing button image to play button
                                    play_img.setImageResource(R.drawable.btn_play);
                                }
                            }else{
                                // Resume song
                                if(player[i]!=null){

                                    player[i].start();
                                    //updateProgressBar(i);
                                    mHandler.postDelayed(mUpdateTimeTask, 100);


                                    // Changing button image to pause button
                                    play_img.setImageResource(R.drawable.btn_pause);
                                }
                            }

                        }
                    });


                }
                leftImage.setVisibility(View.INVISIBLE);

            } else if (byInspector.equals("Y")) {
//            linearLayout.setBackgroundColor(Color.parseColor("#dddddd"));
                linearLayout.setBackgroundResource(R.drawable.rounded_corner_2);
                commentBy.setText("Inspector");
                rightImage.setVisibility(View.GONE);
            } else {
//            linearLayout.setBackgroundColor(Color.parseColor("#dddddd"));
                linearLayout.setBackgroundResource(R.drawable.rounded_corner_2);
                commentBy.setText("Admin");
                rightImage.setVisibility(View.GONE);
            }
            comment.setText(commentMsg);
            dateTime.setText(date);
        }else{
            Uri uriprofile = Uri.parse(commentMsg.trim());
            chat_image.setVisibility(View.VISIBLE);
            card_chat.setVisibility(View.VISIBLE);
            Glide.with(context).load(uriprofile).apply(options).into(chat_image);
            comment.setVisibility(View.GONE);
            leftImage.setVisibility(View.INVISIBLE);
            rightImage.setVisibility(View.VISIBLE);

        }

        return view;
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    /*public void updateProgressBar(int i) {
        position=i;
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }*/


}
