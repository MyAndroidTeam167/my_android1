package com.example.hp.farmapp.Notification;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.hp.farmapp.Response.ImageActivity;
import com.example.hp.farmapp.R;

import java.util.Calendar;

/**
 * Created by hp on 9/21/2017.
 */
public class MyTestService extends IntentService {
    private static final int NOTIFICATION_ID = 3;
    Context context;
    // Must create a default constructor
    public MyTestService() {
        // Used to name the worker thread, important only for debugging.
        super("MyTestService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        // if you override onCreate(), make sure to call super().
        // If a Context object is needed, call getApplicationContext() here.
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onHandleIntent(Intent intent) {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);



        Log.e("here","Also in testService");
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("My Titel");
        builder.setContentText("This is the Body");
        builder.setSound(defaultSoundUri);
        builder.setSmallIcon(R.drawable.user);
        Intent notifyIntent = new Intent(this, ImageActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);





        AlarmManager alarmMgr;
        PendingIntent alarmIntent;
        Calendar calendar = Calendar.getInstance();

        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent2 = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent2, 0);


        //alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
        //     AlarmManager.INTERVAL_DAY, alarmIntent);
        alarmMgr.cancel(alarmIntent);

        // This describes what will happen when service is triggered
    }
}