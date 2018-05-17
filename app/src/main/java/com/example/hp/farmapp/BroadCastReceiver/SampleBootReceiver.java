package com.example.hp.farmapp.BroadCastReceiver;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.hp.farmapp.CalendarPackage.CalendarTask.ShowTaskViewPagerActivity;
import com.example.hp.farmapp.Notification.AlarmReceiver;
import com.example.hp.farmapp.Notification.NotificationActivity;
import com.example.hp.farmapp.R;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by hp on 13-04-2018.
 */

public class SampleBootReceiver extends BroadcastReceiver {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    int MID=0;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Alarm", "Broadcast");
        Intent service1 = new Intent(context, NotificationService1.class);
        service1.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        context.startService(service1);

        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, ShowTaskViewPagerActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification notification;
        Notification/*Compat*/.Builder notificationBuilder = new Notification/*Compat*/.Builder(context);
        notification = notificationBuilder.setSmallIcon(R.drawable.spade_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.image1)).setContentTitle("You Have Pending Notifications")
                .setColor(context.getResources().getColor(R.color.colorPrimaryDark))
                .setContentText("Please Complete them on time").setSound(alarmSound)
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent)
                .setStyle(new Notification.BigTextStyle()
                .bigText("Please Complete them on time to maintain Your Rating")
                                .setSummaryText("to maintain Your Rating")
                )
/*
                .addAction(R.drawable.ic_cloud_circle_mid_grey_24dp,"My Action",pendingIntent)
*/
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000}).build();
        MID++;

        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;;
        notification.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        notification.ledARGB = 0xFFFFA500;
        notification.ledOnMS = 800;
        notification.ledOffMS = 1000;
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(m, notification);
        //notificationManager.cancel(m);
        //notificationManager.cancelAll();


    }

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = 0x008000;
            notificationBuilder.setColor(color);
            return R.drawable.ic_person_green_24dp;

        }
        return R.drawable.ic_person_green_24dp;
    }
}
