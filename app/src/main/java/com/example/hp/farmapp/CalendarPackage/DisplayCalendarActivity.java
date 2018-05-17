package com.example.hp.farmapp.CalendarPackage;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hp.farmapp.CalendarPackage.Adapter.CalendarAdapter;
import com.example.hp.farmapp.CalendarPackage.LandingActivity.LandingActivity;
import com.example.hp.farmapp.R;
import java.util.GregorianCalendar;

public class DisplayCalendarActivity extends AppCompatActivity {
    public GregorianCalendar cal_month, cal_month_copy;
    private CalendarAdapter cal_adapter;
    private TextView tv_month;
    Context context;
    Toolbar mActionBarToolbar;



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent=new Intent(context,LandingActivity.class);
        startActivity(intent);
        finish();
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
        setContentView(R.layout.activity_display_calendar);
        context=this;

        TextView title=(TextView)findViewById(R.id.tittle);
        title.setText(R.string.calendar_title);
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);

        //getSupportActionBar().setTitle("My Title");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }



        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        cal_adapter = new CalendarAdapter(this, cal_month, CalendarCollection.date_collection_arr);



        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));

        ImageButton previous = (ImageButton) findViewById(R.id.ib_prev);

        previous.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        ImageButton next = (ImageButton) findViewById(R.id.Ib_next);
        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });

        GridView gridview = (GridView) findViewById(R.id.gv_calendar);
        gridview.setAdapter(cal_adapter);
        cal_adapter.notifyDataSetChanged();
        gridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                ((CalendarAdapter) parent.getAdapter()).setSelected(v,position);
                String selectedGridDate = CalendarAdapter.day_string
                        .get(position);

                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*","");
                int gridvalue = Integer.parseInt(gridvalueString);

                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }
                ((CalendarAdapter) parent.getAdapter()).setSelected(v,position);
                ((CalendarAdapter) parent.getAdapter()).getPositionList(selectedGridDate, DisplayCalendarActivity.this);
            }

        });



    }

    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1),
                    cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(context,LandingActivity.class);
        startActivity(intent);
        finish();
    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1),
                    cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) - 1);
        }

    }

    public void refreshCalendar() {
        cal_adapter.refreshDays();
        cal_adapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }

}
