package com.example.hp.farmapp.FarmData;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import com.example.hp.farmapp.DataHandler.DataHandler;
import com.example.hp.farmapp.FarmData.FarmPackage.EditFarmActivity;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 15/12/17.
 */

public class ShowFarmActivity extends AppCompatActivity {
    Toolbar mActionBarToolbar;
    Context context;
    TextView mFarmName,mCropName,mGrowingRegion,mGrowingSeason,mArea,mSoilType,mIrrigationType,mSowingDate,mHarvestDate,mSpecialNote,mFarmAddress;
    String AREA, FARM_GPSC1,FARM_GPSC2,FARM_GPSC3, FARM_GPSC4, FARM_GPSC5, FARM_GPSC6,SOIL_TYPE,IRRIGATION_TYPE,AddL1,AddL2,AddL3,City,State,Country,FarmNum,UserNum;
    String Growing_Season,Growing_Region,Sowing_date,Harvest_date,Special_Comment,Crop_name,Address;
    private static final String REGISTER_URL_DATA_FARMADD = "http://spade.farm/app/index.php/signUp/fetch_farm_data";
    public static final String KEY_USER_NUM = "user_num";
    public static final String KEY_FARM_NUM = "farm_num";
    public static final String KEY_TOKEN = "token1";

    ProgressDialog progressDialog;
    ConnectivityManager connectivityManager;
    boolean connected=false;
    Boolean is_binded=false;
    ImageButton editshowfarm;
    ImageView goback;
    String ct1;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Intent intent=new Intent(context,MainActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        *//*Intent intent=new Intent(context,LandingActivity.class);
        startActivity(intent);
        finish();*//*

        super.onBackPressed();
        //return super.onOptionsItemSelected(item);
    }*/

    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.green_new));
        }
        super.onCreate(savedInstanceState);
        context=this;
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else {
            connected = false;
        }

        if (connected) {
            is_binded = SharedPreferencesMethod.getBoolean(context, SharedPreferencesMethod.BINDED);
            if (is_binded) {
        setContentView(R.layout.activity_show_farm);


        /*TextView title=(TextView)findViewById(R.id.tittle);
        title.setText("My Farm");
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);*/

        //getSupportActionBar().setTitle("My Title");

        /*if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }*/


        FarmNum=SharedPreferencesMethod.getString(context,"farm_num");
        //UserNum=SharedPreferencesMethod.getString(context,"UserNum");
        mFarmName=(TextView)findViewById(R.id.farm_name_tv);
        mArea=(TextView)findViewById(R.id.area_tv);
        mSoilType=(TextView)findViewById(R.id.soil_type_tv);
        mIrrigationType=(TextView)findViewById(R.id.irrigation_type_tv);
        mSpecialNote=(TextView)findViewById(R.id.special_note_tv);
        mFarmAddress=(TextView)findViewById(R.id.farm_address_tv);
        editshowfarm=(ImageButton)findViewById(R.id.editoptionforfarm);
        goback=(ImageView)findViewById(R.id.gobackbuttfarm);
        ct1=SharedPreferencesMethod.getString(context,"cctt");



        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(context,LandingActivity.class);
                startActivity(intent);
                finish();*/
                ShowFarmActivity.super.onBackPressed();
            }
        });

        editshowfarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,EditFarmActivity.class);
                startActivity(intent);
                finish();
            }
        });



        progressDialog = ProgressDialog.show(ShowFarmActivity.this,
                getString(R.string.dialog_please_wait),"");
        AsyncTaskRunner asyncTask=new AsyncTaskRunner();
        asyncTask.execute();
    }else
        {
            setContentView(R.layout.not_binded_layout);
            basic_title();
        }
    }else{
            setContentView(R.layout.internet_not_connencted);
            basic_title();
        }
    }


    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {
        public AsyncTaskRunner() {
            super();
        }
        @Override
        protected String doInBackground(final String... params) {
            try {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL_DATA_FARMADD,

                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
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
                                    String address_num=null;
                                    String pet_name=null;
                                    String farm_num=null;
                                    String is_crop_assigned=null;
                                    String is_verified=null;
                                    String person_num=null;
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
                                        pet_name=jobject.getString("farm_pet_name");
                                        farm_num=jobject.getString("farm_num");
                                        address_num=jobject.getString("address_num");
                                        is_crop_assigned=jobject.getString("is_crop_assigned");
                                        is_verified=jobject.getString("is_verified");
                                        person_num=jobject.getString("person_num");



                                        DataHandler.newInstance().setFardmaddsoiltype(soiltype);
                                        DataHandler.newInstance().setFarmaddaddl1(addl1);
                                        DataHandler.newInstance().setFarmaddaddl2(addl2);
                                        DataHandler.newInstance().setFarmaddaddl3(addl3);
                                        DataHandler.newInstance().setFarmaddirrigationtype(irrigationtype);
                                        DataHandler.newInstance().setFarmaddarea(area);
                                        DataHandler.newInstance().setFarmaddcity(farm_city);
                                        DataHandler.newInstance().setFarmaddcountry(farm_country);
                                        DataHandler.newInstance().setFarmdaddstate(farm_state);
                                        DataHandler.newInstance().setFarmaddspclcmnt(speccomment);
                                        DataHandler.newInstance().setFarmaddpetname(pet_name);
                                        DataHandler.newInstance().setShowfarmaddressnum(address_num);
                                        DataHandler.newInstance().setShowfarmfarmnum(farm_num);
                                        DataHandler.newInstance().setShowfarmgpsc1(gpsc1);
                                        DataHandler.newInstance().setShowfarmgpsc2(gpsc2);
                                        DataHandler.newInstance().setShowfarmgpsc3(gpsc3);
                                        DataHandler.newInstance().setShowfarmgpsc4(gpsc4);
                                        DataHandler.newInstance().setShowfarmgpsc5(gpsc5);
                                        DataHandler.newInstance().setShowfarmgpsc6(gpsc6);
                                        DataHandler.newInstance().setShowfarmiscropassigned(is_crop_assigned);
                                        DataHandler.newInstance().setShowfarmisverified(is_verified);
                                        DataHandler.newInstance().setShowfarmpersonnum(person_num);



                                        SOIL_TYPE=soiltype;
                                        IRRIGATION_TYPE=irrigationtype;
                                        AREA=area;
                                        AddL1=addl1;
                                        AddL2=addl2;
                                        AddL3=addl3;
                                        City=farm_city;
                                        State=farm_state;
                                        Country=farm_country;
                                        Special_Comment =speccomment;

                                        if(AddL2.matches("")){
                                        }
                                        else{
                                            AddL2=", "+AddL2;
                                        }
                                        if(AddL3.matches("")){
                                        }
                                        else{
                                            AddL3=", "+AddL3;
                                        }
                                        Address=AddL1 + AddL2 + AddL3+", "+City+", "+State+", "+Country;

                                        mFarmName.setText(pet_name);
                                        mArea.setText(AREA);
                                        mSoilType.setText(SOIL_TYPE);
                                        mIrrigationType.setText(IRRIGATION_TYPE);
                                        mFarmAddress.setText(Address);
                                        mSpecialNote.setText(Special_Comment);
                                        progressDialog.dismiss();

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
                                Toast.makeText(ShowFarmActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();


                        if(FarmNum!=null){
                            params.put(KEY_FARM_NUM,FarmNum);
                        }
                        if(ct1!=null){
                            params.put(KEY_TOKEN,ct1);
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

    void basic_title(){
        TextView title=(TextView)findViewById(R.id.tittle);
        title.setText("Farm Activity");
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
}
