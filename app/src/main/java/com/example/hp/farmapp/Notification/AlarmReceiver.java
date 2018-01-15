package com.example.hp.farmapp.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.hp.farmapp.Notification.MyTestService;
import com.example.hp.farmapp.R;

/**
 * Created by hp on 9/21/2017.
 */
public class AlarmReceiver extends BroadcastReceiver {


    public AlarmReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("coming here","sdas");
        Intent intent1 = new Intent(context, MyTestService.class);
        context.startService(intent1);
       // AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        /*Log.e("running","thisssssssssssssssss");
        // am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        intent = new Intent(context, ImageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setSmallIcon(R.drawable.user)
                .setContentTitle("MY custom notification")
                .setContentText("abho to yahi")
                .setContentInfo("infoo")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentIntent(pendingIntent).build();
        //DataHandler.newInstance().setMessage(message);

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
*/
    }
}