package com.example.hp.farmapp.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.LandingActivity.LandingActivity;
import com.example.hp.farmapp.FarmData.FarmAddActivity;
import com.example.hp.farmapp.LangBaseActivity.BaseActivity;
import com.example.hp.farmapp.PersonData.FillProfileActivity;
import com.example.hp.farmapp.DataHandler.DataHandler;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginVerificationActivity extends BaseActivity {

    Context context;
    private static final String REGISTER_URL_CHECK_FILLED = "https://spade.farm/app/index.php/signUp/get_app_registry_data";
    private static final String REGISTER_URL_DATA_PROFILE = "https://spade.farm/app/index.php/signUp/fetch_profile";
    private static final String REGISTER_URL_DATA_FARMADD = "https://spade.farm/app/index.php/signUp/fetch_farm_data";

    public static final String KEY_USER_NUM = "user_num";
    String profiledataregisterurl,farmdaddregisterurl;
    String usernum="";
    String tempusernum="";
    String[] parameternamearray,parametervaluearray;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datasaveactivity);
        context=this;
        usernum=DataHandler.newInstance().getUsernumber();
       // tempusernum="130";
        progressDialog = ProgressDialog.show(LoginVerificationActivity.this, getString(R.string.dialog_please_wait),"");

        profiledataregisterurl=REGISTER_URL_DATA_PROFILE;
        farmdaddregisterurl=REGISTER_URL_DATA_FARMADD;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL_CHECK_FILLED,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonarray = null;
                        try {
                            jsonarray = new JSONArray(response);

                            parameternamearray=new String[jsonarray.length()];
                            parametervaluearray=new String[jsonarray.length()];
                            //activitydescriptionfarmal=new String[jsonarray.length()];

                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String parametername = jsonobject.getString("parameter_name");
                                String parametervalue=jsonobject.getString("parameter_value");
                               // String activitydescription=jsonobject.getString("activity_description");

                                parameternamearray[i]=parametername;
                                parametervaluearray[i]=parametervalue;
                               // activitydescriptionfarmal[i]=activitydescription;
                                Log.e("Date :",parametername+"Parameter value"+parametervalue);
                            }

                            /*DataHandler.newInstance().setDatefarmcal(datefarmcal);
                            DataHandler.newInstance().setActivityfarmcal(activityfarmcal);
                            DataHandler.newInstance().setActivitydescriptionfarmcal(activitydescriptionfarmal);
*/
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
                params.put(KEY_USER_NUM, usernum);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                if(parameternamearray.length==0){
                    progressDialog.dismiss();
                    finish();
                    Intent intent=new Intent(context,OTPActivity.class);
                    startActivity(intent);
                }else if(parameternamearray.length==1){
                    progressDialog.dismiss();
                    finish();
                    Intent intent=new Intent(context,FillProfileActivity.class);
                    startActivity(intent);
                }else if(parameternamearray.length==2){
                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    runner.execute(profiledataregisterurl);
                    progressDialog.dismiss();
                    finish();
                    Intent intent=new Intent(context,FarmAddActivity.class);
                    DataHandler.newInstance().setFromActivty("loginvefification");
                    startActivity(intent);

                }
                else if(parameternamearray.length==3) {
                    if (parameternamearray[0].equals("is_otp_verified") && parametervaluearray[0].equals("1")) {

                        if (parameternamearray[1].equals("is_profile_set") && parametervaluearray[1].equals("1")) {

                            if (parameternamearray[2].equals("is_farm_set") && parametervaluearray[2].equals("1")) {
                                AsyncTaskRunner runnerprofiledata = new AsyncTaskRunner();
                                runnerprofiledata.execute(profiledataregisterurl,"profile");
                                AsyncTaskRunner runnerfarmdata = new AsyncTaskRunner();
                                runnerfarmdata.execute(farmdaddregisterurl,"farmadd");
                                progressDialog.dismiss();
                                finish();
                                Intent intent = new Intent(context, LandingActivity.class);
                                startActivity(intent);
                                SharedPreferencesMethod.setBoolean(context,"Login",true);
                            } else {
                                progressDialog.dismiss();
                                finish();
                                Intent intent = new Intent(context, FarmAddActivity.class);
                                DataHandler.newInstance().setFromActivty("loginvefification");
                                startActivity(intent);
                            }
                        } else {
                            progressDialog.dismiss();
                            finish();
                            Intent intent = new Intent(context, FillProfileActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        progressDialog.dismiss();
                        finish();
                        Intent intent = new Intent(context, OTPActivity.class);
                        startActivity(intent);
                    }
                /*Intent intent=new Intent(context,HelpActivity.class);
                startActivity(intent);*/
                }
            }
        }, 3000);
    }





    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {
        public AsyncTaskRunner() {
            super();
        }


        @Override
        protected String doInBackground(String... params) {
            //usernum="130";
            final String regurl,flag;
            regurl=params[0];
            flag=params[1];

            try {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, regurl,

                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                       // String soiltype=jobject.getString("soilType");
                                       /* String mobilelogin = jobject.getString("mobNo");
                                        String usernum=jobject.getString("user_num");
                                        DataHandler.newInstance().setLoginEmail(emaillogin);
                                        DataHandler.newInstance().setLoginmobileno(mobilelogin);
                                        DataHandler.newInstance().setUsernumber(usernum);*/
                                        //progressDialog.dismiss();

                                        if(flag.matches("profile")) {
                                            String firstname = null;
                                            String lastname = null;
                                            String middlename = null;
                                            String adharno = null;
                                            String panno = null;
                                            String dob = null;
                                            String mobno1 = null;
                                            String mobno2 = null;
                                            String landlineno = null;
                                            String email = null;
                                            String addL1 = null;
                                            String addL2 = null;
                                            String addL3 = null;
                                            String city = null;
                                            String state = null;
                                            String country = null;
                                            String person_num;


                                            try {
                                                JSONObject jobject = new JSONObject(response);
                                                firstname = jobject.getString("firstName");
                                                lastname = jobject.getString("lastName");
                                                middlename = jobject.getString("middleName");
                                                adharno = jobject.getString("adhaarNo");
                                                panno = jobject.getString("PanNo");
                                                dob = jobject.getString("dob");
                                                mobno1 = jobject.getString("mobNo1");
                                                mobno2 = jobject.getString("mobNo2");
                                                landlineno = jobject.getString("landLineNo");
                                                email = jobject.getString("email");
                                                addL1 = jobject.getString("addL1");
                                                addL2 = jobject.getString("addL2");
                                                addL3 = jobject.getString("addL3");
                                                city = jobject.getString("city");
                                                state = jobject.getString("state");
                                                country = jobject.getString("country");
                                                person_num=jobject.getString("person_num");


                                                SharedPreferencesMethod.setString(context,"first_name",firstname);
                                                SharedPreferencesMethod.setString(context,"middle_name",middlename);
                                                SharedPreferencesMethod.setString(context,"last_name",lastname);
                                                SharedPreferencesMethod.setString(context,"aadhar_no",adharno);
                                                SharedPreferencesMethod.setString(context,"pan_no",panno);
                                                SharedPreferencesMethod.setString(context,"dob",dob);
                                                SharedPreferencesMethod.setString(context,"mobNo",mobno1);
                                                SharedPreferencesMethod.setString(context,"altermobNo",mobno2);
                                                SharedPreferencesMethod.setString(context,"landline_no",landlineno);
                                                SharedPreferencesMethod.setString(context,"email",email);
                                                SharedPreferencesMethod.setString(context,"profileaddl1",addL1);
                                                SharedPreferencesMethod.setString(context,"profileaddl2",addL2);
                                                SharedPreferencesMethod.setString(context,"profileaddl3",addL3);
                                                SharedPreferencesMethod.setString(context,"profilecity",city);
                                                SharedPreferencesMethod.setString(context,"profilestate",state);
                                                SharedPreferencesMethod.setString(context,"profilecountry",country);
                                                SharedPreferencesMethod.setString(context,"person_num",person_num);



                                            } catch (JSONException e) {

                                                progressDialog.dismiss();
                                                e.printStackTrace();
                                            }
                                           // Toast.makeText(LoginVerificationActivity.this, firstname, Toast.LENGTH_SHORT).show();
                                        }


                                        else{
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
                                            }

                                            //  Toast.makeText(LoginVerificationActivity.this, soiltype, Toast.LENGTH_SHORT).show();
                                        }
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginVerificationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put(KEY_USER_NUM, usernum);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            }catch (Exception e){}
            return regurl;
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LoginVerificationActivity.this,getString(R.string.dialog_please_wait),"");

        }

        @Override
        protected void onPostExecute(String s) {

            //Toast.makeText(MainActivity.this, s+"Not Getting Response", Toast.LENGTH_SHORT).show();

            /*if(s.equals("\"0\""))

            {
                progressDialog.dismiss();

            }*/
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

}
