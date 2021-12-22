package com.example.mobilebank.ui.notifications.account;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobilebank.Data;
import com.example.mobilebank.R;
import com.example.mobilebank.ui.login.DatabaseHelper;

public class addcard extends AppCompatActivity {

    private DatabaseHelper dbhelper;
    private EditText usercard;
    private Spinner cardtpye;
    private Button addin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcard);
        init();
    }

    private void init() {
        usercard = findViewById(R.id.usercard);
        cardtpye = findViewById(R.id.cardtype);
        addin = findViewById(R.id.addin);
        dbhelper = new DatabaseHelper(this);
        addin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addin();
            }
        });
    }

    private void addin() {
        String phone;
        String cardnum;
        SQLiteDatabase db;
        String type;
        final Data app = (Data)getApplication();
        phone = app.getcurrentuser();
        cardnum = usercard.getText().toString();
        type = (String) cardtpye.getSelectedItem();

        //判断银行卡是否已经添加
        if (SearchDB(cardnum)) {
            db = dbhelper.getWritableDatabase();
            ContentValues values;
            values = new ContentValues();
            values.put("Phone", phone);
            values.put("Cardname", type);
            values.put("Cardid", cardnum);
            values.put("Balance", 0);
            db.insert("Card", null, values);
            db.close();
            Toast.makeText(this, "添加成功！", Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                return;
            }
            Back();
        }

        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(addcard.this);
            builder.setTitle("该银行卡号已添加！");
            builder.setMessage("是否返回我的界面");
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Back();
                }
            });
            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.show();
        }
    }


    private boolean SearchDB(String cardnum)
    {
        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("Card", null, "Cardid=?",
                new String[]{cardnum}, null, null, null);
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
        this.finish();
    }
}