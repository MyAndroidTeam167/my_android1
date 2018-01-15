package com.example.hp.farmapp.Beans;

/**
 * Created by hp on 9/13/2017.
 */
public class GetProfile {
    String activity;
    String activitydescription;
    String notification;
    int _id;
    int yeartpeid;

    public String getActivitydescription() {
        return activitydescription;
    }

    public void setActivitydescription(String activitydescription) {
        this.activitydescription = activitydescription;
    }

    public GetProfile(String activity, String activitydescription) {
        this.activity = activity;
this.activitydescription=activitydescription;    }

    public int getYeartpeid() {
        return yeartpeid;
    }

    public void setYeartpeid(int yeartpeid) {
        this.yeartpeid = yeartpeid;
    }

    public GetProfile(int id, String notification) {
        this._id = id;
        this.notification = notification;

    }

    public GetProfile(){}

    public GetProfile(String notification){
        this.notification=notification;
    }


    public String getNotification() {
        return this.notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public int get_id() {
        return this._id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

}
