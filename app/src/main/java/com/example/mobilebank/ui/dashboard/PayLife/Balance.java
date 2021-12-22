package com.example.mobilebank.ui.dashboard.PayLife;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mobilebank.Data;
import com.example.mobilebank.R;
import com.example.mobilebank.ui.login.DatabaseHelper;

public class Balance extends AppCompatActivity {

    private TextView balshowbank;
    private TextView balshowschool;
    private TextView balshowsmoney;
    private DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        setTitle("校园卡");

        final Data app = (Data)getApplication();
        dbhelper = new DatabaseHelper(this);

        balshowbank = findViewById(R.id.balbank);
        balshowschool = findViewById(R.id.balschool);
        balshowsmoney = findViewById(R.id.moneyview);

        balshowbank.setText(app.getcurrbankcard());
        balshowschool.setText(app.getCurrPayid());

        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.query("Schoolbalance", null, "SchoolCard=?",
                new String[]{app.getCurrPayid()}, null, null, null);
        cursor.moveToFirst();
        {
            balshowsmoney.setText(String.valueOf(cursor.getDouble(1)));
        }
        cursor.close();
    }
}