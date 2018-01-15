package com.example.hp.farmapp.CalendarPackage.CalendarTask;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.Adapter.RecyclerTouchListener;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.GetterSetter.Taskdata;
import com.example.hp.farmapp.CalendarPackage.LandingActivity;
import com.example.hp.farmapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FarmActionReplyActivity extends AppCompatActivity {
    Toolbar mActionBarToolbar;
    Context context;
    ImageView imgView;
    CheckBox mCheckBox;
    Button mButton;
    EditText mEditText;
    String sTaskStatus;
    final String DATA_ID = "data_id";
    final String DATA_TASK_DATE = "data_task_date";
    final String DATA_TASK_STATUS = "task_done_status";
    final String FARMER_REPLY = "farmer_reply";
    String fetch_id, description,farmerReply, chemical, chemical_qty, compulsary, activity, img_link, done, date_of_task;
    String REGISTER_URL = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmCalendar/fetch_farm_day_data_by_id";
    String SAVE_FARMER_RESPONSE_URL = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmCalendar/set_farmer_reponse";
    TextView tvFarmerReply, tvactivityName, tvactivityDescription, tvactivityDate, tvchemical, tvqtychemical, tvCompulsary;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent intent=new Intent(context,LandingActivity.class);
//        startActivity(intent);
//        finish();
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_action_reply);
        Log.e("check", "reached onCreate");
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        context = this;

        TextView title = (TextView) findViewById(R.id.tittle);
        title.setText("Activity");
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);

        tvactivityName = (TextView) findViewById(R.id.reply_activity_name_value);
        tvactivityDescription = (TextView) findViewById(R.id.reply_activity_description_value);
        tvactivityDate = (TextView) findViewById(R.id.reply_activity_date_value);
        tvchemical = (TextView) findViewById(R.id.reply_activity_chemical_name_value);
        tvqtychemical = (TextView) findViewById(R.id.reply_activity_quantity_value);
        tvCompulsary = (TextView) findViewById(R.id.reply_activity_compulsary_value);
        imgView = (ImageView) findViewById(R.id.img_view);
        mCheckBox = (CheckBox) findViewById(R.id.reply_done_status);
        mButton = (Button) findViewById(R.id.button_submit);
        mEditText = (EditText) findViewById(R.id.et_farmer_reply);
        tvFarmerReply = (TextView) findViewById(R.id.tv_farmer_reply);

        //getSupportActionBar().setTitle("My Title");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("id");
        final String task_date = extras.getString("task_date");
//        Toast.makeText(getApplicationContext()," "+id+" "+task_date,Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Log.e("check","got response");
                            JSONObject jobject = null;
                            Log.e("check", "reached try block");
                            jobject = new JSONObject(response);
                            fetch_id = jobject.getString("id");
                            activity = jobject.getString("activity");
                            compulsary = jobject.getString("is_compulsary");
                            img_link = jobject.getString("img_link");
                            chemical = jobject.getString("chemical_to_use");
                            chemical_qty = jobject.getString("quantity_of_chemical");
                            done = jobject.getString("is_done");
                            date_of_task = jobject.getString("date");
                            description = jobject.getString("activity_description");
                            farmerReply = jobject.getString("farmer_reply");
                            Log.e("Check", "id" + id + " " + "img_link" + " " + img_link);
                            tvactivityName.setText(activity);
                            tvactivityDescription.setText(description);
                            tvactivityDate.setText(date_of_task);
                            tvchemical.setText(chemical);
                            tvqtychemical.setText(chemical_qty + " lit/acre");
                            if (compulsary.equals("Y")) {
                                tvCompulsary.setText("Yes");
                            } else {
                                tvCompulsary.setText("No");
                            }
                            Uri uri = Uri.parse(img_link);
                            Picasso.with(context).load(uri).into(imgView);
                            if (done.equals("Y")) {
                                mCheckBox.setChecked(true);
                                mCheckBox.setClickable(false);
                                mButton.setClickable(false);
                                mButton.setBackgroundColor(Color.parseColor("#808080"));
                                mEditText.setVisibility(View.GONE);
                                tvFarmerReply.setText(farmerReply);
                            } else {
                                mCheckBox.setChecked(false);
                                tvFarmerReply.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                if (id != null) {
                    params.put(DATA_ID, id);
                    Log.e("check", "id passed" + " " + id);
                }
                if (task_date != null) {
                    params.put(DATA_TASK_DATE, task_date);
                    Log.e("check", "date passed" + " " + task_date);
                }
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setMessage("Want to submit your response?");
                alertDialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if(mCheckBox.isChecked()){
                            sTaskStatus = "Y";
                        }else{
                            sTaskStatus = "N";
                        }

                        final String response = mEditText.getText().toString().trim();
                        if(!isResponseFilled(response)){
                            dialog.cancel();
                            Toast.makeText(context,"Please Enter Comment",Toast.LENGTH_LONG).show();
                            mEditText.setError("Enter Reponse");

                        }else {
                            StringRequest stringRequestNew = new StringRequest(Request.Method.POST, SAVE_FARMER_RESPONSE_URL,
                                    new com.android.volley.Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jobject = null;
                                                jobject = new JSONObject(response);
                                                Log.e("check", "today date " + jobject.getString("today_date"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    },
                                    new com.android.volley.Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                        }
                                    }) {

                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    if (response != null) {
                                        params.put(FARMER_REPLY, response);
                                        Log.e("check", "REached params response = " + response);
                                    }
                                    if (sTaskStatus != null) {
                                        params.put(DATA_TASK_STATUS, sTaskStatus);
                                    }
                                    if (fetch_id != null) {
                                        params.put(DATA_ID, fetch_id);
                                    }
                                    if (date_of_task != null) {
                                        params.put(DATA_TASK_DATE, date_of_task);
                                    }
                                    return params;
                                }
                            };

                            RequestQueue requestQueueNew = Volley.newRequestQueue(context);
                            requestQueueNew.add(stringRequestNew);

                            Toast.makeText(context, "Action Submitted", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(context,LandingActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        finish();
                        dialog.cancel();
                    }
                });

                // Create the AlertDialog object and return it
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
       /* Uri uri = Uri.parse();
        Picasso.with(context).load(uri).into(imgView);*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean isResponseFilled(String text){
        boolean flag = true;
        if(text.isEmpty()){
            flag=false;
        }
        if(text.equals("null")){
            flag=false;
        }
        if(text.equals(" ")){
            flag=false;
        }
        return flag;
    }
}
