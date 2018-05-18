package com.example.hp.farmapp.Login;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.LandingActivity.LandingActivity;
import com.example.hp.farmapp.FarmData.FarmAddActivity;
import com.example.hp.farmapp.LangBaseActivity.BaseActivity;
import com.example.hp.farmapp.Login.ForgetPass.FrgtPassActivity;
import com.example.hp.farmapp.Alert.alertDialogManager;
import com.example.hp.farmapp.DataHandler.DataHandler;
import com.example.hp.farmapp.PersonData.FillProfileActivity;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Signup.SignUpActivity;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.hp.farmapp.app.Config;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

public class MainActivity extends BaseActivity {



    private static final String REGISTER_URL = "https://spade.farm/app/index.php/signUp/is_login_successful";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    String mobilefromsigup="",passwordfromsignup="";
    public static final String KEY_MOBILE = "mobNo";
    public static final String KEY_USER_NUM = "user_num";
    public static final String KEY_TOKEN = "token1";


    public String[] parameternamearray,parametervaluearray;


    final String TAG="loglookhereeee";
    TextView forgetpass,helplogin;
    ProgressDialog progressDialog;
    String responsee;
    alertDialogManager alert = new alertDialogManager();
    PackageInstaller.Session session;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    Boolean fromsignUp;
    Button loginbutt,testbutt;
    String mobNofromsignup;
    Boolean login;
    EditText emailtext,mobile;
    //EditText password;
    String Emaillogin="0",Passlogin,mobilelogin="",emailonrecieve="0",mobileonrecieve="0",passonrecieve,flagonreceive,urlonreceive;
    CheckBox chckshow;
    Boolean locationpermission=false;
    Boolean smspermission=false;
    Context context;
    TextView newuser;
    private TextView txtRegId, txtMessage;
    ShowHidePasswordEditText password;
    Toolbar mActionBarToolbar;
    public String usernumfinal;
    Boolean lock=false;
    String ct1;
    private Boolean exit = false;


    //SessionManager session;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);*/
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_LONG).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_LONG).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.green_new));
        }

        TextView title=(TextView)findViewById(R.id.tittle);
        title.setText(R.string.Accountlogin);
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);

        if (getSupportActionBar() != null){
            /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);*/
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        loginbutt=(Button)findViewById(R.id.loginbutt);
        emailtext= (EditText)findViewById(R.id.emaillogin);
         password = (ShowHidePasswordEditText) findViewById(R.id.pass);
        newuser =(TextView)findViewById(R.id.newUser);
        forgetpass=(TextView)findViewById(R.id.forgetpass);
        helplogin=(TextView)findViewById(R.id.help);
        txtMessage=(TextView)findViewById(R.id.textmesage);
        txtRegId=(TextView)findViewById(R.id.textregid);




        mobNofromsignup=DataHandler.newInstance().getSignupMobile();
        passwordfromsignup=DataHandler.newInstance().getSignUpPassword();


        forgetpass.setGravity(Gravity.CENTER | Gravity.BOTTOM);

        if(mobNofromsignup!=null&&password!=null){
        if(!mobNofromsignup.equals("") && !passwordfromsignup.equals("")){
        emailtext.setText(mobNofromsignup);
        password.setText(passwordfromsignup);}}

        txtMessage.setVisibility(View.GONE);
        txtRegId.setVisibility(View.GONE);






        if(locationpermission==checkLocationPermission()) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

         forgetpass.setOnClickListener(new View.OnClickListener() {
        @Override
               public void onClick(View v) {
            Intent intent = new Intent(context, FrgtPassActivity.class);
            startActivity(intent);
            finish();

        }
                         });


        helplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HelpActivity.class);
                startActivity(intent);
                finish();

            }
        });


        loginbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = password.getText().toString().trim().length();

                if ((!emailValidator(emailtext.getText().toString().trim())) && (!isValidMobile(emailtext.getText().toString().trim())))
                {
                    emailtext.setError(getString(R.string.invalid_email_mobile));
                }

                else if (!(length > 7 && length < 33)) {
                    password.setError(getString(R.string.password_er));
                    password.setText("");
                }

                else
                {

                    if(emailValidator(emailtext.getText().toString().trim()))
                    {
                        Emaillogin = emailtext.getText().toString();
                    }
                    else{
                        mobilelogin=emailtext.getText().toString();
                    }

                    Passlogin   = password.getText().toString();


                    progressDialog = ProgressDialog.show(MainActivity.this,
                            getString(R.string.dialog_please_wait),"");


                    AsyncTaskRunner runnermainact = new AsyncTaskRunner();
                    runnermainact.execute(Emaillogin, mobilelogin, Passlogin, "mainact", usernumfinal, REGISTER_URL);
                    }

            }
        });

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }

/*    private void scheduleAlarm() {
*/
/*
        Intent notifyIntent = new Intent(this,MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (context, NOTIFICATION_REMINDER_NIGHT, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                1000 * 60 * 60 * 24, pendingIntent);*//*



         AlarmManager alarmMgr;
         PendingIntent alarmIntent;
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
// Set the alarm to start at 21:32 PM
        Calendar calendar = Calendar.getInstance();
        Log.e("Started","runing......");
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 40);

// setRepeating() lets you specify a precise custom interval--in this case,
// 1 day
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);

    }
*/


    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        String mess=DataHandler.newInstance().getMessage();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
       //LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidMobile(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
           // if(phone.length() < 6 || phone.length() > 13) {
                 if(phone.length() != 10 ) {
                check = false;
                //txtPhone.setError("Not Valid Number");
            } else {
                     if(phone.startsWith("0"))
                     {check=false;}
                     else {
                check = true;}
            }
        } else {
            check=false;
        }
        return check;
    }


    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {
        public AsyncTaskRunner() {
            super();
        }


        @Override
        protected String doInBackground(final String... params) {
            final String emailonrec,mobilonrec,passonrec,flagonrec,usernumonrec,urlonrec;
            emailonrec = params[0];
            mobilonrec   = params[1];
            passonrec=params[2];
            flagonrec=params[3];
            usernumonrec=params[4];
            urlonrec=params[5];

            try {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlonrec,



                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                responsee = response;

                                Log.e("TAG","In response"+emailonrec+" "+mobilonrec+" "+passonrec+" "+flagonrec+" "+usernumonrec+" "+urlonrec);

                                if(flagonrec.equals("mainact")) {

                                    Log.e("TAG",response);
                                    if (response.equals("\"0\"")) {
                                        alert.showAlertDialog(MainActivity.this, getString(R.string.dialog_password_incorrect), getString(R.string.dialog_password_incorrect_helper), false);
                                        progressDialog.dismiss();
                                        usernumfinal=null;
                                        parameternamearray=null;
                                        password.setText("");

                                    }else if(response.equals("\"000\"")){
                                        alert.showAlertDialog(MainActivity.this, getString(R.string.dialog_password_incorrect), getString(R.string.dialog_password_incorrect_helper), false);
                                        progressDialog.dismiss();
                                        usernumfinal=null;
                                        parameternamearray=null;
                                        password.setText("");
                                    }
                                    else {
                                        try {
                                            JSONObject jobject = new JSONObject(response);
                                            String emaillogin = jobject.getString("email");
                                            String mobilelogin = jobject.getString("mobNo");
                                            String usernum = jobject.getString("user_num");
                                            String is_farmer=jobject.getString("is_farmer");
                                            String is_inspector=jobject.getString("is_inspector");
                                            String is_admin=jobject.getString("is_admin");
                                            String is_verified=jobject.getString("is_verified");
                                            String is_active=jobject.getString("is_active");
                                            String comp_num=jobject.getString("comp_num");

                                            DataHandler.newInstance().setIs_active_otp(is_active);
                                            usernumfinal = usernum;
                                            DataHandler.newInstance().setLoginEmail(emaillogin);
                                            DataHandler.newInstance().setLoginmobileno(mobilelogin);
                                            Log.e("TAG:",mobilelogin);
                                            DataHandler.newInstance().setUsernumber(usernum);
                                            SharedPreferencesMethod.setString(context, "Password", passonrec);
                                            SharedPreferencesMethod.setString(context, "UserNum", usernum);
                                            SharedPreferencesMethod.setString(context, "Mobile", mobilelogin);
                                            SharedPreferencesMethod.setString(context, "Email", emaillogin);

                                            if(comp_num!=null) {
                                                if(comp_num.equals("0")) {
                                                    SharedPreferencesMethod.setBoolean(context, SharedPreferencesMethod.BINDED, false);
                                                }else{
                                                    SharedPreferencesMethod.setBoolean(context, SharedPreferencesMethod.BINDED, true);
                                                }
                                            }

                                            Log.e("MainAct","Came here");




                                            if(is_farmer.equals("Y")){
                                                                lock=true;
                                                                if(is_active.equals("Y")){
                                                                    Intent intent=new Intent(context,LandingActivity.class);
                                                                    SharedPreferencesMethod.setString(context,"cctt",comp_num);
                                                                    SharedPreferencesMethod.setBoolean(context, "Login", true);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }else{
                                                                    Intent intent=new Intent(context,OTPActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                            }else{
                                                password.setText("");
                                                alert.showAlertDialog(MainActivity.this, getString(R.string.incorrect_user),getString(R.string.farmer_login_id), false);
                                                progressDialog.dismiss();

                                            }

                                        } catch (JSONException e) {

                                            progressDialog.dismiss();
                                            e.printStackTrace();
                                            Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }





                                else if(flagonrec.equals("farmadd")&&usernumonrec!=null){
                                    String soiltype = null;
                                    String area = null;
                                    String gpsc1 = null;
                                    String gpsc2 = null;
                                    String gpsc3 = null;
                                    String gpsc4 = null;
                                    String gpsc5 = null;
                                    String gpsc6 = null;
                                    String irrigationtype = null;
                                    String speccomment = null;
                                    String addl1 = null;
                                    String addl2 = null;
                                    String addl3 = null;
                                    String farm_city = null;
                                    String farm_state = null;
                                    String farm_country = null;
                                    String growing_region=null;
                                    String growing_season=null;
                                    String sowing_date=null;
                                    String harvest_date=null;
                                    String crop_name = null;
                                    try {
                                        JSONObject jobject = new JSONObject(response);
                                        area = jobject.getString("area");
                                        gpsc1 = jobject.getString("GPSc1");
                                        gpsc2 = jobject.getString("GPSc2");
                                        gpsc3 = jobject.getString("GPSc3");
                                        gpsc4 = jobject.getString("GPSc4");
                                        gpsc5 = jobject.getString("GPSc5");
                                        gpsc6 = jobject.getString("GPSc6");
                                        soiltype = jobject.getString("soilType");
                                        irrigationtype = jobject.getString("irrigationType");
                                        speccomment = jobject.getString("specComment");
                                        addl1 = jobject.getString("addL1");
                                        addl2 = jobject.getString("addL2");
                                        addl3 = jobject.getString("addL3");
                                        farm_city = jobject.getString("city");
                                        farm_state = jobject.getString("state");
                                        farm_country = jobject.getString("country");
                                        growing_region=jobject.getString("growing_region");
                                        growing_season = jobject.getString("growing_season");
                                        sowing_date=jobject.getString("date_of_sowing");
                                        harvest_date=jobject.getString("expct_dateof_harvest");
                                        crop_name=jobject.getString("crop_name");

                                        SharedPreferencesMethod.setString(context,"farm_area",area);
                                        SharedPreferencesMethod.setString(context,"farm_gpsc1",gpsc1);
                                        SharedPreferencesMethod.setString(context,"farm_gpsc2",gpsc2);
                                        SharedPreferencesMethod.setString(context,"farm_gpsc3",gpsc3);
                                        SharedPreferencesMethod.setString(context,"farm_gpsc4",gpsc4);
                                        SharedPreferencesMethod.setString(context,"farm_gpsc5",gpsc5);
                                        SharedPreferencesMethod.setString(context,"farm_gpsc6",gpsc6);
                                        SharedPreferencesMethod.setString(context,"farm_soil_type",soiltype);
                                        SharedPreferencesMethod.setString(context,"farm_irrigation_type",irrigationtype);
                                        SharedPreferencesMethod.setString(context,"farm_addl1",addl1);
                                        SharedPreferencesMethod.setString(context,"farm_addl2",addl2);
                                        SharedPreferencesMethod.setString(context,"farm_addl3",addl3);
                                        SharedPreferencesMethod.setString(context,"farm_city",farm_city);
                                        SharedPreferencesMethod.setString(context,"farm_state",farm_state);
                                        SharedPreferencesMethod.setString(context,"farm_country",farm_country);
                                        SharedPreferencesMethod.setString(context,"farm_spec_comment",speccomment);
                                        SharedPreferencesMethod.setString(context,"farm_growing_region",growing_region);
                                        SharedPreferencesMethod.setString(context,"farm_growing_season",growing_season);
                                        SharedPreferencesMethod.setString(context,"farm_sowing_date",sowing_date);
                                        SharedPreferencesMethod.setString(context,"farm_harvest_date",harvest_date);
                                        SharedPreferencesMethod.setString(context,"farm_crop_name",crop_name);

                                    } catch (JSONException e) {

                                        progressDialog.dismiss();
                                        e.printStackTrace();
                                        Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_SHORT).show();

                                    }

                                }


                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Log.e("Error",error.toString());
                                Toast.makeText(context, getString(R.string.error_text), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        if(passonrec!=null){
                            params.put(KEY_PASSWORD,passonrec);
                        }
                        if(emailonrec!=null){
                            params.put(KEY_EMAIL,emailonrec);
                        }

                        if(mobilonrec!=null){
                            params.put(KEY_MOBILE,mobilonrec);
                        }

                        if(usernumonrec!=null){
                            params.put(KEY_USER_NUM,usernumonrec);
                        }

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            }catch (Exception e){}
            return responsee;
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



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case 1:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {}
                return;
            }
        }
    }

    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

}
