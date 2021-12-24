package com.example.mobilebank.ui.login;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mobilebank.MainActivity;
import com.example.mobilebank.R;

public class Login_MainActivity extends AppCompatActivity
{
    private Button reg, getpsw, login, lang;
    private ImageButton wechat;
    private EditText phone, psw;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        init();

    }

    private void init() {
        //按钮的绑定
        reg = findViewById(R.id.register);
        getpsw = findViewById(R.id.forgetpsw);
        login = findViewById(R.id.login);
        lang = findViewById(R.id.language);

        wechat = findViewById(R.id.wechat);
        //文本框绑定
        phone = findViewById(R.id.number);
        psw = findViewById(R.id.psw);
        //监视器绑定
        Onclick onclick = new Onclick();
        reg.setOnClickListener(onclick);
        getpsw.setOnClickListener(onclick);
        login.setOnClickListener(onclick);
        lang.setOnClickListener(onclick);
        wechat.setOnClickListener(onclick);

        helper = new DatabaseHelper(this);
        makedata();//数据载入

    }


    private class Onclick implements View.OnClickListener
    {

        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.register:
                    registed();
                    break;
                case R.id.forgetpsw:
                    forget();
                    break;
                case R.id.login:
                    logon();
                    break;
                case R.id.language:
                    LanguageChange();
                    break;
                case R.id.wechat:
                    break;
            }
        }
    }

    private void LanguageChange() //切换view
    {
        Intent intent = new Intent(Login_MainActivity.this, Login_EnglishView.class);
        startActivity(intent);
        finish();
    }

    private void registed()
    {
        Intent intent = new Intent(Login_MainActivity.this, Login_RegisterView.class);
        startActivity(intent);
    }

    private void forget()
    {
        Intent intent = new Intent(Login_MainActivity.this, Login_ForgetPsw.class);
        startActivity((intent));
    }
    private void logon()
    {
        String phoneNum = phone.getText().toString();
        String password = psw.getText().toString();
        SQLiteDatabase db;

        if(null == phoneNum || "".equals(phoneNum))
        {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(null == password || "".equals(password))
        {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            db = helper.getReadableDatabase();
            Cursor cursor = db.query("information", null, "Phone=?",
                    new String[]{phoneNum}, null, null, null);
            cursor.moveToFirst();
            if(cursor.getCount() == 0)
            {
                cursor.close();
                AlertDialog.Builder builder = new AlertDialog.Builder(Login_MainActivity.this);
                builder.setTitle("该手机号未注册");
                builder.setMessage("是否前往注册界面");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Intent intent = new Intent(Login_MainActivity.this,
                                Login_RegisterView.class);
                        startActivity((intent));
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        phone.setText("");
                        psw.setText("");
                    }
                });

                builder.show();
            }

            else
            {
                if(password.equals(cursor.getString(1)))
                {

                    Intent intent = new Intent(Login_MainActivity.this,MainActivity.class);
                    intent.putExtra("send",cursor.getString(0));
                    cursor.close();
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                    cursor.close();
                    psw.setText("");
                }
            }
        }

    }

    //数据载入
    private void makedata() {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Phone","15990736815");
        values.put("Password","123456");
        values.put("Name","林伟涵");
        db.insert("information",null,values);
        values.clear();

        values.put("Phone","12345678901");
        values.put("Password","123456");
        values.put("Name","林驰程");
        db.insert("information",null,values);
        values.clear();

        values.put("Phone","12345678902");
        values.put("Password","123456");
        values.put("Name","尤培杰");
        db.insert("information",null,values);
        values.clear();

        values.put("Phone","15990736815");
        values.put("Cardname","中国银行借记卡");
        values.put("Cardid","623465465465461975");
        values.put("Balance",10000.0);
        db.insert("Card",null,values);
        values.clear();

        values.put("Phone","15990736815");
        values.put("Cardname","中国银行储蓄卡");
        values.put("Cardid","634254354353455555");
        values.put("Balance",1000.0);
        db.insert("Card",null,values);
        values.clear();

        values.put("Phone","12345678901");
        values.put("Cardname","中国银行储蓄卡");
        values.put("Cardid","625894554664782549");
        values.put("Balance",100000.0);
        db.insert("Card",null,values);
        values.clear();

        values.put("Phone","12345678902");
        values.put("Cardname","中国银行信用卡");
        values.put("Cardid","623454355543644655");
        values.put("Balance",5000.0);
        db.insert("Card",null,values);
        values.clear();

        values.put("SchoolCard","118902");
        values.put("Phone","15990736815");
        db.insert("School",null,values);
        values.clear();

        values.put("SchoolCard","118903");
        values.put("Phone","12345678901");
        db.insert("School",null,values);
        values.clear();

        values.put("SchoolCard","118904");
        values.put("Phone","12345678902");
        db.insert("School",null,values);
        values.clear();

        values.put("SchoolCard","118902");
        values.put("money",100);
        db.insert("Schoolbalance",null,values);
        values.clear();

        values.put("SchoolCard","118903");
        values.put("money",1000);
        db.insert("Schoolbalance",null,values);
        values.clear();

        values.put("SchoolCard","118904");
        values.put("money",10000);
        db.insert("Schoolbalance",null,values);
        values.clear();

        values.put("SchoolCard","118905");
        values.put("money",10000);
        db.insert("Schoolbalance",null,values);
        values.clear();

        values.put("TIME","13:20");
        values.put("DAYTIME","2020-12-21");
        values.put("SchoolCard","118902");
        values.put("Money","5.00");
        db.insert("SchoolSpend",null,values);
        values.clear();

        values.put("TIME","21:58");
        values.put("DAYTIME","2021-06-30");
        values.put("SchoolCard","118902");
        values.put("Money","17.50");
        db.insert("SchoolSpend",null,values);
        values.clear();

        values.put("TIME","08:34");
        values.put("DAYTIME","2019-09-01");
        values.put("SchoolCard","118902");
        values.put("Money","3.50");
        db.insert("SchoolSpend",null,values);
        values.clear();

        values.put("TIME","12:20");
        values.put("DAYTIME","2020-03-29");
        values.put("SchoolCard","118902");
        values.put("Money","9.00");
        db.insert("SchoolSpend",null,values);
        values.clear();

        values.put("TIME","14:50");
        values.put("DAYTIME","2021-01-08");
        values.put("SchoolCard","118902");
        values.put("Money","20.00");
        db.insert("SchoolSpend",null,values);
        values.clear();

        values.put("TIME","10:20");
        values.put("DAYTIME","2019-11-30");
        values.put("SchoolCard","118902");
        values.put("Money","15.00");
        db.insert("SchoolSpend",null,values);
        values.clear();

        values.put("TIME","18:10");
        values.put("DAYTIME","2018-10-19");
        values.put("SchoolCard","118903");
        values.put("Money","13.88");
        db.insert("SchoolSpend",null,values);
        values.clear();

        values.put("TIME","13:42");
        values.put("DAYTIME","2020-12-21");
        values.put("SchoolCard","118903");
        values.put("Money","14.88");
        db.insert("SchoolSpend",null,values);
        values.clear();

        values.put("TIME","22:35");
        values.put("DAYTIME","2018-06-13");
        values.put("SchoolCard","118904");
        values.put("Money","19.88");
        db.insert("SchoolSpend",null,values);
        values.clear();

        values.put("Waternum","1234567");
        values.put("Watertopay",130.02);
        db.insert("Waterfee",null,values);
        values.clear();

        values.put("Waternum","7654321");
        values.put("Watertopay",0.00);
        db.insert("Waterfee",null,values);
        values.clear();

        values.put("Electrnum","1234567654321");
        values.put("Electrtopay",289.70);
        db.insert("electricityfee",null,values);
        values.clear();

        values.put("Electrnum","7654321234567");
        values.put("Electrtopay",0.00);
        db.insert("electricityfee",null,values);
        values.clear();

        values.put("Studentnum","08192819");
        values.put("Year","2019");
        values.put("Fare",7000.00);
        db.insert("schoolfare",null,values);
        values.clear();

        values.put("Studentnum","08192819");
        values.put("Year","2020");
        values.put("Fare",7000.00);
        db.insert("schoolfare",null,values);
        values.clear();

        values.put("Studentnum","12345678");
        values.put("Year","2019");
        values.put("Fare",7000.00);
        db.insert("schoolfare",null,values);
        values.clear();

        values.put("Studentnum","87654321");
        values.put("Year","2019");
        values.put("Fare",7000.00);
        db.insert("schoolfare",null,values);
        values.clear();

        values.put("ID",1);
        values.put("Phone","12345678902");
        values.put("Money","5000");
        values.put("Date","2010-12-30 12:00:00");
        values.put("BillType","工资");
        values.put("CardID","54654856456");
        values.put("CardType","信用卡");
        db.insert("bill",null,values);
        values.clear();

        values.put("ID",2);
        values.put("Phone","12345678901");
        values.put("Money","4000");
        values.put("Date","2011-12-30 12:00:00");
        values.put("BillType","工资");
        values.put("CardID","54654856456");
        values.put("CardType","借记卡");
        db.insert("bill",null,values);
        values.clear();

        values.put("ID",3);
        values.put("Phone","12345678901");
        values.put("Money","3000");
        values.put("Date","2012-12-30 12:00:00");
        values.put("BillType","工资");
        values.put("CardID","54654856456");
        values.put("CardType","储蓄卡");
        db.insert("bill",null,values);
        values.clear();

        values.put("ID",4);
        values.put("Phone","12345678901");
        values.put("Money","-2000");
        values.put("Date","2013-12-30 12:00:00");
        values.put("BillType","学费");
        values.put("CardType","学生卡");
        values.put("CardID","54654856456");
        db.insert("bill",null,values);
        values.clear();

        db.close();
    }
}