package com.example.hp.farmapp.CalendarPackage.CalendarTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.Adapter.RecyclerTouchListener;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.GetterSetter.Taskdata;
import com.example.hp.farmapp.CalendarPackage.DisplayCalendarActivity;
import com.example.hp.farmapp.DataHandler.DataHandler;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 15/12/17.
 */

public class ShowTaskActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    Context context;
    LinearLayoutManager mLayoutManager;
    TaskRecyclerViewAdapter mAdapter;
    String[] farmcalid,farmcalactivity,farmcaldate,farmcaldescription,farmcalimglink,farmcalisdone;
    List<Taskdata> taskdatums=new ArrayList<>();
    Taskdata taskdatum=new Taskdata();
    ProgressDialog mprogressDialog;
    private String REGISTER_URL;
    Toolbar mActionBarToolbar;
    String[] eventmsgs;
    String date;
    String farm_num;
    String farm_cal_mast_num;
    String type="";


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(context,DisplayCalendarActivity.class);
        startActivity(intent);
        finish();
        //super.onBackPressed();
        }

    private static final String REGISTER_URL_CALENDAR = "http://spade.farm/app/index.php/farmCalendar/send_task_list_by_date";

    final String TASK_DATE="task_date";
    final String KEY_FARM_NUM="farm_num";
    final String KEY_FARM_CAL_MAST_NUM="farm_cal_mast_num";
    final  String KEY_TOKEN="token4";
    String ct1="";

    //    public ToggleButton toggleButton;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* Intent intent=new Intent(context,LandingActivity.class);
        startActivity(intent);
        finish();*/
       /* Intent intent=new Intent(context,DisplayCalendarActivity.class);
        startActivity(intent);
        finish();*/
       super.onBackPressed();
        return super.onOptionsItemSelected(item);
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
        setContentView(R.layout.activity_show_task_view);
        //taskdatums=new ArrayList<>();
        View relativeLayout = findViewById(R.id.recycler_single_view_relative);

        TextView title=(TextView)findViewById(R.id.tittle);
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);

        //getSupportActionBar().setTitle("My Title");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        context=this;

        farm_num= SharedPreferencesMethod.getString(context,"farm_num");
        ct1=SharedPreferencesMethod.getString(context,"cctt");
        farm_cal_mast_num = DataHandler.newInstance().getFarm_cal_mast_num();
        date = DataHandler.newInstance().getEvent_date();
        title.setText("Task List");

        /*Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            type = extras.getString("Type");
        }*/

        /*extras.getString("Type");*/
        //Log.e("type",type);

//        if(type=="all_activities")
       /* if(type.equals("all_activities")){
            REGISTER_URL = REGISTER_URL_ALL;
            Log.e("enter","entered all");
            title.setText("My Activities");

        }*/
//        if(type=="pending_activities")
       /* if(type.equals("pending_activities")){
            REGISTER_URL = REGISTER_URL_PENDING;
            Log.e("enter","entered pending");
            title.setText("Pending Activities");

//            relativeLayout.setBackgroundColor(Color.parseColor("#FF5333"));
        }
*/

        if(type.equals("calendar_activity")){
            REGISTER_URL = REGISTER_URL_CALENDAR;
            Log.e("enter","entered calendar");
           /* SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                String reformattedStr = myFormat.format(fromUser.parse(Dob));
                Dob=reformattedStr;
                // Toast.makeText(EditProfileActivity.this, "*"+sdobfill+"*", Toast.LENGTH_SHORT).show();
            } catch (ParseException e) {
                e.printStackTrace();
            }
*/
            // Toast.makeText(context, date, Toast.LENGTH_SHORT).show();


        }

        init();

    }

    private void init() {
        //  preparedata();
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();
        context=this;
    }

    private void preparedata() {

    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {
        public AsyncTaskRunner() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL_CALENDAR,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONArray jsonarray = null;
                            try {
                                jsonarray = new JSONArray(response);

                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String id = jsonobject.getString("id");
                                    String activity=jsonobject.getString("activity");
                                    String activitydescription=jsonobject.getString("activity_description");
                                    String date=jsonobject.getString("date");
                                    String activity_img = jsonobject.getString("img_link");
                                    String is_done = jsonobject.getString("is_done");
                                    String farm_dwork_num=jsonobject.getString("farm_dwork_num");

                                    taskdatum = new Taskdata();
                                    taskdatum.setTaskDate(date);
                                    taskdatum.setTaskTitle(activity);
                                    taskdatum.setTaskDescription(activitydescription);
                                    taskdatum.setImgBgLink(activity_img);
                                    taskdatum.setIsDone(is_done);
                                    taskdatum.setTaskId(id);
                                    taskdatum.setFarm_dwork_num(farm_dwork_num);
                                    taskdatums.add(taskdatum);

                                    Log.e("Date :",date+"activity"+activity+taskdatums.size());

                                }
                                Log.e("TaskDatum:",String.valueOf(taskdatums.size()));
                                mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
                                mprogressDialog.dismiss();
                                mAdapter = new TaskRecyclerViewAdapter(taskdatums,context);
                                mRecyclerView.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();
                                mLayoutManager = new LinearLayoutManager(context);
                                mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                mRecyclerView.setLayoutManager(mLayoutManager);

                                mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        Taskdata taskdata=taskdatums.get(position);
                                        Intent intent = new Intent(context, FarmActionReplyActivity.class);
                                        intent.putExtra("id",taskdata.getFarm_dwork_num());
                                        intent.putExtra("task_date",taskdata.getTaskDate());
                                        intent.putExtra("farm_num",farm_num);
                                        intent.putExtra("type","from_calendar");
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
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
                    if(date!=null) {
                        params.put(TASK_DATE, date);
                    }
                    if(farm_num!=null){
                        params.put(KEY_FARM_NUM,farm_num);
                    }

                    if(farm_cal_mast_num!=null){
                        params.put(KEY_FARM_CAL_MAST_NUM,farm_cal_mast_num);
                    }
                    if(ct1!=null){
                        params.put(KEY_TOKEN,ct1);
                    }
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

            return null;
        }


        @Override
        protected void onPreExecute() {
            mprogressDialog =new ProgressDialog(context);
            mprogressDialog.setMessage("Loading....");
            mprogressDialog.show();
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