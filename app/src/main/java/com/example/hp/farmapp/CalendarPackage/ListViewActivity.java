package com.example.hp.farmapp.CalendarPackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.example.hp.farmapp.CalendarPackage.Adapter.AndroidListAdapter;
import com.example.hp.farmapp.CalendarPackage.LandingActivity.LandingActivity;
import com.example.hp.farmapp.R;

import java.util.ArrayList;

public class ListViewActivity extends Activity implements OnClickListener {

	private ListView lv_android;
	private AndroidListAdapter list_adapter;
	private Button btn_calender;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);
		Window window = this.getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			window.setStatusBarColor(ContextCompat.getColor(this, R.color.green_new));
		}
		/*CalendarCollection.date_collection_arr=new ArrayList<CalendarCollection>();
		CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-01","Seed sowing"));
		CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-10","Basel dose"));
		CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-15","Herbicide spray"));
		CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-21","weed removel (if needed)"));
		CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-21","3rd Dose fertilizer"));
		CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-21","Pesticide spray"));
		CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-12-03","4th Dose fertilizer"));
		CalendarCollection.date_collection_arr.add(new CalendarCollection("2017-11-10","5th Dose fertilizer"));
		getWidget();*/
	}

	
	
	public void getWidget(){
		btn_calender = (Button) findViewById(R.id.btn_calender);
		btn_calender.setOnClickListener(this);
		
		lv_android = (ListView) findViewById(R.id.lv_android);
		list_adapter=new AndroidListAdapter(ListViewActivity.this,R.layout.list_item, CalendarCollection.date_collection_arr);
		lv_android.setAdapter(list_adapter);
		
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_calender:
			startActivity(new Intent(ListViewActivity.this,LandingActivity.class));
			finish();
			break;
		default:
			break;
		}
	}
}
