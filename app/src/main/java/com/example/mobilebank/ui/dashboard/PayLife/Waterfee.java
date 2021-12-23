package com.example.mobilebank.ui.dashboard.PayLife;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilebank.Data;
import com.example.mobilebank.R;
import com.example.mobilebank.ui.login.DatabaseHelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Waterfee extends AppCompatActivity {

    private Button checkbutton;
    private DatabaseHelper dbhelper;
    private EditText Usernameedit;
    private Double fee = 0.0;
    private TextView showfee;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterfee);
        setTitle("缴纳水费");
        checkbutton = findViewById(R.id.checkbutton);
        Usernameedit = findViewById(R.id.Usernameedit);
        showfee = findViewById(R.id.showfee);
        next = findViewById(R.id.next);
        dbhelper = new DatabaseHelper(this);
        final Data app = (Data)getApplication();

        checkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = String.valueOf(Usernameedit.getText());
                app.setCurrPayid(username);

                SQLiteDatabase db = dbhelper.getWritableDatabase();
                Cursor cursor = db.query("Waterfee", null, "Waternum=?",
                        new String[]{username}, null, null, null);

                if(cursor.getCount() == 0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Waterfee.this);
                    builder.setTitle("提示");
                    builder.setMessage("找不到该记录");
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
                    cursor.moveToFirst();
                    {
                        fee = cursor.getDouble(1);
                    }
                    cursor.close();

                    if(fee <= 0.001)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Waterfee.this);
                        builder.setTitle("提示");
                        builder.setMessage("当前用户没有欠费");
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
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Waterfee.this,Cardfee.class);
                startActivity(intent);

            }
        });
    }
}