package com.example.mobilebank.ui.login;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mobilebank.R;

public class Login_EnglishView extends AppCompatActivity
{
    private Button regEng, getpswEng, loginEng, langEng;
    private ImageButton twitter;
    private EditText phoneEng, pswEng;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_english_view);

        init();
    }

    private void init()
    {
        //按钮的绑定
        regEng = findViewById(R.id.registerEng);
        getpswEng = findViewById(R.id.forgetpswEng);
        loginEng = findViewById(R.id.loginEng);
        langEng = findViewById(R.id.languageEng);

        twitter = findViewById(R.id.twitter);
        //文本框绑定
        phoneEng = findViewById(R.id.numberEng);
        pswEng = findViewById(R.id.pswEng);
        //监视器绑定
        Onclick onclick = new Onclick();
        regEng.setOnClickListener(onclick);
        getpswEng.setOnClickListener(onclick);
        loginEng.setOnClickListener(onclick);
        langEng.setOnClickListener(onclick);
        twitter.setOnClickListener(onclick);
    }

    private class Onclick implements View.OnClickListener {

        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.registerEng:
                    break;
                case R.id.forgetpswEng:
                    break;
                case R.id.loginEng:
                    break;
                case R.id.languageEng:
                    LanguageChange();
                    break;
                case R.id.twitter:
                    break;
            }
        }
    }

    private void LanguageChange()
    {
        Intent intent = new Intent(Login_EnglishView.this, Login_MainActivity.class);
        startActivity(intent);
        finish();
    }
}