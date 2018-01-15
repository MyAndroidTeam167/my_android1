package com.example.hp.farmapp.Notification;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.farmapp.CalendarPackage.LandingActivity;
import com.example.hp.farmapp.Login.MainActivity;
import com.example.hp.farmapp.TestPackage.Adapter.ProfileAdapter;
import com.example.hp.farmapp.TestPackage.Adapter.RecyclerTouchListener;
import com.example.hp.farmapp.Beans.GetProfile;
import com.example.hp.farmapp.Database.DatabaseHandler;
import com.example.hp.farmapp.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView notirec;
    private ProfileAdapter mAdapter;
    Toolbar mActionBarToolbar;

    String id;
    LinearLayoutManager linearLayoutManager;
    Context context;
   // Runnable refresh;
    Handler  mHandler;
    GetProfile getprofile;
    final List<GetProfile> getProfiles = new ArrayList<>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Intent intent=new Intent(context,LandingActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        context=this;


        TextView title=(TextView)findViewById(R.id.tittle);
        title.setText("Notifications");
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);

        //getSupportActionBar().setTitle("My Title");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        notirec=(RecyclerView)findViewById(R.id.notirecycl);

        mAdapter = new ProfileAdapter(getProfiles);
        notirec.setAdapter(mAdapter);
        linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //notirec.setHasFixedSize(true);
        notirec.setLayoutManager(linearLayoutManager);
        notirec.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        notirec.setItemAnimator(new DefaultItemAnimator());


        notirec.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), notirec, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                GetProfile getProfile = getProfiles.get(position);
                Toast.makeText(getApplicationContext(), getProfile.getActivity() + " is selected!", Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onLongClick(View view, int position) {

            }

        }));
        this.mHandler = new Handler();
        m_Runnable.run();
        mAdapter.notifyDataSetChanged();



        //   prepareProfileData();



        //String[] notification=new String[contacts.size()];
        //String[] id=new String[contacts.size()];

        /*for (GetProfile cn : contacts) {
            String log = "Id: "+cn.get_id()+" ,Notification: " + cn.getNotification();

            id[cn.get_id()]= String.valueOf(cn.get_id());
            notification[cn.get_id()]=cn.getNotification();
            // Writing Contacts to log
            Log.e("Notification in db: ", log);
        }
*/



    }


   /* private void prepareProfileData() {

        DatabaseHandler db = new DatabaseHandler(this);

        List<GetProfile> contacts = db.getAllNotifications();


          for (GetProfile cn : contacts) {
            String log = "Id: "+cn.get_id()+" ,Notification: " + cn.getNotification();
              // Writing Contacts to log

              getProfiles.add(cn.get_id(),cn.getNotification());
            Log.e("Notification in db: ", log);
        }
        //GetProfile getprofile=new GetProfile("New Activity","new Description","2015");
        //getProfiles.add(getprofile);

        //getprofile=new GetProfile("New Activity 2","new Description 2","2016");
        //getProfiles.add(getprofile);

        mAdapter.notifyDataSetChanged();


    }*/


    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
           // Toast.makeText(NotificationActivity.this,"in runnable",Toast.LENGTH_SHORT).show();
            NotificationActivity.this.mHandler.postDelayed(m_Runnable,500000);

            DatabaseHandler db = new DatabaseHandler(context);

            List<GetProfile> contacts = db.getAllNotifications();

            for (GetProfile cn : contacts) {
                String log = "Id: "+cn.get_id()+" ,Notification: " + cn.getNotification();
                id=String.valueOf(cn.get_id());
                // Writing Contacts to log

                getprofile=new GetProfile(cn.getNotification(),id);
                //getprofile.setYear(123);
                getProfiles.add(getprofile);
                Log.e("Notification in db: ", log);
            }

            //GetProfile getprofile=new GetProfile("New Activity","new Description","2015");
            //getProfiles.add(getprofile);

            //getprofile=new GetProfile("New Activity 2","new Description 2","2016");
            //getProfiles.add(getprofile);

        }

    };
}
