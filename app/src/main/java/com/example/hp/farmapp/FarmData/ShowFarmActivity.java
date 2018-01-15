package com.example.hp.farmapp.FarmData;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
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
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;
import com.example.hp.farmapp.app.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
    private static final String REGISTER_URL_DATA_FARMADD = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/signUp/fetch_farm_data";
    public static final String KEY_USER_NUM = "user_num";
    public static final String KEY_FARM_NUM = "farm_num";
    ProgressDialog progressDialog;


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

    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_farm);
        context=this;

        TextView title=(TextView)findViewById(R.id.tittle);
        title.setText("My Farm");
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);

        //getSupportActionBar().setTitle("My Title");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        FarmNum=SharedPreferencesMethod.getString(context,"farm_num");
        UserNum=SharedPreferencesMethod.getString(context,"UserNum");
        mFarmName=(TextView)findViewById(R.id.farm_name_tv);
        mCropName=(TextView)findViewById(R.id.crop_name_tv);
        mArea=(TextView)findViewById(R.id.area_tv);
        mGrowingRegion=(TextView)findViewById(R.id.growing_region_tv);
        mGrowingSeason=(TextView)findViewById(R.id.growing_season_tv);
        mSoilType=(TextView)findViewById(R.id.soil_type_tv);
        mIrrigationType=(TextView)findViewById(R.id.irrigation_type_tv);
        mSowingDate=(TextView)findViewById(R.id.sowing_date_tv);
        mHarvestDate=(TextView)findViewById(R.id.harvest_date_tv);
        mSpecialNote=(TextView)findViewById(R.id.special_note_tv);
        mFarmAddress=(TextView)findViewById(R.id.farm_address_tv);



        progressDialog = ProgressDialog.show(ShowFarmActivity.this,
                getString(R.string.dialog_please_wait),"");
        AsyncTaskRunner asyncTask=new AsyncTaskRunner();
        asyncTask.execute();

     /*   AREA= SharedPreferencesMethod.getString(context,"farm_area");
        SOIL_TYPE=SharedPreferencesMethod.getString(context,"farm_soil_type");
        IRRIGATION_TYPE=SharedPreferencesMethod.getString(context,"farm_irrigation_type");
        AddL1=SharedPreferencesMethod.getString(context,"farm_addl1");
        AddL2=SharedPreferencesMethod.getString(context,"farm_addl2");
        AddL3=SharedPreferencesMethod.getString(context,"farm_addl3");
        City=SharedPreferencesMethod.getString(context,"farm_city");
        State=SharedPreferencesMethod.getString(context,"farm_state");
        Country=SharedPreferencesMethod.getString(context,"farm_country");
        Special_Comment = SharedPreferencesMethod.getString(context,"farm_spec_comment");
        Growing_Season = SharedPreferencesMethod.getString(context,"farm_growing_season");
        Growing_Region = SharedPreferencesMethod.getString(context,"farm_growing_region");
        Sowing_date = SharedPreferencesMethod.getString(context,"farm_sowing_date");
        Harvest_date = SharedPreferencesMethod.getString(context,"farm_harvest_date");
        Crop_name=SharedPreferencesMethod.getString(context,"farm_crop_name");*/
       /* Log.e("shared",SOIL_TYPE);
        Log.e("shared",AddL1 + IRRIGATION_TYPE+","+ City+","+Special_Comment);
*/


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
                                    String growing_region=null;
                                    String growing_season=null;
                                    String sowing_date=null;
                                    String harvest_date=null;
                                    String crop_name = null;
                                    String pet_name=null;
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
                                        pet_name=jobject.getString("farm_pet_name");


                                    /*    SharedPreferencesMethod.setString(context,"farm_area",area);
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
                                        SharedPreferencesMethod.setString(context,"farm_crop_name",crop_name);*/

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
                                        Growing_Season = growing_season;
                                        Growing_Region = growing_region;
                                        Sowing_date = sowing_date;
                                        Harvest_date = harvest_date;
                                        Crop_name=crop_name;
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
                                        mCropName.setText(Crop_name);
                                        mGrowingRegion.setText(Growing_Region);
                                        mGrowingSeason.setText(Growing_Season);
                                        mSoilType.setText(SOIL_TYPE);
                                        mIrrigationType.setText(IRRIGATION_TYPE);
                                        mSowingDate.setText(Sowing_date);
                                        mHarvestDate.setText(Harvest_date);
                                        mSpecialNote.setText(Special_Comment);
                                        mFarmAddress.setText(Address);
                                        progressDialog.dismiss();

                                    } catch (JSONException e) {

                                        progressDialog.dismiss();
                                        e.printStackTrace();
                                    }

                                    //  Toast.makeText(LoginVerificationActivity.this, soiltype, Toast.LENGTH_SHORT).show();
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

                        if(UserNum!=null){
                            params.put(KEY_USER_NUM,UserNum);
                        }
                        if(FarmNum!=null){
                            params.put(KEY_FARM_NUM,FarmNum);
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

}
