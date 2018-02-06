package com.example.hp.farmapp.CalendarPackage.CalendarTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.Adapter.RecyclerTouchListener;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.GetterSetter.Taskdata;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Weather.WeatherFragments.Weather_firstFragment;
import com.example.hp.farmapp.Weather.WeatherFragments.Weather_fourthFragment;
import com.example.hp.farmapp.Weather.WeatherFragments.Weather_secondFragment;
import com.example.hp.farmapp.Weather.WeatherFragments.Weather_thirdFragment;
import com.example.hp.farmapp.Weather.WeatherViewPagerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowTaskViewPagerActivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;
    Context context;
    private static final String REGISTER_URL_ALL = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmCalendar/send_farm_calendar_column_data_to_app";
    private static final String REGISTER_URL_PENDING = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmCalendar/send_farm_calendar_pending_to_app";
    private static final String REGISTER_URL_CALENDAR = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmCalendar/send_task_list_by_date";
   /* List<Taskdata> taskdatumsall=new ArrayList<>();
    List<Taskdata> taskdatumspending=new ArrayList<>();
    Taskdata taskdatumall=new Taskdata();
    Taskdata taskdatumpending=new Taskdata();*/
    ProgressDialog mprogressDialog;
    private String REGISTER_URL;
    final String TASK_DATE="task_date";
    String[] dateall,idall,activityall,activitydescriptionall,activity_imgall,is_doneall;
    String[] datepending,idpending,activitypending,activitydescriptionpending,activity_imgpending,is_donepending;
            String date;
    String url;
    ViewPager pager;





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Intent intent=new Intent(context,LandingActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {
        /*Intent intent=new Intent(context,LandingActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task_view_pager);
        context=this;
        TextView title=(TextView)findViewById(R.id.tittle);
        title.setText("Activities");
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);

//        getSupportActionBar().setTitle("My Title");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

         mprogressDialog =new ProgressDialog(context);
            mprogressDialog.setMessage("Loading....");
            mprogressDialog.show();
        url=REGISTER_URL_ALL;
        AsyncTaskRunner runner=new AsyncTaskRunner();
        runner.execute(url,"All");

        url=REGISTER_URL_PENDING;
        AsyncTaskRunner runnerpending=new AsyncTaskRunner();
        runnerpending.execute(url,"Pending");

       /* url=REGISTER_URL_CALENDAR;
        AsyncTaskRunner runnercal=new AsyncTaskRunner();
        runnercal.execute(url);*/
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 3;
        private String tabTitles[] = new String[]{"Tab1", "Tab2"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            tabTitles = new String[]{"All Activities", "Pending Activities"};
            switch (pos) {

                case 0: return Weather_thirdFragment.newInstance(dateall,idall,activityall,activitydescriptionall,activity_imgall,is_doneall);
                case 1: return Weather_fourthFragment.newInstance(datepending,idpending,activitypending,activitydescriptionpending,activity_imgpending,is_donepending);
                default: return Weather_thirdFragment.newInstance(dateall,idall,activityall,activitydescriptionall,activity_imgall,is_doneall);

                /*case 0: return Weather_firstFragment.newInstance(hightemparr[0],lowtemparr[0],dayarr[0],conditionarr[0],iconarr[0],windstringtosend,tempinceltosend,relhumiditytosend,citystatecountry,observaiontime_tosend,pos+1,daynumarr[0],montharr[0],yeararr[0]);
                case 1: return Weather_secondFragment.newInstance(hightemparr[1],lowtemparr[1],dayarr[1],conditionarr[1],iconarr[1],citystatecountry,observaiontime_tosend,pos+1,daynumarr[1],montharr[1],yeararr[1]);
                case 2: return Weather_secondFragment.newInstance(hightemparr[2],lowtemparr[2],dayarr[2],conditionarr[2],iconarr[2],citystatecountry,observaiontime_tosend,pos+1,daynumarr[2],montharr[2],yeararr[2]);
                case 3: return Weather_secondFragment.newInstance(hightemparr[3],lowtemparr[3],dayarr[3],conditionarr[3],iconarr[3],citystatecountry,observaiontime_tosend,pos+1,daynumarr[3],montharr[3],yeararr[3]);
                    *//*case 2: return Wheather_thirdFragment.newInstance("ThirdFragment, Instance 1");
                    case 3: return Wheather_fourthFragment.newInstance("FourthFragment, Instance 2");
                    case 4: return Wheather_fifthFragment.newInstance("FifthFragment, Instance 3");
                    case 5: return Wheather_sixthFragment.newInstance("SixthFragment, Instance 3");
                    case 6: return Wheather_seventhFragment.newInstance("SixthFragment, Instance 3");*//*
                default: return Weather_firstFragment.newInstance(hightemparr[0],lowtemparr[0],dayarr[0],conditionarr[0],iconarr[0],windstringtosend,tempinceltosend,relhumiditytosend,citystatecountry,observaiontime_tosend,pos+1,dayarr[0],montharr[0],yeararr[0]);
*/

            }
        }
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }


    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {
        public AsyncTaskRunner() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            final String urlonrecieve,typeonrecieve;
            urlonrecieve=params[0];
            typeonrecieve=params[1];


            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlonrecieve,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {



                            if(typeonrecieve.equals("All")){
                                JSONArray jsonarray = null;
                                try {
                                    jsonarray = new JSONArray(response);

                                    dateall=new String[jsonarray.length()];
                                    idall=new String[jsonarray.length()];
                                    activityall=new String[jsonarray.length()];
                                    activitydescriptionall=new String[jsonarray.length()];
                                    activity_imgall=new String[jsonarray.length()];
                                    is_doneall=new String[jsonarray.length()];


                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                         idall[i] = jsonobject.getString("id");
                                         activityall[i]=jsonobject.getString("activity");
                                         activitydescriptionall[i]=jsonobject.getString("activity_description");
                                         dateall[i]=jsonobject.getString("date");
                                         activity_imgall[i] = jsonobject.getString("img_link");
                                         is_doneall[i] = jsonobject.getString("is_done");

                                        Log.e("Date All :",dateall[i]+"activity All"+activityall[i]);

                                   /*     taskdatumall = new Taskdata();
                                        taskdatumall.setTaskDate(date);
                                        taskdatumall.setTaskTitle(activity);
                                        taskdatumall.setTaskDescription(activitydescription);
                                        taskdatumall.setImgBgLink(activity_img);
                                        taskdatumall.setIsDone(is_done);
                                        taskdatumall.setTaskId(id);
                                        taskdatumsall.add(taskdatumall);   */
                                       // Log.e("Date :",date+"activity"+activity+taskdatumsall.size());

                                    }

                                    mprogressDialog.dismiss();
                                    //Log.e("TaskDatumall:",String.valueOf(taskdatumsall.size()));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    mprogressDialog.dismiss();

                                }

                                }


                                else if(typeonrecieve.equals("Pending")){
                                JSONArray jsonarray = null;
                                try {
                                    jsonarray = new JSONArray(response);
                                    datepending=new String[jsonarray.length()];
                                    idpending=new String[jsonarray.length()];
                                    activitypending=new String[jsonarray.length()];
                                    activitydescriptionpending=new String[jsonarray.length()];
                                    activity_imgpending=new String[jsonarray.length()];
                                    is_donepending=new String[jsonarray.length()];


                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                         idpending[i] = jsonobject.getString("id");
                                         activitypending[i]=jsonobject.getString("activity");
                                         activitydescriptionpending[i]=jsonobject.getString("activity_description");
                                         datepending[i]=jsonobject.getString("date");
                                         activity_imgpending[i] = jsonobject.getString("img_link");
                                         is_donepending[i] = jsonobject.getString("is_done");

                                        Log.e("Date Pending :",datepending[i]+"activity Pending"+activitypending[i]);


                                       /* taskdatumpending = new Taskdata();
                                        taskdatumpending.setTaskDate(date);
                                        taskdatumpending.setTaskTitle(activity);
                                        taskdatumpending.setTaskDescription(activitydescription);
                                        taskdatumpending.setImgBgLink(activity_img);
                                        taskdatumpending.setIsDone(is_done);
                                        taskdatumpending.setTaskId(id);
                                        taskdatumspending.add(taskdatumpending);*/

                                       // Log.e("Date :",date+"activity"+activity+taskdatumspending.size());

                                    }
                                  /*  pager = (ViewPager) findViewById(R.id.viewPager);
                                    pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));*/
                                    mprogressDialog.dismiss();
                                   // Log.e("TaskDatumPending:",String.valueOf(taskdatumspending.size()));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    mprogressDialog.dismiss();

                                }


                            }


                            if(datepending!=null && dateall!=null) {
                                    Log.e("coming here","in pager adapter");
                                mprogressDialog.dismiss();
                                pager = (ViewPager) findViewById(R.id.viewPager);
                                pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                            }


                                /*mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
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
                                        //Toast.makeText(getApplicationContext()," "+taskdatums.get(position), Toast.LENGTH_SHORT).show();
                                        Taskdata taskdata=taskdatums.get(position);
//                                        Toast.makeText(getApplicationContext(),"Description ->"+taskdata.getTaskDescription()+", Id ->"+taskdata.getTaskId(),Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(context, FarmActionReplyActivity.class);
                                        intent.putExtra("id",taskdata.getTaskId());
                                        intent.putExtra("task_date",taskdata.getTaskDate());
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));*/

                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mprogressDialog.dismiss();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    if(date!=null) {
                        params.put(TASK_DATE, date);
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
           /* mprogressDialog =new ProgressDialog(context);
            mprogressDialog.setMessage("Loading....");
            mprogressDialog.show();*/
        }

        @Override
        protected void onPostExecute(String s) {
           // mprogressDialog.dismiss();

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
