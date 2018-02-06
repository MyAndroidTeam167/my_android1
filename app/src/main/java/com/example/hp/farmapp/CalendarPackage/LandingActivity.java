package com.example.hp.farmapp.CalendarPackage;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.Adapter.SpinnerAdapter;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.ShowTaskActivity;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.ShowTaskViewPagerActivity;
import com.example.hp.farmapp.CalendarPackage.NavGetterSetter.SpinnerData;
import com.example.hp.farmapp.DataHandler.DataHandler;
import com.example.hp.farmapp.FarmData.FarmAddActivity;
import com.example.hp.farmapp.FarmData.FarmContractActivity;
import com.example.hp.farmapp.FarmData.FarmPackage.FarmImagesActivity;
import com.example.hp.farmapp.FarmData.FarmSoilTestActivity;
import com.example.hp.farmapp.FarmData.ShowFarmActivity;
import com.example.hp.farmapp.Login.MainActivity;
import com.example.hp.farmapp.PersonData.ShowProfileActivity;
import com.example.hp.farmapp.Notification.NotificationActivity;
import com.example.hp.farmapp.R;

import com.example.hp.farmapp.Response.ImageActivity;
import com.example.hp.farmapp.Settings.SettingActivity;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;
import com.example.hp.farmapp.Weather.WeatherViewPagerActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemSelectedListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

//    private ProfileAdapter mAdapter;
//    String Responsee;
    //private List<GetProfile> getProfiles = new ArrayList<>();
    Context context;
//    private static final String REGISTER_URL = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmCalendar/send_farm_calendar_data_to_app";
//    public static final String KEY_FARM_NUM = "farm_num";
//    String farmnum="10";
//    String[] datefarmcalarray,activityfarmcal,activitydescfarmcal;
private static final String REGISTER_URL_ALL = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmCalendar/send_farm_calendar_column_data_to_app";
    private static final String FETCH_FARM_COUNT = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmApp/count_farm_num";
    private static final String FETCH_FARM_NUM_NAME = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmApp/fetch_farm_num_and_name";
    private static final String FETCH_CROP_DETAILS = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmCalendar/send_farm_crop_details";


//    FarmCalAdapter farmCalAdapter;
    RecyclerView mrecyclerView;
    int pStatus = 0;
    private Handler handler = new Handler();
    int spinner_positionn=0;
    String farmnum="1";
    TextView tv;
    String urlsend;
//    private List<Profilebeans> farmcal;
public final String KEY_PERSON_NUM="person_num";
    public final String KEY_FARM_NUM="farm_num";

    private String person_num,no_of_farms;
    private LinearLayoutManager mLayoutManager;
    LinearLayout linearLayout;
    //LocationRequest mLocationRequest;
    GoogleApiClient googleApiClient;
    TextView Addfarm;
//    public GregorianCalendar cal_month, cal_month_copy;
//    private CalendarAdapter cal_adapter;
//    private TextView tv_month;

    Boolean exit = false;
    private Spinner spinner;
    private  TextView textView;
    List<SpinnerData> SpinnerDatums = new ArrayList<>();

    View addanimation;

//    List<SpinnerData> resFetched = new ArrayList<>();

    SpinnerData spinnerDatum = new SpinnerData();
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);*/
        finish();
        return super.onOptionsItemSelected(item);
    }




    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView title=(TextView)findViewById(R.id.tittlelanding);



        title.setText(R.string.title_activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        //toolbar.setTitle("Kumar");
        setSupportActionBar(toolbar);
        context=this;

        spinner_positionn=SharedPreferencesMethod.getInt(context,"spinner_postion")-1;


        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(LandingActivity.this).build();
            googleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            // **************************
            builder.setAlwaysShow(true); // this is the key ingredient
            // **************************

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can
                            // initialize location
                            // requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be
                            // fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling
                                // startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(LandingActivity.this, 1000);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have
                            // no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });
        }

        /*ImageView imageView=(ImageView)findViewById(R.id.bck_land_img);
        Drawable d = imageView.getBackground();
        d.setAlpha(185);
        imageView.setBackground(d);*/



        if (getSupportActionBar() != null){
            /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);*/
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
   /*     final float[] from = new float[3],
                to =   new float[3];

        Color.colorToHSV(Color.parseColor("#B22222"), from);   // from white
        Color.colorToHSV(Color.parseColor("#0000FF"), to);     // to red

        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);   // animate from 0 to 1
        anim.setDuration(300);                              // for 300 ms

        final float[] hsv  = new float[3];                  // transition color
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                // Transition along each axis of HSV (hue, saturation, value)
                hsv[0] = from[0] + (to[0] - from[0])*animation.getAnimatedFraction();
                hsv[1] = from[1] + (to[1] - from[1])*animation.getAnimatedFraction();
                hsv[2] = from[2] + (to[2] - from[2])*animation.getAnimatedFraction();

                addanimation.setBackgroundColor(Color.HSVToColor(hsv));
            }
        });

        anim.start();*/
/*
        int colorFrom = getResources().getColor(R.color.colorPrimaryDark);
        int colorTo = getResources().getColor(R.color.colorAccent);

        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(colorFrom, colorTo);
        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                addanimation.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
            }
        });
        anim.setDuration(300);
        anim.start();
*/
       /* int colorFrom = getResources().getColor(R.color.colorPrimaryDark);
        int colorTo = getResources().getColor(R.color.colorAccent);
        animateBetweenColors(addanimation,colorFrom,colorTo,200);*/

        /*final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(addanimation,
                "backgroundColor",
                new ArgbEvaluator(),
                0xFFFFFFFF,
                0xff78c5f9);
        backgroundColorAnimator.setDuration(300);
        backgroundColorAnimator.start();
*/





        // sum=sum/totalcount;



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       navigationView.setItemIconTintList(null);


        View header = navigationView.getHeaderView(0);


        //spinner = (Spinner)navigationView.inflateHeaderView(R.layout.nav_header_profile).findViewById(R.id.spinnernavheaderr);
        spinner=(Spinner)findViewById(R.id.landing_spinner);
        spinner.setOnItemSelectedListener(this);

      /*  Addfarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FarmAddActivity.class);
                startActivity(intent);
                finish();
            }
        });*/


       /* ImageView text = (ImageView) header.findViewById(R.id.textView);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHandler.newInstance().setFromActivty("landingactivity");
                Intent intent=new Intent(context,FarmAddActivity.class);
                startActivity(intent);
                finish();
            }
        });
*/
        person_num = SharedPreferencesMethod.getString(context,"person_num");
        Log.e("PERSON_NUM",person_num+"kumar");
       // getFarmCount();
        getFarmNumName();

    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id){
        SpinnerData spinDta = new SpinnerData();
        spinDta = SpinnerDatums.get(position);
        SharedPreferencesMethod.setString(context,"farm_num",spinDta.getItem_value());
        if(spinDta.getItem_name().equals("Add New Farm")){
            DataHandler.newInstance().setFromActivty("landingactivity");
            Intent intent=new Intent(context,FarmAddActivity.class);
            startActivity(intent);
            finish();
        }else {
            urlsend = REGISTER_URL_ALL;
            AsyncTaskRunner runnerall = new AsyncTaskRunner();
            runnerall.execute(urlsend, "fetchall",farmnum);
            urlsend = FETCH_CROP_DETAILS;
            farmnum=spinDta.getItem_value();
            AsyncTaskRunner runnercrop = new AsyncTaskRunner();
            runnercrop.execute(urlsend, "fetchcrop",farmnum);
            SharedPreferencesMethod.setInt(context,"spinner_postion",position);

            Toast.makeText(getApplicationContext(), spinDta.getItem_name() + "   " + spinDta.getItem_value(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void getFarmCount(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, FETCH_FARM_COUNT,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            no_of_farms = response;
//                            Toast.makeText(context,"No. of farms are"+no_of_farms,Toast.LENGTH_LONG).show();
                            Log.e("check","No of farms are "+response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log.e(TAG,error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_PERSON_NUM, person_num);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void getFarmNumName(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, FETCH_FARM_NUM_NAME,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonarray = null;
                            jsonarray = new JSONArray(response);

                            Log.e("RESPONSE :",response);
                            for(int i=0;i<jsonarray.length();i++){
                                JSONObject jsonObject= jsonarray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String farm_num = jsonObject.getString("farm_num");
                                String farm_name = jsonObject.getString("farm_pet_name");
                                spinnerDatum = new SpinnerData();
                                spinnerDatum.setItem_name(farm_name);
                                spinnerDatum.setItem_value(farm_num);
                                SpinnerDatums.add(spinnerDatum);
                                Log.e("check","Farm Name = "+spinnerDatum.getItem_name()+farm_name);
                            }
                            Log.e("check"," "+SpinnerDatums.size());

                            spinnerDatum = new SpinnerData();
                            spinnerDatum.setItem_name("Add New Farm");
                            spinnerDatum.setItem_value("0");
                            SpinnerDatums.add(spinnerDatum);

                            Log.e("check"," "+SpinnerDatums.size());
                            SpinnerAdapter spinnerAdapter=new SpinnerAdapter(getApplicationContext(),SpinnerDatums);


                            spinner.setAdapter(spinnerAdapter);
                            spinner.setSelection(spinner_positionn);

//                            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(SpinnerDatums,android.R.layout.simple_spinner_dropdown_item,context);

                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log.e(TAG,error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_PERSON_NUM, person_num);
                Log.e("PERWON NUM :",person_num);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

//        return SpinnerDatums;

    }


    private void init() {

       // AsyncTaskRunner runner = new AsyncTaskRunner();
        //runner.execute(farmnum);
//        prepareData();
//        mrecyclerView=(RecyclerView)findViewById(R.id.profilefarmcal);
//        mrecyclerView.hasFixedSize();

       // farmCalAdapter.notifyDataSetChanged();
        mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mrecyclerView.setLayoutManager(mLayoutManager);

//        farmCalAdapter=new FarmCalAdapter(farmcal,context);
//        mrecyclerView.setAdapter(farmCalAdapter);

     /*   StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonarray = null;
                        Profilebeans profilbeans;
                        farmcal=new ArrayList<>();
                        try {
                            jsonarray = new JSONArray(response);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String date = jsonobject.getString("date");
                                Log.e("Date :",date);
                                profilbeans=new Profilebeans();
                                profilbeans.setDatefarmcal(date);
                                profilbeans.setActivityfarmcal(date);
                                profilbeans.setActivitydescriptionfarmcal(date);
                                farmcal.add(profilbeans);
                                // String url = jsonobject.getString("url");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log.e(TAG,error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_FARM_NUM, farmnum);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        //AsyncTaskRunner runner = new AsyncTaskRunner();
        //runner.execute(farmnum);
      // prepareData();

      //  context=this;
*/

    }

//    private void prepareData() {
//        farmcal = new ArrayList<>();
//        Profilebeans profilbeans;
//        datefarmcalarray=DataHandler.newInstance().getDatefarmcal();
//        activityfarmcal=DataHandler.newInstance().getActivityfarmcal();
//        activitydescfarmcal=DataHandler.newInstance().getActivitydescriptionfarmcal();
//        if(datefarmcalarray!=null) {
//            for (int i = 0; i < datefarmcalarray.length; i++) {
//                profilbeans = new Profilebeans();
//                profilbeans.setDatefarmcal(datefarmcalarray[i]);
//                profilbeans.setActivityfarmcal(activityfarmcal[i]);
//                profilbeans.setActivitydescriptionfarmcal(activitydescfarmcal[i]);
//                farmcal.add(profilbeans);
//            }
//        }

        //Toast.makeText(ProfileActivity.this, "*"+activitydescfarmcal[0]+"*", Toast.LENGTH_SHORT).show();

//    }

       /* profilbeans=new Profilebeans();
        profilbeans.setDatefarmcal(profilbeans.getDatefarmcal());
        profilbeans.setActivityfarmcal(profilbeans.getDatefarmcal());
        profilbeans.setActivitydescriptionfarmcal(profilbeans.getDatefarmcal());
        farmcal.add(profilbeans);

        profilbeans=new Profilebeans();
        profilbeans.setDatefarmcal("12/10/12");
        profilbeans.setActivityfarmcal("new Actiivty");
        profilbeans.setActivitydescriptionfarmcal("new Activity Description");
        farmcal.add(profilbeans);
    }*/

    /*  private void prepareProfileData() {

          GetProfile getprofile=new GetProfile("New Activity","new Description","2015");
          getProfiles.add(getprofile);

          getprofile=new GetProfile("New Activity 2","new Description 2","2016");
          getProfiles.add(getprofile);

          mAdapter.notifyDataSetChanged();


      }
  */
    @Override
    public void onBackPressed() {
        //exit=false;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();

                if (exit) {
                    finish(); // finish activity
                } else {
                    Toast.makeText(this, "Press Back again to Exit.",
                            Toast.LENGTH_SHORT).show();
                    exit = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            exit = false;
                        }
                    }, 3 * 1000);

                }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


       /*if (id == R.id.tvaddnewfarm) {
            Intent intent=new Intent(context,DisplayCalendarActivity.class);
            startActivity(intent);
            finish();
            // Handle the camera action
        }*/
        if (id == R.id.nav_calendar) {
            Intent intent=new Intent(context,DisplayCalendarActivity.class);
            startActivity(intent);
            // Handle the camera action
        }
        /*else if (id == R.id.nav_completed_activities) {
            Intent intent=new Intent(context,ShowTaskActivity.class);
            intent.putExtra("Type","all_activities");
            startActivity(intent);

        }*/
        else if(id==R.id.nav_my_activities){
            Intent intent= new Intent(context, ShowTaskViewPagerActivity.class);
            intent.putExtra("Type","all_activities");
            startActivity(intent);
        }
        else if (id == R.id.nav_notification) {
            Intent intent=new Intent(context,NotificationActivity.class);
            startActivity(intent);

        }

       /* else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            startActivity(Intent.createChooser(sharingIntent, "Share With"));
            return true;
        }*/

        else if (id == R.id.nav_settings) {
            Intent intent=new Intent(context,SettingActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_soil_test){

            Intent intent=new Intent(context,FarmSoilTestActivity.class);
            startActivity(intent);

        }else if(id==R.id.nav_my_profile){
            Intent intent=new Intent(context,ShowProfileActivity.class);
            startActivity(intent);


        }else if(id==R.id.nav_my_farm){
            Intent intent=new Intent(context,ShowFarmActivity.class);
            startActivity(intent);

        }
        else if(id==R.id.nav_farm_images){
            Intent intent=new Intent(context,FarmImagesActivity.class);
            startActivity(intent);

        }else if(id==R.id.nav_weather_report){
            Intent intent=new Intent(context,WeatherViewPagerActivity.class);
            startActivity(intent);

        }
       /* else if (id == R.id.nav_logout) {
            SharedPreferencesMethod.clear(context);
            SharedPreferencesMethod.setBoolean(context,"Login",false);
            Intent intent=new Intent(context,MainActivity.class);
            startActivity(intent);
            finish();

        }*/
        else if(id==R.id.nav_farm_contract){
            Intent intent=new Intent(context,FarmContractActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }






   /* private class AsyncTaskRunner extends AsyncTask<String, Void, String> {
        public AsyncTaskRunner() {
            super();
        }


        @Override
        protected String doInBackground(String... params) {

            params[0]=farmnum;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONArray jsonarray = null;
                            Profilebeans profilbeans;
                            farmcal=new ArrayList<>();
                            try {
                                jsonarray = new JSONArray(response);
                                datefarmcalarray=new String[jsonarray.length()];
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String date = jsonobject.getString("date");
                                    datefarmcalarray[i]=date;
                                    Log.e("Date :",date);
                                   *//* profilbeans=new Profilebeans();
                                    profilbeans.setDatefarmcal(date);
                                    profilbeans.setActivityfarmcal(date);
                                    profilbeans.setActivitydescriptionfarmcal(date);
                                    farmcal.add(profilbeans);*//*
                                    // String url = jsonobject.getString("url");
                                }

                                DataHandler.newInstance().setDatefarmcal(datefarmcalarray);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Log.e(TAG,error.toString());
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put(KEY_FARM_NUM, farmnum);


                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
            return Responsee;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {




            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }*/
/*
        context=this;
        mrecyclerView=(RecyclerView)findViewById(R.id.profilefarmcal);
        farmCalAdapter=new FarmCalAdapter(farmcal,context);
        mrecyclerView.setAdapter(farmCalAdapter);
        farmCalAdapter.notifyDataSetChanged();
        mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mrecyclerView.setLayoutManager(mLayoutManager);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        JSONArray jsonarray = null;
                        Profilebeans profilbeans;
                        farmcal=new ArrayList<>();
                        try {
                            jsonarray = new JSONArray(response);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String date = jsonobject.getString("date");
                                Log.e("Date :",date);
                                profilbeans=new Profilebeans();
                                profilbeans.setDatefarmcal(date);
                                profilbeans.setActivityfarmcal(date);
                                profilbeans.setActivitydescriptionfarmcal(date);
                                farmcal.add(profilbeans);
                               // String url = jsonobject.getString("url");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log.e(TAG,error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_FARM_NUM, farmnum);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/


    /*protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1),
                    cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }

    }*/



    /*protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1),
                    cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) - 1);
        }

    }*/

    /*public void refreshCalendar() {
        cal_adapter.refreshDays();
        cal_adapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }
*/


    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        public AsyncTaskRunner() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            final String urlonrecieve,flagonrecieve;
            urlonrecieve=params[0];
            flagonrecieve=params[1];



            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlonrecieve,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            if(flagonrecieve.equals("fetchall")) {
                                JSONArray jsonarray = null;
                                try {
                                    final int[] fullfill = new int[1];

                                    int completedcount=0,pendingcount=0,totalcount=0,sum=0;

                                    jsonarray = new JSONArray(response);
                                    TextView tvtask_completed_num = (TextView) findViewById(R.id.task_completed_no_);
                                    TextView tvtask_pending_num = (TextView) findViewById(R.id.task_pending_no_);
                                    TextView tv_ranking = (TextView) findViewById(R.id.tv);


                                /*farmcalid=new String[jsonarray.length()];
                                farmcalactivity=new String[jsonarray.length()];
                                farmcaldate=new String[jsonarray.length()];
                                farmcaldescription=new String[jsonarray.length()];
                                farmcalimglink = new String[jsonarray.length()];
                                farmcalisdone = new String[jsonarray.length()];*/
                               /* taskdatums=new ArrayList<>();
                                Taskdata taskdatum=new Taskdata();*/
                                    CalendarCollection.date_collection_arr = new ArrayList<CalendarCollection>();

                                    totalcount = jsonarray.length();
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        String id = jsonobject.getString("id");
                                        String activity = jsonobject.getString("activity");
                                        String activitydescription = jsonobject.getString("activity_description");
                                        String date = jsonobject.getString("date");
                                        String activity_img = jsonobject.getString("img_link");
                                        String is_done = jsonobject.getString("is_done");
                                        String rating_factor = jsonobject.getString("rating_effective_fact");
                                        String date_today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                                        sum = Integer.parseInt(rating_factor) + sum;
                                        //Toast.makeText(context, Integer.parseInt(rating_factor), Toast.LENGTH_SHORT).show();


                                        if (is_done.equals("Y")) {
                                            completedcount++;
                                        } else {
                                            if (date.compareTo(date_today) < 0) {
                                                pendingcount++;
                                            }
                                        }


                                        CalendarCollection.date_collection_arr.add(new CalendarCollection(date, activity));
                                   /* CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-10","Basel dose"));
                                    CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-15","Herbicide spray"));
                                    CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-21","weed removel (if needed)"));
                                    CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-21","3rd Dose fertilizer"));
                                    CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-21","Pesticide spray"));
                                    CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-12-03","4th Dose fertilizer"));
                                    CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-12-05","4th Dose fertilizer"));
                                    CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-10","5th Dose fertilizer"));
*/


                                    /*farmcaldate[i]=date;
                                    farmcaldescription[i]=activitydescription;
                                    farmcalactivity[i]=activity;
                                    farmcalid[i]=id;
                                    farmcalimglink[i]=activity_img;*/

                                    /*taskdatum = new Taskdata();
                                    taskdatum.setTaskDate(date);
                                    taskdatum.setTaskTitle(activity);
                                    taskdatum.setTaskDescription(activitydescription);
                                    taskdatum.setImgBgLink(activity_img);
                                    taskdatum.setIsDone(is_done);
                                    taskdatums.add(taskdatum);*/

                                        // Log.e("Date :",date+"activity"+activity+taskdatums.size());

                                    }

                                    sum = sum / totalcount;


                                    tvtask_completed_num.setText(completedcount + "/" + totalcount);
                                    tvtask_pending_num.setText(pendingcount + "");
                                    //  Toast.makeText(context, String.valueOf(sum), Toast.LENGTH_SHORT).show();
                                    tv_ranking.setText(String.valueOf(sum));
                                    // float ratio=completedcount/totalcount;
                                    final LinearLayout completed_fill = (LinearLayout) findViewById(R.id.task_completed_green_fill);
                                    final LinearLayout full_fill = (LinearLayout) findViewById(R.id.full_layout);
                                    final LinearLayout pending_fill = (LinearLayout) findViewById(R.id.task_pending_red_fill);

                                    final int finalCompletedcount = completedcount;
                                    final int finalTotalcount = totalcount;
                                    final int finalPendingcount = pendingcount;
                                    completed_fill.post(new Runnable()
                                {

                                    @Override
                                    public void run()
                                    {
                                        fullfill[0] =full_fill.getWidth();
                                        Log.i("TEST", "Layout width : "+ full_fill.getWidth());

                                        LinearLayout.LayoutParams lpcompleted = new LinearLayout.LayoutParams(fullfill[0] * finalCompletedcount / finalTotalcount,
                                                21); // or set height to any fixed value you want
                                        completed_fill.setLayoutParams(lpcompleted);
                                        LinearLayout.LayoutParams lppending = new LinearLayout.LayoutParams(fullfill[0] * finalPendingcount / finalTotalcount,
                                                21); // or set height to any fixed value you want
                                        pending_fill.setLayoutParams(lppending);

                                    }
                                });


                                    Resources res = getResources();
                                    Drawable drawable = res.getDrawable(R.drawable.circular);
                                    final ProgressBar mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
                                    mProgress.setProgress(0);   // Main Progress
                                    mProgress.setSecondaryProgress(0); // Secondary Progress
                                    mProgress.setMax(10); // Maximum Progress
                                    mProgress.setProgressDrawable(drawable);

                                    ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, sum);
                                    animation.setDuration(800);
                                    animation.setInterpolator(new DecelerateInterpolator());
                                    animation.start();

                                    tv = (TextView) findViewById(R.id.tv);
                                    final int finalSum = sum;
                                    new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                            // TODO Auto-generated method stub
                                            while (pStatus < finalSum) {
                                                pStatus += 1;

                                                handler.post(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        // TODO Auto-generated method stub
                                                        mProgress.setProgress(pStatus);
                                                        tv.setText(pStatus + "/10");

                                                    }
                                                });
                                                try {
                                                    // Sleep for 200 milliseconds.
                                                    // Just to display the progress slowly
                                                    Thread.sleep(16); //thread will take approx 3 seconds to finish
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }).start();


                                    //    Toast.makeText(context, completedcount+"  "+pendingcount, Toast.LENGTH_SHORT).show();
                               /* Log.e("TaskDatum:",String.valueOf(taskdatums.size()));
                                mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
                                mprogressDialog.dismiss();
                                mAdapter = new TaskRecyclerViewAdapter(taskdatums,context);
                                mRecyclerView.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();
                                mLayoutManager = new LinearLayoutManager(context);
                                mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                mRecyclerView.setLayoutManager(mLayoutManager);*/

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }else /*if(flagonrecieve.equals("fetchcrop"))*/{

                                JSONObject jsonObjectnew;
                                String crop_name=null;
                                String date_of_sowing=null;
                                String Expected_date_of_harvest=null;
                               // Log.e("CHECK",response);
                                TextView tv_sowingdate=(TextView)findViewById(R.id.tv_sowing_date);
                                TextView tv_crop_name=(TextView)findViewById(R.id.tv_crop_name);
                                TextView tv_harvest_date=(TextView)findViewById(R.id.tv_harvest_date);

                                try {
                                    jsonObjectnew=new JSONObject(response);
                                    crop_name= jsonObjectnew.getString("crop_name");
                                    date_of_sowing= jsonObjectnew.getString("date_of_sowing");
                                    Expected_date_of_harvest =jsonObjectnew.getString("expct_dateof_harvest");

                                    tv_crop_name.setText(crop_name);
                                    tv_harvest_date.setText(Expected_date_of_harvest);
                                    tv_sowingdate.setText(date_of_sowing);


                                }

                                catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    if(farmnum!=null){
                        params.put(KEY_FARM_NUM,farmnum);
                    }
                    /*if(date!=null) {
                        params.put(TASK_DATE, date);
                    }*/
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

            return null;
        }


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {

           }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
            } if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


}
