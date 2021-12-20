package com.example.mobilebank.ui.login;


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
        finish();
    }

    private void forget()
    {
        Intent intent = new Intent(Login_MainActivity.this, Login_ForgetPsw.class);
        startActivity((intent));
        finish();
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
                        finish();
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        phone.setText("");
                    }
                });

                builder.show();
            }

            else
            {
                if(password.equals(cursor.getString(2)))
                {
                    cursor.close();
                    Intent intent = new Intent(Login_MainActivity.this,
                            MainActivity.class);
                    startActivity((intent));
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
}