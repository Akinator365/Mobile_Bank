package com.example.mobilebank.ui.dashboard.PayLife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mobilebank.Data;
import com.example.mobilebank.R;
import com.example.mobilebank.ui.login.DatabaseHelper;

public class Cardinfo extends AppCompatActivity {

    private TextView topview;
    private TextView five;
    private TextView leftmoney;
    private String cardchosen;
    private TextView showbankcard;
    private TextView showschoolcard;
    private DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardinfo);
        setTitle("校园卡");

        topview = findViewById(R.id.topText);
        five = findViewById(R.id.lastfive);
        leftmoney = findViewById(R.id.leftcurrency);
        showbankcard = findViewById(R.id.bankcardview);
        showschoolcard = findViewById(R.id.schoolcardview);
        dbhelper = new DatabaseHelper(this);
        final Data app = (Data)getApplication();

        Intent i = getIntent();
        String receiver = i.getStringExtra("send");
        cardchosen = receiver.substring(0,receiver.length()-2);
        app.setcurrbankcard(cardchosen);
        showbankcard.setText(app.getcurrbankcard());

        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.query("School", null, "Phone=?",
                new String[]{app.getcurrentuser()}, null, null, null);
        cursor.moveToFirst();
        {
            app.setCurrPayid(cursor.getString(0));
            showschoolcard.setText(app.getCurrPayid());
        }
        cursor.close();

        topview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cardinfo.this,CardTopUp.class);
                startActivity(intent);
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cardinfo.this,Payhistory.class);
                startActivity(intent);
            }
        });

        leftmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cardinfo.this,Balance.class);
                startActivity(intent);
            }
        });
    }
}