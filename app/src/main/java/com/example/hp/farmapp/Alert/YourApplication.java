package com.example.hp.farmapp.Alert;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;

import com.example.hp.farmapp.Notification.MyFirebaseMessagingService;
import com.example.hp.farmapp.Utiltiy.LocaleUtils;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import java.util.Calendar;
import java.util.Locale;

import static com.example.hp.farmapp.LangBaseActivity.BaseActivity.getLocale;

/**
 * Created by hp on 9/12/2017.
 */
public  class YourApplication extends Application implements Application.ActivityLifecycleCallbacks {

    Context context;
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;
    Calendar rightNow;
    int currentHour,currentminitue;
    String lang;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onCreate() {
        context=this;
        super.onCreate();
        setLocale();
      lang= SharedPreferencesMethod.getString(context,"lang");

        if(lang.equals("")){
            LocaleUtils.setLocale(new Locale("en"));
            LocaleUtils.updateConfig(this, getBaseContext().getResources().getConfiguration());
        }
        else if(lang.equals("English")){
            LocaleUtils.setLocale(new Locale("en"));
            LocaleUtils.updateConfig(this, getBaseContext().getResources().getConfiguration());
        }else if(lang.equals("Telgu")){
            LocaleUtils.setLocale(new Locale("te"));
            LocaleUtils.updateConfig(this, getBaseContext().getResources().getConfiguration());
        }else{
            LocaleUtils.setLocale(new Locale("hi"));
            LocaleUtils.updateConfig(this, getBaseContext().getResources().getConfiguration());
        }

        //LocaleUtility.initialize(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
       // startService(new Intent(this, AlarmReceiver.class));

/*

        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
// Set the alarm to start at 21:32 PM
        Calendar calendar = Calendar.getInstance();
        Log.e("Started","runing......");
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,16);
        calendar.set(Calendar.MINUTE,26);


         rightNow = Calendar.getInstance();
         currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
         currentminitue=rightNow.get(Calendar.MINUTE);

// setRepeating() lets you specify a precise custom interval--in this case,
// 1 day
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);

        if(currentHour>16 && currentminitue>30)
        {
            alarmMgr.cancel(alarmIntent);

        }
*/

        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
/*
        if(currentHour>16 && currentminitue>30)
        {
            alarmMgr.cancel(alarmIntent);

        }
*/

    }

    @Override
    public void onActivityResumed(Activity activity) {

/*
        if(currentHour>16 && currentminitue>30)
        {
            alarmMgr.cancel(alarmIntent);

        }
*/
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
/*
        if(currentHour>16 && currentminitue>30)
        {
            alarmMgr.cancel(alarmIntent);

        }
*/
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtils.updateConfig(this, newConfig);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
/*
        if(currentHour>16 && currentminitue>30)
        {
            alarmMgr.cancel(alarmIntent);

        }
*/
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Intent restartService = new Intent(getApplicationContext(), MyFirebaseMessagingService.class);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),1,restartService,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME,5000,pendingIntent);


/*
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
// Set the alarm to start at 21:32 PM
        Calendar calendar = Calendar.getInstance();
        Log.e("Started","runing......");
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,16);
        calendar.set(Calendar.MINUTE,26);


        rightNow = Calendar.getInstance();
        currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        currentminitue=rightNow.get(Calendar.MINUTE);

// setRepeating() lets you specify a precise custom interval--in this case,
// 1 day
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);


        if(currentHour>16 && currentminitue>24)
        {
            alarmMgr.cancel(alarmIntent);

        }
*/
/*
        Intent restartServicecustom = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntentcustom = PendingIntent.getService(getApplicationContext(),1,restartServicecustom,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManagercustom = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManagercustom.set(AlarmManager.ELAPSED_REALTIME,5000,pendingIntentcustom);*/
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setLocale() {

        final Resources resources = getResources();
        final Configuration configuration = resources.getConfiguration();
        final Locale locale = getLocale(this);
        if (!configuration.locale.equals(locale)) {
            configuration.setLocale(locale);
            resources.updateConfiguration(configuration, null);
        }
    }

}