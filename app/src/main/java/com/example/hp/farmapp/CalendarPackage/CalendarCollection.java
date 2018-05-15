package com.example.hp.farmapp.CalendarPackage;

import java.util.ArrayList;

public class CalendarCollection {
	public String date="";
	public String event_message="";
	public String farm_cal_mast_num="";
	public String farm_num="";
	
	public static ArrayList<CalendarCollection> date_collection_arr;
	public CalendarCollection(String date, String event_message,String farm_cal_mast_num,String farm_num){
	
		this.date=date;	
		this.event_message=event_message;
		this.farm_cal_mast_num=farm_cal_mast_num;
		this.farm_num=farm_num;
		
	}

}
