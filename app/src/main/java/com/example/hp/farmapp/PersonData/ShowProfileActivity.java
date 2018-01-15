package com.example.hp.farmapp.PersonData;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.LandingActivity;
import com.example.hp.farmapp.DataHandler.DataHandler;
import com.example.hp.farmapp.Login.MainActivity;
import com.example.hp.farmapp.PersonData.customfonts.MyTextView;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShowProfileActivity extends AppCompatActivity {

    MyTextView nametv,emailtv,dobtv,mobnotv,mobnoalttv,landlionenotv,aadharnotv,pannotv,addresstv;
    Context context;
    Toolbar mActionBarToolbar;
    ImageButton editshowpro;
    ProgressDialog progressDialog;
    private static final String REGISTER_URL_DATA_PROFILE = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/signUp/fetch_profile";
    public static final String KEY_USER_NUM = "user_num";
    String user_num;


    @Override
    public void onBackPressed() {
        /*Intent intent=new Intent(context,LandingActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();
    }


    ImageView goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);


        context=this;
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }


        nametv=(MyTextView)findViewById(R.id.showprofilename);
        emailtv=(MyTextView)findViewById(R.id.showprofileemail);
        dobtv=(MyTextView)findViewById(R.id.showprofiledob);
        mobnotv=(MyTextView)findViewById(R.id.showprofilemobno);
        mobnoalttv=(MyTextView)findViewById(R.id.showprofilemobno2);
        landlionenotv=(MyTextView)findViewById(R.id.showprofilelandlineno);
        aadharnotv=(MyTextView)findViewById(R.id.showprofileaadharNo);
        pannotv=(MyTextView)findViewById(R.id.showprofilepanno);
        addresstv=(MyTextView)findViewById(R.id.showprofileaddress);
        goback=(ImageView)findViewById(R.id.gobackbutt);
        editshowpro=(ImageButton)findViewById(R.id.editoptionforproile);
        user_num=SharedPreferencesMethod.getString(context,"UserNum");


        progressDialog = ProgressDialog.show(ShowProfileActivity.this,
                getString(R.string.dialog_please_wait),"");
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(context,LandingActivity.class);
                startActivity(intent);
                finish();*/
                onBackPressed();
            }
        });

editshowpro.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(context,EditProfileActivity.class);
        startActivity(intent);
        finish();
    }
});
    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {
        public AsyncTaskRunner() {
            super();
        }


        @Override
        protected String doInBackground(final String... params) {

            try {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL_DATA_PROFILE,

                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {



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
                                        progressDialog.dismiss();


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


                                        if(addL2.matches("")){
                                            addL2=addL2;
                                        }
                                        else{
                                            addL2=", "+addL2;
                                        }
                                        if(addL3.matches("")){
                                            addL3=addL3;
                                        }
                                        else{
                                            addL3=", "+addL3;
                                        }

                                        String Name,Address;
                                        Name=firstname+" "+""+middlename+" "+lastname;
                                        Address=addL1 + addL2 + addL3+", "+city+", "+state+", "+country;

                                        nametv.setText(Name);
                                        emailtv.setText(email);
                                        addresstv.setText(Address);
                                        aadharnotv.setText(adharno);
                                        pannotv.setText(panno);
                                        mobnotv.setText(mobno1);
                                        mobnoalttv.setText(mobno2);
                                        landlionenotv.setText(landlineno);
                                        dobtv.setText(dob);


                                    } catch (JSONException e) {

                                        progressDialog.dismiss();
                                        e.printStackTrace();
                                    }
                                }


                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        if(user_num!=null){
                            params.put(KEY_USER_NUM,user_num);
                        }

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            }catch (Exception e){}
            return "";
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

}
