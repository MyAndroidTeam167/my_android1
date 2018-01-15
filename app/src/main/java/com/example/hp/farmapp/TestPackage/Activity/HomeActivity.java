package com.example.hp.farmapp.TestPackage.Activity;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.farmapp.Login.MainActivity;
import com.example.hp.farmapp.Beans.GetProfile;
import com.example.hp.farmapp.Database.DatabaseHandler;
import com.example.hp.farmapp.R;
//import com.example.hp.farmapp.Services.LogoutService;
import com.example.hp.farmapp.Response.ImageActivity;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

public class HomeActivity extends AppCompatActivity {
    String useremail,usermobile,userpass,messagefrmsrver,sms;
    Context context;

    TextView usermailtv,usermobtv,userpastv,messagefrmserv,smsserv;
    Button homelogout;
    ListView lv;
    private Timer timer;
    Button custumnoti;
    private ScheduledExecutorService scheduleTaskExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DatabaseHandler db = new DatabaseHandler(this);

/*
        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // Do stuff here!

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Do stuff to update UI here!
                        //Toast.makeText(HomeActivity.this, "Its been 5 seconds", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, ImageActivity.class);
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
                                getSystemService(Context.NOTIFICATION_SERVICE);

                        notificationManager.notify(0, notificationBuilder.build());

                    }
                });

            }
        }, 0, 5, TimeUnit.SECONDS);
*/
        custumnoti=(Button)findViewById(R.id.customnoti);

        custumnoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ImageActivity.class);
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
                        getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0, notificationBuilder.build());


            }
        });


        Log.d("Insert: ", "Inserting ..");

        context=this;
        useremail= SharedPreferencesMethod.getString(context,"Email");
        usermobile= SharedPreferencesMethod.getString(context,"Mobile");
        userpass= SharedPreferencesMethod.getString(context,"Password");

        usermailtv=(TextView)findViewById(R.id.useremail);
        usermobtv=(TextView)findViewById(R.id.usermobile);
        userpastv=(TextView)findViewById(R.id.userpass);
        messagefrmserv=(TextView)findViewById(R.id.messagefrmsrv);
        homelogout=(Button)findViewById(R.id.homelogout);
        smsserv=(TextView)findViewById(R.id.smsreciever);

        usermailtv.setText(useremail);
        usermobtv.setText(usermobile);
        userpastv.setText(userpass);

        sms=SharedPreferencesMethod.getString(context,"sms");
        int j=SharedPreferencesMethod.getInt(context,"count");

        String[] msgsave =new String[j];
       // db.addNotification(new GetProfile(SharedPreferencesMethod.getString(context,"message"+j)));


        Log.d("Reading: ", "Reading all contacts..");



        List<GetProfile> contacts = db.getAllNotifications();
        for (GetProfile cn : contacts) {
            String log = "Id: "+cn.get_id()+" ,Notification: " + cn.getNotification();
            // Writing Contacts to log
            Log.e("Notification in db: ", log);
        }

        String finalmsg;

        final ArrayList<String> list = new ArrayList<String>();

        for(int i=0;i<j;i++) {
    msgsave[i] = SharedPreferencesMethod.getString(context, "message" + Integer.toString(i));
            list.add(msgsave[i]);
}

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);

        lv=(ListView)findViewById(R.id.lv);

        lv.setAdapter(adapter);





        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });



    smsserv.setText(Integer.toString(j));

        finalmsg=SharedPreferencesMethod.getString(context,"message" + Integer.toString(j));
        messagefrmserv.setText(finalmsg);



        homelogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesMethod.clear(context);
                SharedPreferencesMethod.setBoolean(context,"Login",false);
                Intent intent=new Intent(context,MainActivity.class);
                startActivity(intent);
            }
        });



    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
                //Do whatever you want with the code here
            }
        }
    };



    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
        /*if (timer != null) {
            timer.cancel();
            Log.i("Main", "cancel timer");
            timer = null;
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
       /* timer = new Timer();
        Log.i("Main", "Invoking logout timer");
        LogOutTimerTask logoutTimeTask = new LogOutTimerTask();
        timer.schedule(logoutTimeTask, 10000);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);*/
    }



    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }



        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }


    private class LogOutTimerTask extends TimerTask {

        @Override
        public void run() {

            //redirect user to login screen
            SharedPreferencesMethod.clear(context);
            SharedPreferencesMethod.setBoolean(context,"Login",false);
            Intent i = new Intent(HomeActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }
}
