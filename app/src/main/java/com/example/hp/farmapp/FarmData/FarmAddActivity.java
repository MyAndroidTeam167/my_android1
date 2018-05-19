package com.example.hp.farmapp.FarmData;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.LandingActivity.LandingActivity;
import com.example.hp.farmapp.DataHandler.DataHandler;
import com.example.hp.farmapp.LangBaseActivity.BaseActivity;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FarmAddActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private static final String DEFAULT_LOCAL = "India";
    private static final String DEFAULT_LOCAL_STATE = "Madhya Pradesh";
    private static final String DEFAULT_LOCAL_CITY = "Indore";
    private static final String REGISTER_URL = "https://spade.farm/app/index.php/farmApp/insert_new_farm";
    public static final String AREA = "area";
    public static final String GPSC1 = "GPSc1";
    public static final String GPSC2 = "GPSc2";
    public static final String GPSC3 = "GPSc3";
    public static final String GPSC4 = "GPSc4";
    public static final String GPSC5 = "GPSc5";
    public static final String GPSC6 = "GPSc6";
    public static final String SOILTYPE = "soilType";
    public static final String KEY_TOKEN = "token3";
    public static final String IRRIGATIONTYPE = "irrigationType";
    public static final String SEPCCOMMENT = "specComment";
    public static final String ADDL1 = "addL1";
    public static final String PERSONNUM = "person_num";
    public static final String USERNUM = "user_num";
    public static final String ADDL2 = "addL2";
    public static final String ADDL3 = "addL3";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String COUNTRY = "country";
    public static final String FARM_PET_NAME = "farm_pet_name";


    Context context;
    ProgressDialog progressDialog;
    String ct1="";

    EditText addl1farmadd, addl2farmadd, addl3farmadd, farmpetname;
    EditText areafarmadd, gpsc1farmadd, gpsc2farmadd, gpsc3farmadd, gpsc4farmadd, gpsc5farmadd, gpsc6farmadd;
    EditText soiltypefarmadd, irrigationtypefarmadd, speccommentfarmadd;
    Button submitfarmadd;
    //ArrayList<String> state = new ArrayList<String>();
    String Countryidonclick = "";
    Toolbar mActionBarToolbar;

    String stateidonclick = "";

    Spinner cityfarmadd, statefarmadd, citizenship, spin_soil_type;

    String strarea, strgpsc1, strgpsc2, strgpsc3, strgpsc4, strgpsc5, strgpsc6, strsoiltype, strsoiltypefromspinner;
    String strirrigationtype, strspeccmnt, straddl1, straddl2, steaddl3;
    String strcity, strstate, strcountry, personnumfromfillprofile, usernumfrommainact, strfarmpetname;
    String fromActivity;

    String[] soiltype = {"Select Soil Type", "Alluvial Soils", "Black Soils", "Red Soils", "Laterite Soils", "Mountain Soils", "Desert Soils", "Other"};

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Intent intent=new Intent(context,MainActivity.class);
        startActivity(intent);
        finish();*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (fromActivity.matches("loginvefification")) {
            //super.onBackPressed();
            finish();

        } else if (fromActivity.matches("fillprofile")) {
            //super.onBackPressed();
            finish();

        } else {

            Intent intent = new Intent(context, LandingActivity.class);
            startActivity(intent);
            finish();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.green_new));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_add);

        fromActivity = DataHandler.newInstance().getFromActivty();

        context = this;
        addl1farmadd = (EditText) findViewById(R.id.addl1farmadd);
        addl2farmadd = (EditText) findViewById(R.id.addl2farmadd);
        addl3farmadd = (EditText) findViewById(R.id.addl3farmadd);
        farmpetname = (EditText) findViewById(R.id.farm_pet_name);
        farmpetname.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        cityfarmadd = (Spinner) findViewById(R.id.cityfarmadd);
        statefarmadd = (Spinner) findViewById(R.id.satefarmadd);
        citizenship = (Spinner) findViewById(R.id.countryfarmadd);
        areafarmadd = (EditText) findViewById(R.id.areafarmadd);
        spin_soil_type = (Spinner) findViewById(R.id.spinner_soil_type);
        ct1=SharedPreferencesMethod.getString(context,"cctt");
        /*  gpsc1farmadd = (EditText) findViewById(R.id.gpsc1farmadd);
        gpsc2farmadd = (EditText) findViewById(R.id.gpsc2farmadd);
        gpsc3farmadd = (EditText) findViewById(R.id.gpsc3farmadd);
        gpsc4farmadd = (EditText) findViewById(R.id.gpsc4farmadd);
        gpsc5farmadd = (EditText) findViewById(R.id.gpsc5farmadd);
        gpsc6farmadd = (EditText) findViewById(R.id.gpsc6farmadd);
      */
        soiltypefarmadd = (EditText) findViewById(R.id.soiltypefarmadd);

        soiltypefarmadd.setVisibility(View.GONE);
        irrigationtypefarmadd = (EditText) findViewById(R.id.irrigationtypefarmadd);
        speccommentfarmadd = (EditText) findViewById(R.id.speccommentfarmadd);
        submitfarmadd = (Button) findViewById(R.id.savefarmadd);


        spin_soil_type.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, soiltype);

        /*
        {
            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextSize(16);
                return v;
            }
            public View getDropDownView(int position, View convertView,ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                return v;
            }
        };*/
        aa.setDropDownViewResource(R.layout.spinner_item_new_black_text);
        spin_soil_type.setAdapter(aa);


        personnumfromfillprofile = SharedPreferencesMethod.getString(context, "person_num");
        usernumfrommainact = SharedPreferencesMethod.getString(context, "UserNum");
        //getSupportActionBar().setTitle("My Title");


        TextView title = (TextView) findViewById(R.id.tittle);
        title.setText(getString(R.string.edit_farm_title));
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);

        if (getSupportActionBar() != null) {
          /*  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);*/
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

       /* gpsc1farmadd.setVisibility(View.GONE);
        gpsc2farmadd.setVisibility(View.GONE);
        gpsc3farmadd.setVisibility(View.GONE);
        gpsc4farmadd.setVisibility(View.GONE);
        gpsc5farmadd.setVisibility(View.GONE);
        gpsc6farmadd.setVisibility(View.GONE);
        gpsc1farmadd.setVisibility(View.GONE);
*/
        JSONObject objcount = null;
        try {
            objcount = new JSONObject(loadJSONFromAssetcountry(context));
            JSONArray m_jArrycount = objcount.getJSONArray("countries");
            final String[] newcountry = new String[m_jArrycount.length()];
            final String[] newcountryid = new String[m_jArrycount.length()];
            ArrayList<String> countries = new ArrayList<String>();

            for (int i = 0; i < m_jArrycount.length(); i++) {
                JSONObject jo_insidecount = m_jArrycount.getJSONObject(i);
                newcountry[i] = jo_insidecount.getString("name");
                newcountryid[i] = jo_insidecount.getString("id");
                countries.add(newcountry[i]);

            }

            //Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
            ArrayAdapter<String> countryadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
            citizenship.setAdapter(countryadapter);
            citizenship.setSelection(countryadapter.getPosition(DEFAULT_LOCAL));


            citizenship.setEnabled(false);


            citizenship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    ArrayList<String> state = new ArrayList<String>();
                    Countryidonclick = newcountryid[position];
                    JSONObject objstate = null;
                    int cntryidcout = 0;

                    try {

                        objstate = new JSONObject(loadJSONFromAssetstate(context));
                        JSONArray m_jArrystate = objstate.getJSONArray("states");
                        String[] newstate = new String[m_jArrystate.length()];
                        String[] newstateid = new String[m_jArrystate.length()];
                        String[] newstatecountid = new String[m_jArrystate.length()];
                        for (int j = 0; j < m_jArrystate.length(); j++) {
                            JSONObject jo_insidestate = m_jArrystate.getJSONObject(j);
                            newstatecountid[j] = jo_insidestate.getString("country_id");
                            if (newstatecountid[j].equals(Countryidonclick)) {
                                newstate[j] = jo_insidestate.getString("name");
                                newstateid[j] = jo_insidestate.getString("id");
                                cntryidcout++;
                            } else {
                            }

                        }

                        final String[] finalstate = new String[cntryidcout];
                        final String[] finalstateid = new String[cntryidcout];

                        for (int k = 0, l = 0; k < m_jArrystate.length(); k++, l++) {
                            if (newstatecountid[k].equals(Countryidonclick)) {
                                finalstate[l] = newstate[k];
                                finalstateid[l] = newstateid[k];
                                state.add(finalstate[l]);
                            } else {
                                l--;
                            }

                        }

                        ArrayAdapter<String> stateadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, state);
                        statefarmadd.setAdapter(stateadapter);
                        statefarmadd.setSelection(stateadapter.getPosition(DEFAULT_LOCAL_STATE));


                        //Toast.makeText(FarmAddActivity.this, Integer.toString(cntryidcout), Toast.LENGTH_SHORT).show();


                        statefarmadd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                ArrayList<String> city = new ArrayList<String>();

                                // Toast.makeText(FarmAddActivity.this, finalstate[position] + finalstateid[position], Toast.LENGTH_SHORT).show();


                                JSONObject objcity = null;
                                int stateidcount = 0;
                                stateidonclick = finalstateid[position];

                                try {
                                    objcity = new JSONObject(loadJSONFromAssetcity(context));
                                    JSONArray m_jArrycity = objcity.getJSONArray("cities");
                                    String[] newcitye = new String[m_jArrycity.length()];
                                    String[] newcityid = new String[m_jArrycity.length()];
                                    String[] newcitycountid = new String[m_jArrycity.length()];

                                    for (int m = 0; m < m_jArrycity.length(); m++) {
                                        JSONObject jo_insidecity = m_jArrycity.getJSONObject(m);
                                        newcitycountid[m] = jo_insidecity.getString("state_id");
                                        if (newcitycountid[m].equals(stateidonclick)) {
                                            newcitye[m] = jo_insidecity.getString("name");
                                            newcityid[m] = jo_insidecity.getString("id");
                                            stateidcount++;
                                        } else {
                                        }

                                    }

//                                    Toast.makeText(FarmAddActivity.this, Integer.toString(m_jArrycity.length()), Toast.LENGTH_SHORT).show();
                                    final String[] finalcity = new String[stateidcount];
                                    final String[] finalcityid = new String[stateidcount];

                                    for (int n = 0, o = 0; n < m_jArrycity.length(); n++, o++) {
                                        if (newcitycountid[n].equals(stateidonclick)) {
                                            finalcity[o] = newcitye[n];
                                            finalcityid[o] = newcityid[n];
                                            city.add(finalcity[o]);
                                        } else {
                                            o--;
                                        }

                                    }
                                    //Toast.makeText(FarmAddActivity.this, Integer.toString(stateidcount), Toast.LENGTH_SHORT).show();
                                    ArrayAdapter<String> cityadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, city);
                                    cityfarmadd.setAdapter(cityadapter);
                                    cityfarmadd.setSelection(cityadapter.getPosition(DEFAULT_LOCAL_CITY));


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {


                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


        submitfarmadd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                strarea = areafarmadd.getText().toString().trim();
               /* strgpsc1=gpsc1farmadd.getText().toString().trim();
                strgpsc2=gpsc2farmadd.getText().toString().trim();
                strgpsc3=gpsc3farmadd.getText().toString().trim();
                strgpsc4=gpsc4farmadd.getText().toString().trim();
                strgpsc5=gpsc5farmadd.getText().toString().trim();
                strgpsc6=gpsc6farmadd.getText().toString().trim();*/
                strsoiltype = soiltypefarmadd.getText().toString().trim();
                strirrigationtype = irrigationtypefarmadd.getText().toString().trim();
                strspeccmnt = speccommentfarmadd.getText().toString().trim();
                straddl1 = addl1farmadd.getText().toString().trim();
                straddl2 = addl2farmadd.getText().toString().trim();
                steaddl3 = addl3farmadd.getText().toString().trim();
                strcity = cityfarmadd.getSelectedItem().toString();
                strcountry = citizenship.getSelectedItem().toString();
                strstate = statefarmadd.getSelectedItem().toString();

                if (straddl1.matches("")) {
                    addl1farmadd.setError(getString(R.string.address_error));
                } else if (strarea.matches("")) {
                    areafarmadd.setError(getString(R.string.area_error));
                }  else if (strirrigationtype.matches("")) {
                    irrigationtypefarmadd.setError(getString(R.string.error_irrigation));
                } else {
                    if (spin_soil_type.getSelectedItem().toString().equals("Other")) {
                        Log.e("Tag", "came here in Other category");
                        if (strsoiltype.matches("")) {
                            soiltypefarmadd.setError("Soiltype can't be null");
                        } else {
                            Log.e("Tag", "came here in Other category in if else");
                        }
                    }

                    progressDialog = ProgressDialog.show(FarmAddActivity.this,
                            getString(R.string.loading_text), "");
                    try {
                        GetText();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    public String loadJSONFromAssetcountry(Context mcontext) {
        String json = null;
        try {
            InputStream is = mcontext.getAssets().open("countries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String loadJSONFromAssetstate(Context mcontext) {
        String json = null;
        try {
            InputStream is = mcontext.getAssets().open("states.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String loadJSONFromAssetcity(Context mcontext) {
        String json = null;
        try {
            InputStream is = mcontext.getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void GetText() throws JSONException {
        // Get user defined values
        strarea = areafarmadd.getText().toString().trim();
       /* strgpsc1=gpsc1farmadd.getText().toString().trim();
        strgpsc2=gpsc2farmadd.getText().toString().trim();
        strgpsc3=gpsc3farmadd.getText().toString().trim();
        strgpsc4=gpsc4farmadd.getText().toString().trim();
        strgpsc5=gpsc5farmadd.getText().toString().trim();
        strgpsc6=gpsc6farmadd.getText().toString().trim();*/
        strsoiltype = soiltypefarmadd.getText().toString().trim();
        strirrigationtype = irrigationtypefarmadd.getText().toString().trim();
        strspeccmnt = speccommentfarmadd.getText().toString().trim();
        straddl1 = addl1farmadd.getText().toString().trim();
        straddl2 = addl2farmadd.getText().toString().trim();
        steaddl3 = addl3farmadd.getText().toString().trim();
        strcity = cityfarmadd.getSelectedItem().toString();
        strcountry = citizenship.getSelectedItem().toString();
        strstate = statefarmadd.getSelectedItem().toString();
        strfarmpetname = farmpetname.getText().toString().trim();
        strsoiltypefromspinner = spin_soil_type.getSelectedItem().toString().trim();




        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("\"Successfully added Farm and Address\"")) {
                            progressDialog.dismiss();
                            Toast.makeText(FarmAddActivity.this, R.string.succefull_farm_addition, Toast.LENGTH_LONG).show();
                            SharedPreferencesMethod.setBoolean(context, "Login", true);
                            Intent intent = new Intent(context, LandingActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         Log.e("error",error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(FarmAddActivity.this,R.string.error_text, Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
               /* params.put(GPSC1,sfrstnamefill);
                params.put(GPSC2, smiddlenamefill);
                params.put(GPSC3, slastnamefill);
                params.put(GPSC4, sadharnofill);
                params.put(GPSC5, spannofill);
                params.put(GPSC6, sdobfill);*/
                if (strsoiltypefromspinner.equals("Other")) {
                    params.put(SOILTYPE, strsoiltype);
                } else {
                    params.put(SOILTYPE, strsoiltypefromspinner);
                }
                params.put(IRRIGATIONTYPE, strirrigationtype);
                params.put(SEPCCOMMENT, strspeccmnt);
                params.put(ADDL1, straddl1);
                params.put(ADDL2, straddl2);
                params.put(ADDL3, steaddl3);
                params.put(AREA, strarea);
                params.put(COUNTRY, strcountry);
                params.put(STATE, strstate);
                params.put(CITY, strcity);
                params.put(PERSONNUM, personnumfromfillprofile);
                params.put(USERNUM, usernumfrommainact);
                params.put(FARM_PET_NAME, strfarmpetname);
                params.put(KEY_TOKEN,ct1);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (soiltype[i].equals("Other")) {
            soiltypefarmadd.setVisibility(View.VISIBLE);
        } else {
            soiltypefarmadd.setVisibility(View.GONE);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}