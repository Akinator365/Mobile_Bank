package com.example.mobilebank.ui.login;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mobilebank.MainActivity;
import com.example.mobilebank.R;

public class Login_MainActivity extends AppCompatActivity {

    private Button reg, getpsw, login, lang;
    private ImageButton wechat;
    private EditText phone, psw;

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
        Intent intent = new Intent(Login_MainActivity.this, MainActivity.class);
        startActivity((intent));
    }
}