package com.example.mobilebank.ui.dashboard.PayLife;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobilebank.Data;
import com.example.mobilebank.R;
import com.example.mobilebank.ui.login.DatabaseHelper;

public class Schoolfare extends AppCompatActivity {

    private Button checkbutton;
    private DatabaseHelper dbhelper;
    private EditText Studentnumedit;
    private EditText yearedit;
    private Double fee = 0.0;
    private TextView showfee;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolfare);
        setTitle("缴纳学费");
        checkbutton = findViewById(R.id.checkbutton);
        Studentnumedit = findViewById(R.id.Studentnumedit);
        yearedit = findViewById(R.id.yearedit);
        showfee = findViewById(R.id.showfee);
        next = findViewById(R.id.next);
        dbhelper = new DatabaseHelper(this);
        final Data app = (Data)getApplication();

        checkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = String.valueOf(Studentnumedit.getText());
                String year = String.valueOf(yearedit.getText());
                app.setCurrPayid(username);
                app.setyear(year);

                SQLiteDatabase db = dbhelper.getWritableDatabase();
                Cursor cursor = db.query("schoolfare", null, "Studentnum=? AND Year=?",
                        new String[]{username,year}, null, null, null);
                cursor.moveToFirst();
                {
                    fee = cursor.getDouble(2);
                }
                cursor.close();

                if(fee <= 0.001)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Schoolfare.this);
                    builder.setTitle("提示");
                    builder.setMessage("当前学号没有欠费");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {

                        }
                    });
                    builder.show();
                }
                else
                {
                    showfee.setVisibility(View.VISIBLE);
                    showfee.setText("当前欠费金额："+String.valueOf(fee));
                    next.setVisibility(View.VISIBLE);
                    app.setfee(fee);

                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Schoolfare.this,Cardfee.class);
                startActivity(intent);

            }
        });
    }
}