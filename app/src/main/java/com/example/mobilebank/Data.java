package com.example.mobilebank;

import android.app.Application;

public class Data extends Application {

    private String currentacc;
    private String currbankcard;
    private String currPayid;
    private int lastview;
    private String year;
    private Double fee;

    public String getcurrbankcard(){
        return this.currbankcard;
    }
    public void setcurrbankcard(String change){
        this.currbankcard= change;
    }

    public String getCurrPayid(){
        return this.currPayid;
    }
    public void setCurrPayid(String change){
        this.currPayid= change;
    }

    public String getcurrentuser(){
        return this.currentacc;
    }
    public void setcurrentuser(String change){
        this.currentacc= change;
    }

    public int getlastview(){
        return this.lastview;
    }
    public void setlastview(int change){
        this.lastview= change;
    }

    public Double getfee(){
        return this.fee;
    }
    public void setfee(Double change){
        this.fee= change;
    }

    public String getyear(){
        return this.year;
    }
    public void setyear(String change){
        this.year= change;
    }

    @Override
    public void onCreate(){
        currentacc = "hello";
        lastview = -1;
        fee = 0.0;
        super.onCreate();
    }

}
