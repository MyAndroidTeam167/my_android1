package com.example.hp.farmapp.DataHandler;

/**
 * Created by hp on 8/28/2017.
 */
public class DataHandler {


    String loginEmail;
    String loginmobileno;
    Boolean signUp;
    String signUpmail;
    String usernumber;
    String signupMobile;
    String message="";
    String password;
    String signUpPassword;
    String moborEmailonforget;
    String passwordonfrgt;
    String[] datefarmcal;
    String[] activityfarmcal;
    String[] activitydescriptionfarmcal;
    int msgcount;
    String personnum;
    String fromActivty;

    public String getFromActivty() {
        return fromActivty;
    }

    public void setFromActivty(String fromActivty) {
        this.fromActivty = fromActivty;
    }

    public String getPersonnum() {
        return personnum;
    }

    public void setPersonnum(String personnum) {
        this.personnum = personnum;
    }

    public int getMsgcount() {
        return msgcount;
    }

    public void setMsgcount(int msgcount) {
        this.msgcount = msgcount;
    }

    public String[] getActivityfarmcal() {
        return activityfarmcal;
    }

    public void setActivityfarmcal(String[] activityfarmcal) {
        this.activityfarmcal = activityfarmcal;
    }

    public String[] getActivitydescriptionfarmcal() {
        return activitydescriptionfarmcal;
    }

    public void setActivitydescriptionfarmcal(String[] activitydescriptionfarmcal) {
        this.activitydescriptionfarmcal = activitydescriptionfarmcal;
    }

    public String[] getDatefarmcal() {
        return datefarmcal;
    }

    public void setDatefarmcal(String[] datefarmcal) {
        this.datefarmcal = datefarmcal;
    }


    public String getPasswordonfrgt() {
        return passwordonfrgt;
    }

    public void setPasswordonfrgt(String passwordonfrgt) {
        this.passwordonfrgt = passwordonfrgt;
    }

    public String getMoborEmailonforget() {
        return moborEmailonforget;
    }

    public void setMoborEmailonforget(String moborEmailonforget) {
        this.moborEmailonforget = moborEmailonforget;
    }

    public String getSignUpPassword() {
        return signUpPassword;
    }

    public void setSignUpPassword(String signUpPassword) {
        this.signUpPassword = signUpPassword;
    }

    public Boolean getSignUp() {
        return signUp;
    }

    public void setSignUp(Boolean signUp) {
        this.signUp = signUp;
    }


    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }


    public String getSignUpmail() {
        return signUpmail;
    }

    public void setSignUpmail(String signUpmail) {
        this.signUpmail = signUpmail;
    }

    public String getSignupMobile() {
        return signupMobile;
    }

    public void setSignupMobile(String signupMobile) {
        this.signupMobile = signupMobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getLoginmobileno() {
        return loginmobileno;
    }

    public void setLoginmobileno(String loginmobileno) {
        this.loginmobileno = loginmobileno;
    }

    private static DataHandler datahandler=null;
    public static DataHandler newInstance(){
        if(datahandler==null){
            datahandler=new DataHandler();
        }
        return datahandler;
    }
}
