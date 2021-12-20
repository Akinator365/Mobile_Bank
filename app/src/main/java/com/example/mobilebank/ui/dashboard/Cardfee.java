package com.example.mobilebank.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobilebank.R;
import com.example.mobilebank.ui.login.MyDatabaseHelper;

public class Cardfee extends AppCompatActivity {

    private Button next;
    private Button choosecard;
    private Button createtable;
    private PayDatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardfee);
        dbhelper = new PayDatabaseHelper(this,"info.db",null,1);
        setTitle("校园卡");

        next = findViewById(R.id.Cardnext);
        choosecard = findViewById(R.id.Cardchoose);
        createtable = findViewById(R.id.create);

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

        createtable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("phone","1364738742");
                values.put("Cardname","中国银行借记卡");
                values.put("Cardid","6258945467547");
                db.insert("Card",null,values);
                values.clear();

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        String str = data.getStringExtra("data");

        choosecard.setText(str);

        String choosecontent = String.valueOf(choosecard.getText());;
        if(!choosecontent.equals("请选择>"))
        {
            next.setEnabled(true);
        }

    }

}