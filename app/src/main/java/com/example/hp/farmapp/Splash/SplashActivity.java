package com.example.hp.farmapp.Splash;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.hp.farmapp.CalendarPackage.LandingActivity.LandingActivity;
import com.example.hp.farmapp.LangBaseActivity.BaseActivity;
import com.example.hp.farmapp.Login.MainActivity;
import com.example.hp.farmapp.Signup.SetLanguageActivity;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import com.example.hp.farmapp.app.Config;

import java.util.Locale;

public class SplashActivity extends BaseActivity {

    private Thread mThread;
    private int versionCode;
    private boolean isFinish = false;
    //private Context context;
    int t = 0;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    int i;
    final  String TAG="reg id";
    public static final int GET_VERSION_API = 1;
    private String android_id;
    Context context;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    String languageset="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        context=this;
        //languageset=SharedPreferencesMethod.getString(context,SharedPreferencesMethod.LANGUAGE);
        init();
        scheduleAlarm();
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

       /* if (!TextUtils.isEmpty(regId))
            //txtRegId.setText("Firebase Reg Id: " + regId);
        else
       */    // txtRegId.setText("Firebase Reg Id is not received yet!");
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    private void init() {
        context = this;
        android_id = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
        SharedPreferencesMethod.setString(context,SharedPreferencesMethod.DEVICE_ID,android_id);
        //languageset=SharedPreferencesMethod.getString(context,SharedPreferencesMethod.LANGUAGE);
    }


    private Runnable mRunnable = new Runnable() {

        public void run() {




            //SystemClock.sleep(3000);
            mHandler.sendEmptyMessage(0);
        }
    };
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0 && (!isFinish)) {
                stop();

            }
            super.handleMessage(msg);
        }
    };



    @Override
    protected void onDestroy() {
        try {
            mThread.interrupt();
            mThread = null;

        } catch (Exception e) {
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        //SystemClock.sleep(3000);
        //stop();
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                stop();
            }
        }, 2500);

        super.onResume();
    }

    @Override
    protected void onStop() {
        stop();

        super.onStop();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void stop() {
        isFinish = true;
        if (t == 0) {
            //languageset=SharedPreferencesMethod.getString(context,"lang");

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefLang", 0);
            languageset=pref.getString("key_lang","");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {


            //else{
                if (SharedPreferencesMethod.getBoolean(context, "Login")) {
                    Log.e("LoginStatus :","TRUE");
                    Log.e("Language set Data",languageset);
                    
                    if(languageset.equals("")){
                        Intent intent=new Intent(context,SetLanguageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(languageset.equals("English")){
                        Log.e("Language set Data",languageset);
                            changeLang("en");
                            startActivity(new Intent(context, LandingActivity.class));
                            finish();
                        }
                        else if(languageset.equals("Telgu")){
                            Log.e("Language set Data",languageset);
                            changeLang("te");
                            startActivity(new Intent(context, LandingActivity.class));
                            finish();
                        }
                         else{
                        Log.e("Language set Data",languageset);
                        changeLang("hi");
                        startActivity(new Intent(context, LandingActivity.class));
                        finish();
                    }
                    }
                else {
                    Log.e("Language set Data",languageset);
                    if(languageset.equals("")){
                        Intent intent=new Intent(context,SetLanguageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                     else if(languageset.equals("English")){
                            changeLang("en");
                            startActivity(new Intent(context, MainActivity.class));
                            finish();}
                        else if(languageset.equals("Telgu")){
                            changeLang("te");
                            startActivity(new Intent(context, MainActivity.class));
                            finish();
                     }
                     else{
                         changeLang("hi");
                         startActivity(new Intent(context, MainActivity.class));
                         finish();
                     }
                }

            //}

                }
            }, 1300);
        }
    }

    @Override
    public void finish() {
        t = 1;
        super.finish();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //stop();
        }
        return super.onTouchEvent(event);
    }

    private void StartAnimations() {

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash_logo);
        iv.clearAnimation();
        iv.startAnimation(anim);
    }

   /* public void getResponse(JSONObject output, int type) {
        if (type == GET_VERSION_API) {
            try {
                if (output != null) {
                    if (output.getString("RP_MESSAGE").equals("NETWORK_NI_HAI_BHAI") || output.getString("RP_MESSAGE").equals("ERROR")) {
                        Toast.makeText(context, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                    } else {
                        if (output.getString("message").equals("successfully")) {
                            int version = output.getInt("apk_version");
                            try {
                                PackageManager manager = context.getPackageManager();
                                PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
                                String versionName = info.versionName;
                                versionCode = info.versionCode;
                                if (version == versionCode) {
                                    mThread = new Thread(mRunnable);
                                    mThread.start();
                                } else {
                                    // show dialog for update app
                                }

                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(context, getString(R.string.exception), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(context, getString(R.string.exception), Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, getString(R.string.exception), Toast.LENGTH_LONG).show();
            }
        }*/

    private void scheduleAlarm() {
/*
        Intent notifyIntent = new Intent(this,MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (context, NOTIFICATION_REMINDER_NIGHT, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                1000 * 60 * 60 * 24, pendingIntent);*/


/*
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
// Set the alarm to start at 21:32 PM
        Calendar calendar = Calendar.getInstance();
        Log.e("Started","runing......");
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE,14);

// setRepeating() lets you specify a precise custom interval--in this case,
// 1 day
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);
*/

    }


    private void changeLang(String lang) {
        Locale myLocale;
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

    }

}

