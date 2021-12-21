package com.example.mobilebank.ui.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final String Create = "create table Card("
            + "Phone VARCHAR(20) ,"
            + "Cardname VARCHAR(30),"
            + "Cardid text PRIMARY KEY ) ";

    private static final String Initialuser = "create table Current("
            + "Phone VARCHAR(20) PRIMARY KEY,"
            + "Name VARCHAR(30)) ";

    private static final String user = "create table User("
            + "Phone VARCHAR(20) PRIMARY KEY,"
            + "Name VARCHAR(30)) ";

    public DatabaseHelper(Context context)
    {
        super(context, "info.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        //创建账号密码表
        sqLiteDatabase.execSQL("CREATE TABLE information(" +
                "Phone VARCHAR(20) PRIMARY KEY, Password VARCHAR(20))");
        //创建银行卡表
        sqLiteDatabase.execSQL(Create);
        //存储用户姓名和手机号
        sqLiteDatabase.execSQL(Initialuser);
        //存储当前用户
        sqLiteDatabase.execSQL(user);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
