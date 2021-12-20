package com.example.mobilebank.ui.dashboard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class PayDatabaseHelper extends SQLiteOpenHelper
{
    private Context context;
    private static final String Create = "create table Card("
            + "phone text primary KEY,"
            + "Cardname text,"
            + "Cardid text)";

    public PayDatabaseHelper(@Nullable Context context, @Nullable String name,
                             @Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(Create);
        Toast.makeText(context,"Create succeeded", Toast.LENGTH_LONG).show();;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
