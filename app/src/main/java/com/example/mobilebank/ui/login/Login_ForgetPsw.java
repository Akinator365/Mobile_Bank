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
import android.widget.Toast;

import com.example.mobilebank.R;

public class Login_ForgetPsw extends AppCompatActivity
{
    private EditText Phone;
    private EditText NewPassword;
    private DatabaseHelper helper;
    private Button Forget;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget_psw);

        init();
    }

    private void init()
    {
        Phone = findViewById(R.id.numberFor);
        NewPassword = findViewById(R.id.newpsw);
        Forget = findViewById(R.id.ForgetFor);

        helper = new DatabaseHelper(this);

        Forget.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                forgetpsw();
            }
        });


    }

    private void forgetpsw()
    {
        String PhoneNum = Phone.getText().toString();
        String NewPsw = NewPassword.getText().toString();
        SQLiteDatabase db;

        if(PhoneNum.equals("") || PhoneNum == null)
        {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(NewPsw.equals("") || PhoneNum == null)
        {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            db = helper.getWritableDatabase();
            Cursor cursor = db.query("information", null, "Phone=?",
                    new String[]{PhoneNum}, null, null, null);
            cursor.moveToFirst();
            if(cursor.getCount() == 0)
            {
                cursor.close();
                AlertDialog.Builder builder = new AlertDialog.Builder(Login_ForgetPsw.this);
                builder.setTitle("该手机号未注册");
                builder.setMessage("是否前往注册界面");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Intent intent = new Intent(Login_ForgetPsw.this,
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
                        Phone.setText("");
                    }
                });

                builder.show();
                return;
            }
            else
            {
                ContentValues values = new ContentValues();
                values.put("Password",NewPsw);
                db.update("information", values, "Phone=?", new String[]{PhoneNum});
                db.close();
                cursor.close();
                Toast.makeText(this, "更改成功，即将转回登陆页面",Toast.LENGTH_LONG).show();
                try
                {
                    Thread.sleep(3000);
                } catch (InterruptedException e)
                {
                    return;
                }

                Intent intent = new Intent(Login_ForgetPsw.this, Login_MainActivity.class);
                startActivity((intent));
                finish();

            }

        }
    }
}