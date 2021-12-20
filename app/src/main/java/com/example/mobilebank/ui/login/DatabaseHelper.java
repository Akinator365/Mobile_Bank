package com.example.mobilebank.ui.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private Context context;
    private static final String Create = "create table Card("
            + "phone text PRIMARY KEY,"
            + "Cardname text,"
            + "Cardid text)";

    public DatabaseHelper(Context context)
    {
        super(context, "info.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        //创建账号密码表
        sqLiteDatabase.execSQL("CREATE TABLE information(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Phone VARCHAR(20), Password VARCHAR(20))");
        //创建银行卡表
        sqLiteDatabase.execSQL(Create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
