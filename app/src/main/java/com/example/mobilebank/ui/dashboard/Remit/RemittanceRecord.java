package com.example.mobilebank.ui.dashboard.Remit;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.example.mobilebank.R;
import com.example.mobilebank.ui.login.DatabaseHelper;

public class RemittanceRecord extends AppCompatActivity {
    private ListView mLvRecord;

    private DatabaseHelper dbhelper=new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remittance_record);

        mLvRecord = findViewById(R.id.lv_record);
        SQLiteDatabase mydb = dbhelper.getWritableDatabase();
        Cursor cursor = mydb.query("transfer", new String[]{"_id","PayCardID","Money","Receiver","GetCardID","Attachment"}, null,null,null, null, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(RemittanceRecord.this,R.layout.item5,cursor,new String[]{"PayCardID","Money","Receiver","GetCardID","Attachment"},new int[]{R.id.tv_payaccount,R.id.tv_paymoney,R.id.tv_receiver,R.id.tv_getaccount,R.id.tv_attachment});
        mLvRecord.setAdapter(adapter);






    }
}