package com.example.mobilebank.ui.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final String CreateCard = "create table Card("
            + "Phone VARCHAR(20) ,"
            + "Cardname VARCHAR(30),"
            + "Cardid text PRIMARY KEY, "
            + "Balance DOUBLE) ";

    private static final String CreateUser = "create table User("
            + "Phone VARCHAR(20) PRIMARY KEY,"
            + "Name VARCHAR(30)) ";

    private static final String CreateSchoolcard = "create table School("
            + "SchoolCard VARCHAR(20) PRIMARY KEY,"
            + "Phone VARCHAR(20)) ";

    private static final String Createbalance = "create table Schoolbalance("
            + "SchoolCard VARCHAR(20) PRIMARY KEY,"
            + "money DOUBLE) ";

    private static final String Createspend = "create table SchoolSpend("
            + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "TIME VARCHAR(20),"
            + "DAYTIME VARCHAR(20),"
            + "SchoolCard VARCHAR(20),"
            + "Money VARCHAR(20)) ";


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
        sqLiteDatabase.execSQL(CreateCard);
        //存储用户姓名和手机号
        sqLiteDatabase.execSQL(CreateUser);
        //存储校园卡号和手机号
        sqLiteDatabase.execSQL(CreateSchoolcard);
        //存储校园卡余额
        sqLiteDatabase.execSQL(Createbalance);
        //存储消费记录
        sqLiteDatabase.execSQL(Createspend);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
