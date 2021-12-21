package com.example.mobilebank.ui.dashboard.PayLife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mobilebank.R;

public class Cardinfo extends AppCompatActivity {

    private TextView topview;
    private TextView five;
    private TextView leftmoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardinfo);
        setTitle("校园卡");

        topview = findViewById(R.id.topText);
        five = findViewById(R.id.lastfive);
        leftmoney = findViewById(R.id.leftcurrency);

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