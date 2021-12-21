package com.example.mobilebank;

import android.app.Application;

public class Data extends Application {

    private String currentacc;
    private String currbankcard;
    private String currschoolcard;

    public String getcurrbankcard(){
        return this.currbankcard;
    }
    public void setcurrbankcard(String change){
        this.currbankcard= change;
    }

    public String getcurrschoolcard(){
        return this.currschoolcard;
    }
    public void setcurrschoolcard(String change){
        this.currschoolcard= change;
    }

    public String getcurrentuser(){
        return this.currentacc;
    }
    public void setcurrentuser(String change){
        this.currentacc= change;
    }

    @Override
    public void onCreate(){
        currentacc = "hello";
        super.onCreate();
    }

}
