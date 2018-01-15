package com.example.hp.farmapp.CalendarPackage;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import com.example.hp.farmapp.Settings.SettingActivity;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;
import com.example.hp.farmapp.Weather.WeatherViewPagerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemSelectedListener {

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

//    FarmCalAdapter farmCalAdapter;
    RecyclerView mrecyclerView;
    String url;
//    private List<Profilebeans> farmcal;
public final String KEY_PERSON_NUM="person_num";
    private String person_num,no_of_farms;
    private LinearLayoutManager mLayoutManager;
    LinearLayout linearLayout;
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

        
        Toast.makeText(context, "Utkarsh", Toast.LENGTH_SHORT).show();

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



        AsyncTaskRunner runner=new AsyncTaskRunner();
        runner.execute();

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
        spinner=(Spinner)header.findViewById(R.id.spinnernavheaderr);
        spinner.setOnItemSelectedListener(this);

      /*  Addfarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FarmAddActivity.class);
                startActivity(intent);
                finish();
            }
        });*/


        ImageView text = (ImageView) header.findViewById(R.id.textView);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHandler.newInstance().setFromActivty("landingactivity");
                Intent intent=new Intent(context,FarmAddActivity.class);
                startActivity(intent);
                finish();
            }
        });

        person_num = SharedPreferencesMethod.getString(context,"person_num");
        Log.e("PERSON_NUM",person_num+"kumar");
       // getFarmCount();
        getFarmNumName();

    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id){
        SpinnerData spinDta = new SpinnerData();
        spinDta = SpinnerDatums.get(position);
        SharedPreferencesMethod.setString(context,"farm_num",spinDta.getItem_value());
        Toast.makeText(getApplicationContext(), spinDta.getItem_value(), Toast.LENGTH_SHORT).show();
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
                            SpinnerAdapter spinnerAdapter=new SpinnerAdapter(getApplicationContext(),SpinnerDatums);
                            spinner.setAdapter(spinnerAdapter);
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
        } else if (id == R.id.nav_completed_activities) {
            Intent intent=new Intent(context,ShowTaskActivity.class);
            intent.putExtra("Type","all_activities");
            startActivity(intent);

        }else if(id==R.id.nav_my_activities){
            Intent intent= new Intent(context, ShowTaskActivity.class);
            intent.putExtra("Type","pending_activities");
            startActivity(intent);


        }else if (id == R.id.nav_notification) {
            Intent intent=new Intent(context,NotificationActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            startActivity(Intent.createChooser(sharingIntent, "Share With"));
            return true;
        }
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



            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL_ALL,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONArray jsonarray = null;
                            try {
                                jsonarray = new JSONArray(response);
                                /*farmcalid=new String[jsonarray.length()];
                                farmcalactivity=new String[jsonarray.length()];
                                farmcaldate=new String[jsonarray.length()];
                                farmcaldescription=new String[jsonarray.length()];
                                farmcalimglink = new String[jsonarray.length()];
                                farmcalisdone = new String[jsonarray.length()];*/
                               /* taskdatums=new ArrayList<>();
                                Taskdata taskdatum=new Taskdata();*/
                                CalendarCollection.date_collection_arr=new ArrayList<CalendarCollection>();

                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String id = jsonobject.getString("id");
                                    String activity=jsonobject.getString("activity");
                                    String activitydescription=jsonobject.getString("activity_description");
                                    String date=jsonobject.getString("date");
                                    String activity_img = jsonobject.getString("img_link");
                                    String is_done = jsonobject.getString("is_done");

                                    CalendarCollection.date_collection_arr.add(new CalendarCollection(date,activity));
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
            /*mprogressDialog =new ProgressDialog(context);
            mprogressDialog.setMessage("Loading....");
            mprogressDialog.show();*/
        }

        @Override
        protected void onPostExecute(String s) {

           /* mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
           Log.e("TaskDatum:",String.valueOf(taskdatums.size()));
            mAdapter = new TaskRecyclerViewAdapter(taskdatums,context);

            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mLayoutManager = new LinearLayoutManager(context);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);*/
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }


}
