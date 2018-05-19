package com.example.hp.farmapp.CalendarPackage.LandingActivity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.BroadCastReceiver.SampleBootReceiver;
import com.example.hp.farmapp.CalendarPackage.Adapter.SliderPagerAdapter;
import com.example.hp.farmapp.CalendarPackage.Adapter.SpinnerAdapter;
import com.example.hp.farmapp.CalendarPackage.CalendarCollection;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.ShowTaskActivity;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.ShowTaskViewPagerActivity;
import com.example.hp.farmapp.CalendarPackage.DisplayCalendarActivity;
import com.example.hp.farmapp.CalendarPackage.NavGetterSetter.SpinnerData;
import com.example.hp.farmapp.DataHandler.DataHandler;
import com.example.hp.farmapp.FarmData.FarmAddActivity;
import com.example.hp.farmapp.FarmData.FarmContractActivity;
import com.example.hp.farmapp.FarmData.FarmPackage.FarmImagesActivity;
import com.example.hp.farmapp.FarmData.FarmSoilTestActivity;
import com.example.hp.farmapp.FarmData.ShowFarmActivity;
import com.example.hp.farmapp.FarmImages.ShowAllFarmImagesActivity;
import com.example.hp.farmapp.Fragments.ProfileFragment;
import com.example.hp.farmapp.LangBaseActivity.BaseActivity;
import com.example.hp.farmapp.Login.MainActivity;
import com.example.hp.farmapp.PersonData.FillProfileActivity;
import com.example.hp.farmapp.PersonData.ShowProfileActivity;
import com.example.hp.farmapp.Notification.NotificationActivity;
import com.example.hp.farmapp.R;

import com.example.hp.farmapp.Response.ImageActivity;
import com.example.hp.farmapp.Settings.SettingActivity;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;
import com.example.hp.farmapp.Weather.WeatherViewPagerActivity;
import com.example.hp.farmapp.app.Config;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifTextView;


public class LandingActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    Context context;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    String is_weather_enabled="";

    /*private static final String REGISTER_URL_ALL = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmCalendar/send_farm_calendar_column_data_to_app";
    private static final String FETCH_FARM_COUNT = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmApp/count_farm_num";
    private static final String FETCH_FARM_NUM_NAME = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmApp/fetch_farm_num_and_name";
    private static final String FETCH_CROP_DETAILS = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmCalendar/send_farm_crop_details";
    private static final String REGISTER_URL_DATA_PROFILE = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/signUp/fetch_profile";
    private static final String URL_CHECK_COMPANY_NUM = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmApp/is_binded";
    private static final String URL_CHCECK_APP_REGISTORY = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/signUp/send_comp_registry";
*/


    private static final String REGISTER_URL_ALL = "https://spade.farm/app/index.php/farmCalendar/send_farm_calendar_column_data_to_app";
    private static final String FETCH_FARM_COUNT = "https://spade.farm/app/index.php/farmApp/count_farm_num";
    private static final String FETCH_FARM_NUM_NAME = "https://spade.farm/app/index.php/farmApp/fetch_farm_num_and_name";
    private static final String FETCH_CROP_DETAILS = "https://spade.farm/app/index.php/farmCalendar/send_farm_crop_details";
    private static final String REGISTER_URL_DATA_PROFILE = "https://spade.farm/app/index.php/signUp/fetch_profile";
    private static final String URL_CHECK_COMPANY_NUM = "https://spade.farm/app/index.php/farmApp/is_binded";
    private static final String URL_CHCECK_APP_REGISTORY = "https://spade.farm/app/index.php/signUp/send_comp_registry";

    //http://spade.farm/app/index.php/signUp/send_comp_registry

    Boolean is_binded = false;

    final String TAG = "loglookhereeee";
    RecyclerView mrecyclerView;
    ImageView noti_icon_iv;
    int pStatus = 0;
    private Handler handler = new Handler();
    int spinner_positionn = 0;
    ProgressDialog progressDialog;
    String farmnum = "1";
    TextView tv;
    String urlsend;
    ConnectivityManager connectivityManager;
    boolean connected = false;
    String user_num;
    int sum = 0;
    CardView insp_card_view;
    GifTextView no_internetGif;
    //    private List<Profilebeans> farmcal;
    public final String KEY_PERSON_NUM = "person_num";
    public final String KEY_FARM_NUM = "farm_num";
    public final String USER_NUM = "user_num";
    public final String KEY_TOKEN = "token3";
    public final String KEY_TOKEN_FETCH_PROFILE = "token1";

    public final String KEY_TOKEN_NUM_NAME = "token4";
    public final String KEY_APP_REG_TOKEN = "token2";

    int DELAY_MILLIS = 0;
    Boolean is_select_farm = false;
    TextView user_not_binded;


    private String person_num, no_of_farms;
    private LinearLayoutManager mLayoutManager;
    LinearLayout linearLayout;
    Bitmap myBitmap;

    //LocationRequest mLocationRequest;
    GoogleApiClient googleApiClient;
    TextView Addfarm, no_internet_text;
    Boolean is_app_reg_checked = false;
    BottomNavigationView bottomNavigationView;

    Boolean exit = false;
    private Spinner spinner;
    private TextView insp_name_landing;
    List<SpinnerData> SpinnerDatums = new ArrayList<>();
    Boolean mfetch_crop = false, mfetch_all = false;
    View addanimation;
    RelativeLayout main_fram_landing;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String ct1;
    DrawerLayout drawer;
    Boolean login_status = false;
    NavigationView navigationView;

    SpinnerData spinnerDatum = new SpinnerData();
    CircleImageView insp_profile_image;
    RelativeLayout relative_binding_layout;
    TextView mywidget;
    Bitmap image1, image2, image3, image4, image5;

    String insep_mobile_number = "";

    TextView tvtask_completed_num;
    TextView tvtask_pending_num;
    TextView tv_ranking ;
    LinearLayout completed_fill;
    LinearLayout full_fill;
    LinearLayout pending_fill;
    TextView tv_sowingdate;
    TextView tv_crop_name;
    Boolean is_profile_filled=false;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }


    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<Bitmap> slider_image_list;
    private TextView[] dots;
    int page_position = 0;

    private Handler handler_slider;
    private final int delay = 3000;
    private int page = 0;


    Runnable runnable = new Runnable() {
        public void run() {
            if (sliderPagerAdapter.getCount() == page) {
                page = 0;
            } else {
                page++;
            }
            vp_slider.setCurrentItem(page, true);
            handler_slider.postDelayed(this, delay);
        }
    };


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            // JSON here
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.green_new));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView title = (TextView) findViewById(R.id.tittlelanding);
        insp_card_view = (CardView) findViewById(R.id.cv_ins_pro_plus_name);
        no_internetGif = (GifTextView) findViewById(R.id.no_internet_land_gif);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        main_fram_landing = (RelativeLayout) findViewById(R.id.main_container_landing);
        no_internet_text = (TextView) findViewById(R.id.no_text_internet);
        relative_binding_layout = (RelativeLayout) findViewById(R.id.rel_binding_lay);
        mywidget = (TextView) findViewById(R.id.mywidget);
        mywidget.setSelected(true);


        title.setText(R.string.title_activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        noti_icon_iv = (ImageView) findViewById(R.id.noti_icon_for_landing);
        setSupportActionBar(toolbar);
        context = this;

        ct1 = SharedPreferencesMethod.getString(context, "cctt");
        login_status = SharedPreferencesMethod.getBoolean(context, "Login");


        handler_slider = new Handler();

        insp_name_landing = (TextView) findViewById(R.id.insp_name_landing);
        insp_profile_image = (CircleImageView) findViewById(R.id.ins_profile_pic);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        insp_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strnumber = insep_mobile_number.trim();
                if (insep_mobile_number.equals("")) {
                    Toast.makeText(context, R.string.no_inspector_text, Toast.LENGTH_SHORT).show();
                } else {
                    Uri number = Uri.parse("tel:" + strnumber);
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(number);
                    view.getContext().startActivity(intent);
                }
            }
        });

        user_num = SharedPreferencesMethod.getString(context, "UserNum");

        Log.e("USERNUM", SharedPreferencesMethod.getString(context, user_num));



        FirebaseMessaging.getInstance().subscribeToTopic("foo-bar");
        FirebaseMessaging.getInstance().subscribeToTopic("user_" + user_num);
        displayFirebaseRegId();
        CalendarCollection.date_collection_arr = new ArrayList<CalendarCollection>();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefreshlanding);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = new Intent(context, LandingActivity.class);
                startActivity(intent);
                finish();
            }
        });


        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else {
            connected = false;
        }
        if (connected) {


            if (login_status) {
                is_binded = SharedPreferencesMethod.getBoolean(context, SharedPreferencesMethod.BINDED);

                if (is_binded) {

                    DELAY_MILLIS = 0;
                    is_app_reg_checked = SharedPreferencesMethod.getBoolean(context, SharedPreferencesMethod.SETAPCHECK);
                    if (is_app_reg_checked) {

                    } else {
                        DELAY_MILLIS = 2000;
                        progressDialog = ProgressDialog.show(LandingActivity.this,
                                getString(R.string.dialog_please_wait), "");
                        CheckAppRegistry();
                    }

                } else {
                    DELAY_MILLIS = 2000;
                    relative_binding_layout.setVisibility(View.VISIBLE);
                    progressDialog = ProgressDialog.show(LandingActivity.this,
                            getString(R.string.dialog_please_wait), "");
                    bindUser();
                }

                noti_icon_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, NotificationActivity.class);
                        startActivity(intent);
                    }
                });

                bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
                removeShiftMode(bottomNavigationView);

                bottomNavigationView.getMenu().getItem(0).setCheckable(false);

                BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
                    final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
                    final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                    // set your height here
                    layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
                    // set your width here
                    layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
                    iconView.setLayoutParams(layoutParams);
                }

                bottomNavigationView.setOnNavigationItemSelectedListener(
                        new BottomNavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                Intent intent;
                                switch (item.getItemId()) {
                                    case R.id.action_activities:
                                        if (!is_select_farm) {
                                            intent = new Intent(context, ShowTaskViewPagerActivity.class);
                                            intent.putExtra("Type", "all_activities");
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(context, R.string.select_a_farm_first_text, Toast.LENGTH_SHORT).show();
                                        }
                                        break;


                                    case R.id.action_myfarm:
                                        if (!is_select_farm) {
                                            item.setCheckable(true);
                                            intent = new Intent(context, ShowFarmActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(context, R.string.select_a_farm_first_text, Toast.LENGTH_SHORT).show();
                                        }
                                        break;


                                    case R.id.action_profile:
                                        person_num = SharedPreferencesMethod.getString(context, "person_num");
                                        if (person_num.equals("")) {
                                            if(!is_profile_filled) {
                                                intent = new Intent(context, FillProfileActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        } else {
                                            intent = new Intent(context, ShowProfileActivity.class);
                                            startActivity(intent);
                                        }
                                        break;

                                    case R.id.action_settingss:
                                        intent = new Intent(context, SettingActivity.class);
                                        startActivity(intent);
                                        break;


                                    case R.id.action_weather:
                                        if (!is_select_farm) {
                                            intent = new Intent(context, WeatherViewPagerActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(context, R.string.select_a_farm_first_text, Toast.LENGTH_SHORT).show();
                                        }
                                        break;


                                }
                                return true;
                            }
                        });

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {


                        is_binded = SharedPreferencesMethod.getBoolean(context, SharedPreferencesMethod.BINDED);
                        Log.e("BINDING VALUE", is_binded.toString());

                        if (is_binded) {


                            tv_sowingdate = (TextView) findViewById(R.id.tv_sowing_date);
                            tv_crop_name = (TextView) findViewById(R.id.tv_crop_name);
                            tvtask_completed_num = (TextView) findViewById(R.id.task_completed_no_);
                            tvtask_pending_num = (TextView) findViewById(R.id.task_pending_no_);
                            tv_ranking = (TextView) findViewById(R.id.tv);
                            completed_fill = (LinearLayout) findViewById(R.id.task_completed_green_fill);
                            full_fill = (LinearLayout) findViewById(R.id.full_layout);
                            pending_fill = (LinearLayout) findViewById(R.id.task_pending_red_fill);

                            ct1 = SharedPreferencesMethod.getString(context, "cctt");

                            is_weather_enabled=SharedPreferencesMethod.getString(context,SharedPreferencesMethod.COMP_WEATHER);
                            if(is_weather_enabled.equals("1")){

                            }
                            else{
                                hideItem();
                                removeShiftMode(bottomNavigationView);
                            }


                            relative_binding_layout.setVisibility(View.GONE);
                            vp_slider = (ViewPager) findViewById(R.id.vp_slider);
                            ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
                            try {
                                Drawable myDrawable1 = getResources().getDrawable(R.drawable.image1);
                                image1 = ((BitmapDrawable) myDrawable1).getBitmap();
                                Drawable myDrawable2 = getResources().getDrawable(R.drawable.image2);
                                image2 = ((BitmapDrawable) myDrawable2).getBitmap();
                                Drawable myDrawable3 = getResources().getDrawable(R.drawable.image3);
                                image3 = ((BitmapDrawable) myDrawable3).getBitmap();
                                Drawable myDrawable4 = getResources().getDrawable(R.drawable.image4);
                                image4 = ((BitmapDrawable) myDrawable4).getBitmap();
                                Drawable myDrawable5 = getResources().getDrawable(R.drawable.image5);
                                image5 = ((BitmapDrawable) myDrawable5).getBitmap();
                                URL url1 = new URL("https://fthmb.tqn.com/LdEwLPTAAmyv-Rv3KPCdJ3RFli4=/768x0/filters:no_upscale():max_bytes(150000):strip_icc()/Coding-58b1fe485f9b5860464afed9.jpg");
                                URL url2 = new URL("https://fthmb.tqn.com/jML6Cm9IDrPWv00P1LFnIm3OtJU=/768x0/filters:no_upscale():max_bytes(150000):strip_icc()/153883795-copy-56a9f4953df78cf772abbec0.jpg");

                                /*image1 = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
                                image2 = BitmapFactory.decodeStream(url2.openConnection().getInputStream());*/
                            } catch (IOException e) {
                                System.out.println(e);
                            }
                            slider_image_list = new ArrayList<>();
                            slider_image_list.add(image1);
                            slider_image_list.add(image2);
                            slider_image_list.add(image3);
                            slider_image_list.add(image4);
                            slider_image_list.add(image5);

                            /*slider_image_list.add("https://fthmb.tqn.com/uXo0h1mgo3x6LfsYEOVQ01_zPL0=/768x0/filters:no_upscale():max_bytes(150000):strip_icc()/483034101-56a9f6723df78cf772abc5e1.jpg");
                            slider_image_list.add("https://fthmb.tqn.com/JnrcwFwlaXM86muPEBZal0O6Br0=/768x0/filters:no_upscale():max_bytes(150000):strip_icc()/183099261-56a9f6715f9b58b7d00038da.jpg");
                            slider_image_list.add("https://fthmb.tqn.com/jML6Cm9IDrPWv00P1LFnIm3OtJU=/768x0/filters:no_upscale():max_bytes(150000):strip_icc()/153883795-copy-56a9f4953df78cf772abbec0.jpg");
*/
                            sliderPagerAdapter = new SliderPagerAdapter(LandingActivity.this, slider_image_list);
                            vp_slider.setAdapter(sliderPagerAdapter);


                            vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                    page = position;
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    addBottomDots(position);
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });



                            if (SharedPreferencesMethod.getInt(context, "spinner_postion") == 0) {
                                spinner_positionn = 0;
                            } else {
                                spinner_positionn = SharedPreferencesMethod.getInt(context, "spinner_postion") - 1;

                            }


                            /*if (googleApiClient == null) {
                                googleApiClient = new GoogleApiClient.Builder(context)
                                        .addApi(LocationServices.API).addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) context)
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
                            }*/

                            if (getSupportActionBar() != null) {
                                getSupportActionBar().setDisplayShowTitleEnabled(false);
                            }


                            View header = navigationView.getHeaderView(0);
                            final CircleImageView landing_profile_pic;
                            landing_profile_pic = (CircleImageView) header.findViewById(R.id.profile_landing);


                            progressDialog = ProgressDialog.show(LandingActivity.this,
                                    getString(R.string.dialog_please_wait), "");

                            try {

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL_DATA_PROFILE,

                                        new com.android.volley.Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {


                                                String profile_img = null;
                                                String message = null;
                                                String status = null;
                                                String result = null;
                                                String person_num = null;


                                                try {

                                                    JSONObject jobject = new JSONObject(response);
                                                    message = jobject.getString("msg");
                                                    status = jobject.getString("status");
                                                    result = jobject.getString("result");

                                                    if (!status.equals("0")) {

                                                        JSONObject iprofileObject = new JSONObject(result);


                                                        profile_img = iprofileObject.getString("profile_img");
                                                        person_num = iprofileObject.getString("person_num");
                                                        SharedPreferencesMethod.setString(context, "person_num", person_num);

                                                        if (!person_num.equals("")) {
                                                            //hideItem();
                                                            is_profile_filled=true;
                                                            mywidget.setVisibility(View.GONE);
                                                        } else {
                                                            //showItem();
                                                            is_profile_filled=false;
                                                            mywidget.setVisibility(View.VISIBLE);
                                                        }

                                                        if (profile_img.equals("null")) {
                                                            landing_profile_pic.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_person_green_24dp));
                                                        } else {
                                                            Uri uriprofile = Uri.parse(profile_img);
                                                            Picasso.with(landing_profile_pic.getContext()).load(uriprofile).into(landing_profile_pic);
                                                        }
                                                    } else {
                                                        is_profile_filled=false;
                                                        mywidget.setVisibility(View.VISIBLE);


                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();

                                                }


                                            }


                                        },
                                        new com.android.volley.Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                progressDialog.dismiss();
                                                Log.e("checkArray",error.toString());
                                                Toast.makeText(context, R.string.error_text, Toast.LENGTH_LONG).show();
                                            }
                                        }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();

                                        if (user_num != null) {
                                            params.put(USER_NUM, user_num);
                                        }
                                        if (ct1 != null) {
                                            params.put(KEY_TOKEN_FETCH_PROFILE, ct1);
                                        }

                                        return params;
                                    }
                                };

                                RequestQueue requestQueue = Volley.newRequestQueue(context);
                                requestQueue.add(stringRequest);
                            } catch (Exception e) {
                                progressDialog.dismiss();
                            }


                            landing_profile_pic.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(context, ShowProfileActivity.class);
                                    startActivity(intent);
                                }
                            });

                            //spinner = (Spinner)navigationView.inflateHeaderView(R.layout.nav_header_profile).findViewById(R.id.spinnernavheaderr);
                            spinner = (Spinner) findViewById(R.id.landing_spinner);
                            spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) context);


                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    person_num = SharedPreferencesMethod.getString(context, "person_num");
                                    Log.e("PERSON_NUM", person_num + "kumar");
                                   // progressDialog.setCancelable(true);
                                    if (!person_num.equals("")) {
                                        //progressDialog.dismiss();
                                        getFarmNumName();
                                        //Toast.makeText(context, "person_num"+person_num, Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressDialog.dismiss();
                                    }

                                }
                            }, 1300);


                        } else {
                            // drawer.setVisibility(View.GONE);
                            relative_binding_layout.setVisibility(View.VISIBLE);
                        }


                    }
                }, DELAY_MILLIS);

            } else {
                new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.please_login))
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("foo-bar");
                                FirebaseMessaging.getInstance().unsubscribeFromTopic("user_" + user_num);
                                //SharedPreferencesMethod.clear(context);
                                SharedPreferencesMethod.setBoolean(context, "Login", false);
                                Intent intent = new Intent(context, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                    /*.setNegativeButton("No", null)*/
                        .show();
            }
        } else {

            no_internetGif.setVisibility(View.VISIBLE);
            main_fram_landing.setVisibility(View.GONE);
            no_internet_text.setVisibility(View.VISIBLE);
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_sort_white_24dp);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });


    }

    private void CheckAppRegistry() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHCECK_APP_REGISTORY,

                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jobject = null;
                        try {
                            jobject = new JSONObject(response);
                            String result = jobject.getString("result");
                            String status = jobject.getString("status");
                            if (status.equals("0")) {
                                progressDialog.dismiss();
                            } else {
                                JSONObject resultobject = new JSONObject(result);
                                String gps = resultobject.getString("gps");
                                String maxFarms = resultobject.getString("maxfarms");
                                String images = resultobject.getString("images");
                                String weather = resultobject.getString("weather");
                                String audio_reply=resultobject.getString("audio");
                                String offline_back_support_tech=resultobject.getString("obst");
                                String live_back_support_tech=resultobject.getString("lbst");
                                String license_period=resultobject.getString("lp");
                                String farm_count=resultobject.getString("fc");
                                String inspector_count=resultobject.getString("ic");


                                Toast.makeText(context, weather+"   ---------     "+images, Toast.LENGTH_SHORT).show();
                                SharedPreferencesMethod.setString(context, "GPSAPPCHECK", gps);
                                SharedPreferencesMethod.setString(context,SharedPreferencesMethod.COMP_AUDIO_REPLY,audio_reply);
                                SharedPreferencesMethod.setString(context,SharedPreferencesMethod.COMP_FARM_COUNT,farm_count);
                                SharedPreferencesMethod.setString(context,SharedPreferencesMethod.COMP_GPS,gps);
                                SharedPreferencesMethod.setString(context,SharedPreferencesMethod.COMP_INSPECTOR_COUNT,inspector_count);
                                SharedPreferencesMethod.setString(context,SharedPreferencesMethod.COMP_LICENSE_PERIOD,license_period);
                                SharedPreferencesMethod.setString(context,SharedPreferencesMethod.COMP_LIVE_BACK_TECH_SUPPORT,live_back_support_tech);
                                SharedPreferencesMethod.setString(context,SharedPreferencesMethod.COMP_OFFLINE_BACK_TECH_SUPPORT,offline_back_support_tech);
                                SharedPreferencesMethod.setString(context,SharedPreferencesMethod.COMP_IMAGES,images);
                                SharedPreferencesMethod.setString(context,SharedPreferencesMethod.COMP_MAX_FARMS,maxFarms);
                                SharedPreferencesMethod.setString(context,SharedPreferencesMethod.COMP_WEATHER,weather);
                                SharedPreferencesMethod.setBoolean(context, SharedPreferencesMethod.SETAPCHECK, true);
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("checkArray",error.toString());
                        Toast.makeText(context, R.string.error_text, Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_APP_REG_TOKEN, ct1);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void bindUser() {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHECK_COMPANY_NUM,

                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONObject jobject = null;
                            try {

                                jobject = new JSONObject(response);
                                String result = jobject.getString("result");
                                String status = jobject.getString("status");
                                String message = jobject.getString("msg");
                                JSONObject resultobject = new JSONObject(result);
                                String comp_num = resultobject.getString("comp_num");
                                Log.e("comp_num", comp_num);
                                if (comp_num != null) {
                                    if (comp_num.equals("0")) {
                                        SharedPreferencesMethod.setBoolean(context, SharedPreferencesMethod.BINDED, false);
                                    } else {
                                        SharedPreferencesMethod.setBoolean(context, SharedPreferencesMethod.BINDED, true);
                                        SharedPreferencesMethod.setString(context, "cctt", comp_num);
                                    }
                                    progressDialog.dismiss();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();

                            }

                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("checkArray",error.toString());
                            Toast.makeText(context, R.string.error_text, Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(USER_NUM, user_num);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            progressDialog.dismiss();
        }


    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        if (SharedPreferencesMethod.getInt(context, "spinner_postion") == 0) {
            spinner_positionn = 0;
        } else {
            spinner_positionn = SharedPreferencesMethod.getInt(context, "spinner_postion") - 1;

        }
        SpinnerData spinDta = new SpinnerData();
        spinDta = SpinnerDatums.get(position);
        SharedPreferencesMethod.setString(context, "farm_num", spinDta.getItem_value());
        SharedPreferencesMethod.setString(context, "GPS", spinDta.getGps_cordinate());

        if (spinDta.getItem_name().equals("Add New Farm")) {
            DataHandler.newInstance().setFromActivty("landingactivity");
            Intent intent = new Intent(context, FarmAddActivity.class);
            startActivity(intent);
            finish();
            // Toast.makeText(context, spinner_positionn+"", Toast.LENGTH_SHORT).show();
        } else if (spinDta.getItem_name().equals("Select Farm")) {
            is_select_farm = true;
            insp_name_landing.setText("");
            insp_profile_image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_person_green_24dp));
            tvtask_completed_num.setText("");
            tvtask_pending_num.setText("");
            tv_ranking.setText("0/0");
            tv_crop_name.setText("");
            tv_sowingdate.setText("");
           /* int fulfill;
            fulfill = full_fill.getWidth();*/
            //Log.i("TEST", "Layout width : " + full_fill.getWidth());
            LinearLayout.LayoutParams lpcompleted = new LinearLayout.LayoutParams(0,
                    21); // or set height to any fixed value you want
            completed_fill.setLayoutParams(lpcompleted);
            LinearLayout.LayoutParams lppending = new LinearLayout.LayoutParams(0,
                    21); // or set height to any fixed value you want
            pending_fill.setLayoutParams(lppending);
        } else {
            is_select_farm = false;
            if (spinDta.getInsp_profile_img().equals("null")) {
                insp_profile_image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_person_green_24dp));
            } else {
                Uri uriprofile = Uri.parse(spinDta.getInsp_profile_img());
                Picasso.with(insp_profile_image.getContext()).load(uriprofile).into(insp_profile_image);
                // myBitmap = ((BitmapDrawable) farmer_profile_image.getDrawable()).getBitmap();
            }
            if (spinDta.getInspector_name().equals("") || spinDta.getInspector_name().equals("null") || spinDta.getInspector_name().equals("null null") || spinDta.getInspector_name().equals("null null null")) {
                insp_name_landing.setText(R.string.no_inspector_text);
            } else {
                insp_name_landing.setText(spinDta.getInspector_name());
            }

            if (spinDta.getInspector_mob_no().equals("") || spinDta.getInspector_mob_no().equals("null")) {
                insep_mobile_number = "";
            } else {
                insep_mobile_number = spinDta.getInspector_mob_no();
            }

           /* progressDialog = ProgressDialog.show(LandingActivity.this,
                    getString(R.string.dialog_please_wait),"");*/
            CalendarCollection.date_collection_arr.clear();
            urlsend = REGISTER_URL_ALL;
            AsyncTaskRunner runnerall = new AsyncTaskRunner();
            runnerall.execute(urlsend, "fetchall", farmnum);
            urlsend = FETCH_CROP_DETAILS;
            farmnum = spinDta.getItem_value();
            AsyncTaskRunner runnercrop = new AsyncTaskRunner();
            runnercrop.execute(urlsend, "fetchcrop", farmnum);
            SharedPreferencesMethod.setInt(context, "spinner_postion", position);
            //SharedPreferencesMethod.getEditor(context).putInt("spinner_postion",position);
            //Toast.makeText(getApplicationContext(), spinDta.getItem_name() + "   " + spinDta.getItem_value() + "  " + position, Toast.LENGTH_SHORT).show();
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



    private void getFarmNumName() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, FETCH_FARM_NUM_NAME,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject new_object = null;

                            new_object = new JSONObject(response);

                            String status = new_object.getString("status");
                            String message = new_object.getString("msg");
                            String result = new_object.getString("result");

                            spinnerDatum = new SpinnerData();
                            spinnerDatum.setItem_name("Select Farm");
                            spinnerDatum.setItem_value("1111111");
                            spinnerDatum.setGps_cordinate("null");
                            spinnerDatum.setInsp_profile_img("null");
                            spinnerDatum.setInspector_name("null");
                            SpinnerDatums.add(spinnerDatum);

                            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(), SpinnerDatums);
                            spinner.setAdapter(spinnerAdapter);

                            if (!status.equals("0")) {
                                JSONArray jsonarray = null;
                                jsonarray = new JSONArray(result);

                                Log.e("RESPONSE :", response);


                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonObject = jsonarray.getJSONObject(i);
//                                String id = jsonObject.getString("id");
                                    String farm_num = jsonObject.getString("farm_num");
                                    String farm_name = jsonObject.getString("farm_pet_name");
                                    String gps = jsonObject.getString("GPSc1");
                                    String firstname = jsonObject.getString("firstName");
                                    String middlename = jsonObject.getString("middleName");
                                    String lastname = jsonObject.getString("lastName");
                                    String mobno1 = jsonObject.getString("mobNo1");
                                    String mobno2 = jsonObject.getString("mobNo2");
                                    String profile_img = jsonObject.getString("profile_img");


                                    String Name;
                                    Name = firstname + " " + "" + middlename + " " + lastname;

                                    spinnerDatum = new SpinnerData();
                                    spinnerDatum.setItem_name(farm_name);
                                    spinnerDatum.setItem_value(farm_num);
                                    spinnerDatum.setGps_cordinate(gps);
                                    spinnerDatum.setInsp_profile_img(profile_img);
                                    spinnerDatum.setInspector_name(Name);
                                    spinnerDatum.setInspector_mob_no(mobno1);
                                    //spinnerDatum.setItem_name(gps);
                                    SpinnerDatums.add(spinnerDatum);
                                    Log.e("check_farm_num_and name", "Farm Name = " + spinnerDatum.getItem_name() + spinnerDatum.getItem_value());
                                }
                                Log.e("check_size", " " + SpinnerDatums.size());
                                spinner.setSelection(spinner_positionn);
                                progressDialog.dismiss();
                                spinnerAdapter.notifyDataSetChanged();
                            } else {
                                //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }

                            spinnerDatum = new SpinnerData();
                            spinnerDatum.setItem_name("Add New Farm");
                            spinnerDatum.setItem_value("0");
                            spinnerDatum.setGps_cordinate("null");
                            spinnerDatum.setInsp_profile_img("null");
                            spinnerDatum.setInspector_name("null");
                            SpinnerDatums.add(spinnerDatum);
                            spinnerAdapter.notifyDataSetChanged();

                            Log.e("check", " " + SpinnerDatums.size());


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log.e(TAG,error.toString());
                        Log.e("checkArray",error.toString());
                        Toast.makeText(context, R.string.error_text, Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_PERSON_NUM, person_num);
                params.put(KEY_TOKEN_NUM_NAME, ct1);
                Log.e("PERWON NUM :", person_num);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

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
                Toast.makeText(this, getString(R.string.back_press),
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_calendar) {
            if (!is_select_farm) {
                Intent intent = new Intent(context, DisplayCalendarActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(context, R.string.select_a_farm_first_text, Toast.LENGTH_SHORT).show();

            }
        } else if (id == R.id.nav_notification) {
            Intent intent = new Intent(context, NotificationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_soil_test) {
            if (!is_select_farm) {
                Intent intent = new Intent(context, FarmSoilTestActivity.class);
                startActivity(intent);
            } else
                Toast.makeText(context, R.string.select_a_farm_first_text, Toast.LENGTH_SHORT).show();


        } else if (id == R.id.nav_farm_images) {
            if (!is_select_farm) {
                Intent intent = new Intent(context, ShowAllFarmImagesActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(context, R.string.select_a_farm_first_text, Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_logout) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.sure_exit_text)
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes_text, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FirebaseMessaging.getInstance().unsubscribeFromTopic("foo-bar");
                            FirebaseMessaging.getInstance().unsubscribeFromTopic("user_" + user_num);
                            SharedPreferencesMethod.clear(context);
                            SharedPreferencesMethod.setBoolean(context, "Login", false);
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.no_text, null)
                    .show();

        } else if (id == R.id.nav_farm_contract) {
            if (!is_select_farm) {
                Intent intent = new Intent(context, FarmContractActivity.class);
                startActivity(intent);
            } else
                Toast.makeText(context, R.string.select_a_farm_first_text, Toast.LENGTH_SHORT).show();

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

    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        public AsyncTaskRunner() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            final String urlonrecieve, flagonrecieve;
            urlonrecieve = params[0];
            flagonrecieve = params[1];


            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlonrecieve,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            if (flagonrecieve.equals("fetchall")) {
                                JSONArray jsonarray = null;
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    Log.e("Response full", jsonObject.toString());
                                    String status = jsonObject.getString("status");
                                    String status_msg = jsonObject.getString("status_msg");
                                    Log.e("Response status", status.toString());

                                    final int[] fullfill = new int[1];


                                    if (status.equals("1")) {

                                        String result_set = jsonObject.getString("result");

                                        //final int[] fullfill = new int[1];

                                        int completedcount = 0, pendingcount = 0, totalcount = 0;
                                        String rating_factor = "0";

                                        jsonarray = new JSONArray(result_set);

                                        totalcount = jsonarray.length();
                                        for (int i = 0; i < jsonarray.length(); i++) {
                                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                                            String id = jsonobject.getString("id");
                                            String farm_num = jsonobject.getString("farm_num");
                                            String activity = jsonobject.getString("activity");
                                            String activitydescription = jsonobject.getString("activity_description");
                                            String date = jsonobject.getString("date");
                                            String activity_img = jsonobject.getString("img_link");
                                            String is_done = jsonobject.getString("is_done");
                                            rating_factor = jsonobject.getString("rating");
                                            String date_today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                                            String farm_cal_mast_num = jsonobject.getString("farm_cal_mast_num");

                                            Log.e("Farm_cal_mast_num", farm_cal_mast_num + "   " + farm_num);

                                            //sum = Integer.parseInt(rating_factor) + sum;
                                            //Toast.makeText(context, Integer.parseInt(rating_factor), Toast.LENGTH_SHORT).show();


                                            if (is_done.equals("Y")) {
                                                completedcount++;
                                            } else {
                                                if (date.compareTo(date_today) < 0) {

                                                    pendingcount++;

                                                }

                                            }


                                            CalendarCollection.date_collection_arr.add(new CalendarCollection(date, activity, farm_cal_mast_num, farm_num));

                                        }


                                        if(pendingcount>0){
                                            AlarmManager alarmManager = (AlarmManager)getSystemService(context.ALARM_SERVICE);
                                            Intent alarmIntent = new Intent(context, SampleBootReceiver.class);

                                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                            alarmIntent.setData((Uri.parse("custom://"+System.currentTimeMillis())));
                                            //alarmManager.cancel(pendingIntent);
                                            Calendar alarmStartTime = Calendar.getInstance();
                                            Calendar now = Calendar.getInstance();
                                            alarmStartTime.set(Calendar.HOUR_OF_DAY, 8);
                                            alarmStartTime.set(Calendar.MINUTE, 00);
                                            alarmStartTime.set(Calendar.SECOND,00);
                                            if (now.after(alarmStartTime)) {
                                                Log.d("Hey", "Added a day");
                                                alarmStartTime.add(Calendar.DATE, 1);
                                            }

                                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), 1000 /** 60 * 60 * 5*/, pendingIntent);//AlarmManager.INTERVAL_DAY
                                            Log.d("Alarm", "Alarms set for everyday 8 am.");
                                        }else{
                                            alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                                            Intent intent = new Intent(context, SampleBootReceiver.class);
                                            alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                                            if (alarmMgr != null) {
                                                alarmMgr.cancel(alarmIntent);
                                            }
                                        }

                                        sum = Integer.parseInt(rating_factor);


                                        tvtask_completed_num.setText(completedcount + "/" + totalcount);
                                        tvtask_pending_num.setText(pendingcount + "");
                                        //  Toast.makeText(context, String.valueOf(sum), Toast.LENGTH_SHORT).show();
                                        //tv_ranking.setText(String.valueOf(sum));
                                        // float ratio=completedcount/totalcount;

                                        final int finalCompletedcount = completedcount;
                                        final int finalTotalcount = totalcount;
                                        final int finalPendingcount = pendingcount;
                                        completed_fill.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                fullfill[0] = full_fill.getWidth();
                                                Log.i("TEST", "Layout width : " + full_fill.getWidth());

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
                                        mProgress.setSecondaryProgress(10); // Secondary Progress
                                        mProgress.setMax(10); // Maximum Progress
                                        mProgress.setProgressDrawable(drawable);

                                        ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, sum);
                                        animation.setDuration(800);
                                        animation.setInterpolator(new DecelerateInterpolator());
                                        animation.start();

                                        tv = (TextView) findViewById(R.id.tv);

                                        if (sum == 0) {
                                            pStatus = 0;
                                            tv.setText(pStatus + "/10");
                                        } else {
                                            new Thread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    // TODO Auto-generated method stub
                                                    while (pStatus < sum) {

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
                                        }

                                        mfetch_all = true;

                                    } else {


                                        sum = 0;


                                        tvtask_completed_num.setText("0");
                                        tvtask_pending_num.setText("0");
                                        completed_fill.post(new Runnable() {

                                            @Override
                                            public void run() {
                                                fullfill[0] = full_fill.getWidth();
                                                Log.i("TEST", "Layout width : " + full_fill.getWidth());

                                                LinearLayout.LayoutParams lpcompleted = new LinearLayout.LayoutParams(0,
                                                        21); // or set height to any fixed value you want
                                                completed_fill.setLayoutParams(lpcompleted);
                                                LinearLayout.LayoutParams lppending = new LinearLayout.LayoutParams(0,
                                                        21); // or set height to any fixed value you want
                                                pending_fill.setLayoutParams(lppending);

                                            }
                                        });


                                        Resources res = getResources();
                                        Drawable drawable = res.getDrawable(R.drawable.circular);
                                        final ProgressBar mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
                                        mProgress.setProgress(0);   // Main Progress
                                        mProgress.setSecondaryProgress(10); // Secondary Progress
                                        mProgress.setMax(10); // Maximum Progress
                                        mProgress.setProgressDrawable(drawable);

                                        ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, sum);
                                        animation.setDuration(800);
                                        animation.setInterpolator(new DecelerateInterpolator());
                                        animation.start();

                                        tv = (TextView) findViewById(R.id.tv);

                                        if (sum == 0) {
                                            pStatus = 0;
                                            tv.setText(pStatus + "/10");
                                        } else {
                                            new Thread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    // TODO Auto-generated method stub
                                                    while (pStatus < sum) {

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
                                        }

                                        mfetch_all = true;


                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            } else /*if(flagonrecieve.equals("fetchcrop"))*/ {


                                JSONObject jsonObjectnew;
                                String crop_name = null;
                                String date_of_sowing = null;
                                String Expected_date_of_harvest = null;
                                // Log.e("CHECK",response);

                                TextView tv_harvest_date = (TextView) findViewById(R.id.tv_harvest_date);

                                try {
                                    jsonObjectnew = new JSONObject(response);
                                    String result_set = jsonObjectnew.getString("result");
                                    String status = jsonObjectnew.getString("status");
                                    if (status.equals("1")) {
                                        JSONObject newjsonObject = new JSONObject(result_set);
                                        crop_name = newjsonObject.getString("crop_name");
                                        date_of_sowing = newjsonObject.getString("date_of_sowing");
                                        Expected_date_of_harvest = newjsonObject.getString("expct_dateof_harvest");

                                        tv_crop_name.setText(crop_name);
                                        tv_harvest_date.setText(Expected_date_of_harvest);
                                        tv_harvest_date.setVisibility(View.GONE);
                                        tv_sowingdate.setText(date_of_sowing);

                                        mfetch_crop = true;
                                    } else {
                                        tv_crop_name.setText(R.string.not_assigned_text);
                                        tv_harvest_date.setText("0000-00-00");
                                        tv_harvest_date.setVisibility(View.GONE);
                                        tv_sowingdate.setText("0000-00-00");
                                        mfetch_crop = true;
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }


                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("checkArray",error.toString());
                            Toast.makeText(context, R.string.error_text, Toast.LENGTH_LONG).show();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    if (farmnum != null) {
                        params.put(KEY_FARM_NUM, farmnum);
                    }
                    if (ct1 != null) {
                        params.put(KEY_TOKEN, ct1);
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
           /* if(mfetch_all && mfetch_crop){
                progressDialog.dismiss();
            }*/
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        String mess = DataHandler.newInstance().getMessage();

    }


    private void addBottomDots(int currentPage) {
        dots = new TextView[slider_image_list.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(18);
            dots[i].setTextColor(Color.parseColor("#000000"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (connected) {
            if (is_binded) {
                handler_slider.postDelayed(runnable, delay);
            }
        } else {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (connected) {
            if (is_binded) {

                handler_slider.removeCallbacks(runnable);
            }
        } else {

        }
    }

    @SuppressLint("RestrictedApi")
    static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }


    private void hideItem()
    {
        // bottomNavigationView= (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().removeItem(R.id.action_weather);
       /* Menu nav_Menu = bottomNavigationView.getMenu();
        nav_Menu.findItem(R.id.action_weather).setVisible(false);*/
    }

   /* private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }*/

    @Override
    protected void onRestart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    super.onRestart();}
}
