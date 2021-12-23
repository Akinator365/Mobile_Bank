package com.example.mobilebank.ui.login;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilebank.R;

public class Login_RegisterView extends AppCompatActivity
{
    private EditText numberReg, pswReg;
    private Button RegisterReg;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register_view);

        init();
    }


    private void init() {
        numberReg = findViewById(R.id.numberReg);
        pswReg = findViewById(R.id.pswReg);
        RegisterReg = findViewById(R.id.RegisterReg);
        helper = new DatabaseHelper(this);
        RegisterReg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Register();
            }
        });
    }

    private void Register()
    {
        String phone;
        String password;
        SQLiteDatabase db;
        ContentValues values;
        phone = numberReg.getText().toString();
        if(SearchDB(phone))
        {
            password = pswReg.getText().toString();
            db = helper.getWritableDatabase();
            values = new ContentValues();
            values.put("Phone", phone);
            values.put("Password", password);
            db.insert("information", null, values);
            values.clear();
            values.put("Phone", phone);
            values.put("Name","null");
            db.insert("User", null, values);
            values.clear();

            Toast.makeText(this, "注册成功！ 即将转至登陆界面", Toast.LENGTH_LONG).show();
            db.close();
            try
            {
                Thread.sleep(3000);
            } catch (InterruptedException e)
            {
                return;
            }
            finish();
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Login_RegisterView.this);
            builder.setTitle("该手机号已被注册");
            builder.setMessage("是否返回登陆界面");
            builder.setPositiveButton("是", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    Back();
                }
            });
            builder.setNegativeButton("否", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                }
            });

            builder.show();
        }
    }

    private boolean SearchDB(String phone)
    {
        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("information", null, "Phone=?",
                new String[]{phone}, null, null, null);

        if(cursor.getCount() == 0)
        {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    private void Back()
    {
        Intent intent = new Intent(Login_RegisterView.this, Login_MainActivity.class);
        startActivity((intent));
        finish();
    }
}