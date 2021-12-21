package com.example.mobilebank.ui.dashboard.PayLife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mobilebank.R;
import com.example.mobilebank.ui.login.DatabaseHelper;

import java.security.PrivateKey;

public class Cardfee extends AppCompatActivity {

    private Button next;
    private Button choosecard;
    private String cardchosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardfee);
        setTitle("校园卡");

        next = findViewById(R.id.Cardnext);
        choosecard = findViewById(R.id.Cardchoose);

        next.setBackgroundColor(Color.parseColor("#BEBEBE"));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Cardfee.this,Cardinfo.class);
                    intent.putExtra("send",cardchosen);
                    startActivity(intent);

            }
        });

        choosecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cardfee.this,Bankcard.class);
                startActivityForResult(intent, 11);

            }
        });


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        String str = data.getStringExtra("data");

        choosecard.setText(str);

        cardchosen = String.valueOf(choosecard.getText());;
        if(!cardchosen.equals("请选择 >"))
        {
            next.setEnabled(true);
            next.setBackgroundColor(Color.parseColor("#0456D3"));
        }

    }

}