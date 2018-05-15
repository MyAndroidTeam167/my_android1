package com.example.hp.farmapp.CalendarPackage.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.hp.farmapp.CalendarPackage.CalendarCollection;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.FarmActionReplyActivity;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.ShowTaskActivity;
import com.example.hp.farmapp.CalendarPackage.EventDisplayActivity;
import com.example.hp.farmapp.DataHandler.DataHandler;
import com.example.hp.farmapp.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class CalendarAdapter extends BaseAdapter {
	private Context context;

	private java.util.Calendar month;
	public GregorianCalendar pmonth;
	/**
	 * calendar instance for previous month for getting complete view
	 */
	public GregorianCalendar pmonthmaxset;
	private GregorianCalendar selectedDate;
	int firstDay;
	int maxWeeknumber;
	int maxP;
	public String[] eventmsgs;
	int flag = 0;
	int count = 0;
	int calMaxP;
	int lastWeekDay;
	int leftDays;
	int mnthlength;
	String itemvalue, curentDateString;
	DateFormat df;

	private ArrayList<String> items;
	String[] eventmsglist;
	public static List<String> day_string;
	private View previousView;
	public ArrayList<CalendarCollection> date_collection_arr;

	public CalendarAdapter(Context context, GregorianCalendar monthCalendar, ArrayList<CalendarCollection> date_collection_arr) {
		this.date_collection_arr = date_collection_arr;
		CalendarAdapter.day_string = new ArrayList<String>();
		Locale.setDefault(Locale.US);
		month = monthCalendar;
		selectedDate = (GregorianCalendar) monthCalendar.clone();
		this.context = context;
		month.set(GregorianCalendar.DAY_OF_MONTH, 1);

		this.items = new ArrayList<String>();
		df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		curentDateString = df.format(selectedDate.getTime());
		refreshDays();

	}

	public void setItems(ArrayList<String> items) {
		for (int i = 0; i != items.size(); i++) {
			if (items.get(i).length() == 1) {
				items.set(i, "0" + items.get(i));
			}
		}
		this.items = items;
	}

	public int getCount() {
		return day_string.size();
	}

	public Object getItem(int position) {
		return day_string.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new view for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		TextView dayView;
		if (convertView == null) { // if it's not recycled, initialize some
			// attributes
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.cal_item, null);

		}


		dayView = (TextView) v.findViewById(R.id.date);
		String[] separatedTime = day_string.get(position).split("-");


		String gridvalue = separatedTime[2].replaceFirst("^0*", "");
		if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
			dayView.setTextColor(Color.GRAY);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		}else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
			dayView.setTextColor(Color.GRAY);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		}else {
			// setting curent month's days in blue color.
			dayView.setTextColor(Color.GRAY);
		}
		if(day_string.get(position).equals(curentDateString)) {
			v.setBackgroundColor(Color.parseColor("#FC6C55"));
			dayView.setTextColor(Color.WHITE);
//			v.setBackgroundColor(Color.CYAN);
		} else {
			v.setBackgroundColor(Color.parseColor("#FFFFFF"));
		}

		dayView.setText(gridvalue);

		// create date string for comparison
		String date = day_string.get(position);

		if (date.length() == 1) {
			date = "0" + date;
		}
		String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
		if (monthStr.length() == 1) {
			monthStr = "0" + monthStr;
		}

		// show icon if date is not empty and it exists in the items array
        /*ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
		if (date.length() > 0 && items != null && items.contains(date)) {
			iw.setVisibility(View.VISIBLE);
		} else {
			iw.setVisibility(View.GONE);
		}
		*/

		setEventView(v, position, dayView);

		return v;
	}

	public View setSelected(View view, int pos) {
		if (previousView != null) {
			previousView.setBackgroundColor(Color.parseColor("#FFFFFF"));
			TextView dayView = (TextView) previousView.findViewById(R.id.date);
			dayView.setTextColor(Color.GRAY);
			//this line sets the color of item after being touched.
		}

//		view.setBackgroundColor(Color.CYAN);
		view.setBackgroundColor(Color.parseColor("#FC6C55"));
		TextView dayView = (TextView) view.findViewById(R.id.date);
       dayView.setTextColor(Color.WHITE);

		int len = day_string.size();
		if (len > pos) {
			if (day_string.get(pos).equals(curentDateString)) {

			} else {

				previousView = view;

			}

		}


		return view;
	}

	public void refreshDays() {
		// clear items
		items.clear();
		day_string.clear();
		Locale.setDefault(Locale.US);
		pmonth = (GregorianCalendar) month.clone();
		// month start day. ie; sun, mon, etc
		firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
		// finding number of weeks in current month.
		maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
		// allocating maximum row number for the gridview.
		mnthlength = maxWeeknumber * 7;
		maxP = getMaxP(); // previous month maximum day 31,30....
		calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
		/**
		 * Calendar instance for getting a complete gridview including the three
		 * month's (previous,current,next) dates.
		 */
		pmonthmaxset = (GregorianCalendar) pmonth.clone();
		/**
		 * setting the start date as previous month's required date.
		 */
		pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

		/**
		 * filling calendar gridview.
		 */
		for (int n = 0; n < mnthlength; n++) {

			itemvalue = df.format(pmonthmaxset.getTime());
			pmonthmaxset.add(GregorianCalendar.DATE, 1);
			day_string.add(itemvalue);
		}
	}

	private int getMaxP() {
		int maxP;
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			pmonth.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}
		maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

		return maxP;
	}


	public void setEventView(View v, int pos, TextView txt) {

		int len = CalendarCollection.date_collection_arr.size();
		for (int i = 0; i < len; i++) {
			CalendarCollection cal_obj = CalendarCollection.date_collection_arr.get(i);
			String date = cal_obj.date;
			String farm_cal_mast_num=cal_obj.farm_cal_mast_num;
			String farm_num=cal_obj.farm_num;
			int len1 = day_string.size();
			if (len1 > pos) {

				if (day_string.get(pos).equals(date)) {
					v.setBackgroundColor(Color.parseColor("#FC6C55"));
					v.setBackgroundResource(R.drawable.rounded_calender_item);
					txt.setTextColor(Color.WHITE);
				}
			}
		}
	}

	public void getPositionList(String date, final Activity act) {

		int len = CalendarCollection.date_collection_arr.size();

		Log.d("length", "len :" + len);


		eventmsgs = new String[10];

		String event_date_final = "";
		String farm_num="";
		 String farm_cal_mast_num = "";

		for (int i = 0; i < len; i++) {

			CalendarCollection cal_collection = CalendarCollection.date_collection_arr.get(i);

			final String event_date = cal_collection.date;

			final String event_message = cal_collection.event_message;

			farm_cal_mast_num=cal_collection.farm_cal_mast_num;

			farm_num =cal_collection.farm_num;

//          final String eventdate="";

//          final String mesage="";

//            int count_date = 0;

			Log.e("cal_coll", event_date);

			Log.e("cal_coll", event_message);

			Log.e("cal_coll",farm_cal_mast_num);
			Log.e("cal_coll",farm_num);




/*//          if (date.equals(event_date)) {
              *//*for (int j = 0; j < len; j++) {
                  if(date.equals(event_date)){
                      eventmsgs[j]=cal_collection.event_message;
                  }
                  }*//*
            Toast.makeText(context, "You have event on this date: "+event_date, Toast.LENGTH_LONG).show();
           new AlertDialog.Builder(context)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Date: "+event_date)
             .setMessage("Event: "+event_message)
              .setPositiveButton("OK",new DialogInterface.OnClickListener(){
              public void onClick(DialogInterface dialog, int which)
              {
                  Intent intent=new Intent(context, MainActivity.class);
                  intent.putExtra("eventdate",event_date);
                    intent.putExtra("evntmsg",event_message);
                    intent.putExtra("evntmsg",eventmsgs[2]);
                  context.startActivity(intent);
                                }
                                              }).show();

            //Here this break statement is breaking the loop after one event is shown for that particular date
            //and therefore other events of the same date are unable to load. so for now I am commenting it out.
          break;*/
			if (date.equals(event_date)) {
				event_date_final = event_date;
				eventmsgs[count] = cal_collection.event_message;
				count++;
				flag = 1;
			}
		}


		DataHandler.newInstance().setActivityfarmcal(eventmsgs);
		DataHandler.newInstance().setMsgcount(count);

		if (flag == 1) {
			for (int j = 0; j < count; j++) {
				Log.e("ARRAY", "" + eventmsgs[j]);
			}
			Intent intent = new Intent(context, ShowTaskActivity.class);
			//intent.putExtra("eventdate", event_date_final);
			//intent.putExtra("evntmsg", eventmsgs);
			//intent.putExtra("msgCount", count);
			//intent.putExtra("farm_num",farm_num);
			DataHandler.newInstance().setFarm_cal_mast_num(farm_cal_mast_num);
			DataHandler.newInstance().setEvent_date(event_date_final);
			//intent.putExtra("farm_cal_mast_num",farm_cal_mast_num);
			intent.putExtra("Type", "calendar_activity");
//          intent.putExtra("evntmsg",eventmsgs[2]);
			context.startActivity(intent);
			((Activity) context).finish();
		} else {
		}
	}
}

