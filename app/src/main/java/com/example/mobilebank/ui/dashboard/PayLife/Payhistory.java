package com.example.mobilebank.ui.dashboard.PayLife;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobilebank.Data;
import com.example.mobilebank.R;
import com.example.mobilebank.ui.login.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Payhistory extends AppCompatActivity {

    private TextView fiveshowbank;
    private TextView fiveshowschool;
    private DatabaseHelper dbhelper;
    private LinearLayout spend_1;
    private TextView showspend_1;
    private LinearLayout spend_2;
    private TextView showspend_2;
    private LinearLayout spend_3;
    private TextView showspend_3;
    private LinearLayout spend_4;
    private TextView showspend_4;
    private LinearLayout spend_5;
    private TextView showspend_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payhistory);
        setTitle("校园卡");
        final Data app = (Data)getApplication();
        dbhelper = new DatabaseHelper(this);

        fiveshowbank = findViewById(R.id.fivebank);
        fiveshowschool = findViewById(R.id.fiveschool);

        spend_1 = findViewById(R.id.five_1);
        spend_2 = findViewById(R.id.five_2);
        spend_3 = findViewById(R.id.five_3);
        spend_4 = findViewById(R.id.five_4);
        spend_5 = findViewById(R.id.five_5);

        showspend_1 = findViewById(R.id.fi_1);
        showspend_2 = findViewById(R.id.fi_2);
        showspend_3 = findViewById(R.id.fi_3);
        showspend_4 = findViewById(R.id.fi_4);
        showspend_5 = findViewById(R.id.fi_5);


        fiveshowbank.setText(app.getcurrbankcard());
        fiveshowschool.setText(app.getcurrschoolcard());

        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.query("SchoolSpend", null, "SchoolCard=?",
                new String[]{app.getcurrschoolcard()}, null, null, null);
        int i=0;
        if(cursor.moveToFirst())
        {

            do{

                String itemtime = cursor.getString(1);
                String itemdaytime = cursor.getString(2);
                String itemmoney = cursor.getString(4);
                i++;
                spend_1.setVisibility(View.VISIBLE);
                String str= itemdaytime+"        "+itemtime+"                 "+itemmoney;
                showspend_1.setText(str);
                if(i==2)
                {
                    spend_2.setVisibility(View.VISIBLE);
                    str= itemdaytime+"        "+itemtime+"                 "+itemmoney;
                    showspend_2.setText(str);
                }
                if(i==3)
                {
                    spend_3.setVisibility(View.VISIBLE);
                    str= itemdaytime+"        "+itemtime+"                 "+itemmoney;
                    showspend_3.setText(str);
                }
                if(i==4)
                {
                    spend_4.setVisibility(View.VISIBLE);
                    str= itemdaytime+"        "+itemtime+"                 "+itemmoney;
                    showspend_4.setText(str);
                }
                if(i==5)
                {
                    spend_5.setVisibility(View.VISIBLE);
                    str= itemdaytime+"        "+itemtime+"                 "+itemmoney;
                    showspend_5.setText(str);
                }

            }while (cursor.moveToNext());
        }
        cursor.close();


    }
}