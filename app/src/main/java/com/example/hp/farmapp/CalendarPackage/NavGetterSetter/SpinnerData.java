package com.example.hp.farmapp.CalendarPackage.NavGetterSetter;

/**
 * Created by user on 2/1/18.
 */

public class SpinnerData {
    String item_name;
    String item_value;
    String gps_cordinate;

    String insp_profile_img;
    String inspector_name;
    String inspector_mob_no;

    public String getInspector_mob_no() {
        return inspector_mob_no;
    }

    public void setInspector_mob_no(String inspector_mob_no) {
        this.inspector_mob_no = inspector_mob_no;
    }

    public String getInsp_profile_img() {
        return insp_profile_img;
    }

    public void setInsp_profile_img(String insp_profile_img) {
        this.insp_profile_img = insp_profile_img;
    }

    public String getInspector_name() {
        return inspector_name;
    }

    public void setInspector_name(String inspector_name) {
        this.inspector_name = inspector_name;
    }

    public String getGps_cordinate() {
        return gps_cordinate;
    }

    public void setGps_cordinate(String gps_cordinate) {
        this.gps_cordinate = gps_cordinate;
    }

    public void setItem_name(String item_name){
        this.item_name = item_name;
    }
    public void setItem_value(String item_value){
        this.item_value = item_value;
    }
    public String getItem_name(){
        return item_name;
    }
    public String getItem_value(){
        return item_value;
    }
}
