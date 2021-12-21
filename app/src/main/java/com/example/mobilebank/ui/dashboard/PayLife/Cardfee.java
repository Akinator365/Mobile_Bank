package com.example.mobilebank.ui.dashboard.PayLife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobilebank.R;
import com.example.mobilebank.ui.login.DatabaseHelper;

public class Cardfee extends AppCompatActivity {

    private Button next;
    private Button choosecard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardfee);
        setTitle("校园卡");

        next = findViewById(R.id.Cardnext);
        choosecard = findViewById(R.id.Cardchoose);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(Cardfee.this,Cardinfo.class);
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

        String choosecontent = String.valueOf(choosecard.getText());;
        if(!choosecontent.equals("请选择 >"))
        {
            next.setEnabled(true);
        }

    }

}