package com.example.hp.farmapp.CalendarPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hp.farmapp.CalendarPackage.LandingActivity.LandingActivity;
import com.example.hp.farmapp.DataHandler.DataHandler;
import com.example.hp.farmapp.LangBaseActivity.BaseActivity;
import com.example.hp.farmapp.R;

import java.util.ArrayList;
import java.util.List;

public class EventDisplayActivity extends BaseActivity {


    String[] eventmsgs;
    private ListView lv;
    int count;
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,LandingActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_display);
        lv = (ListView) findViewById(R.id.your_list_view_id);
        eventmsgs= DataHandler.newInstance().getActivityfarmcal();
        count=DataHandler.newInstance().getMsgcount();
        //Toast.makeText(EventDisplayActivity.this, eventmsgs.length, Toast.LENGTH_SHORT).show();
        List<String> your_array_list = new ArrayList<String>();
        for(int i=0;i<count;i++){
            your_array_list.add(eventmsgs[i]);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        lv.setAdapter(arrayAdapter);


    }
}
