package com.example.mobilebank;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobilebank.databinding.ActivityMainBinding;
import com.example.mobilebank.ui.login.DatabaseHelper;
import com.example.mobilebank.ui.login.Login_MainActivity;
import com.example.mobilebank.ui.login.Login_RegisterView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseHelper dbhelper;
    public String currentacc = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        dbhelper = new DatabaseHelper(this);

        Intent i = getIntent();
        currentacc = i.getStringExtra("send");

        makedata();
    }

    private void makedata() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        db.execSQL("DELETE FROM Current");

        values.put("Name","林伟涵");
        values.put("Phone","15990736815");
        db.insert("User",null,values);
        values.clear();

        values.put("Name","林驰程");
        values.put("Phone","12345678901");
        db.insert("User",null,values);
        values.clear();

        values.put("Name","尤培杰");
        values.put("Phone","12345678902");
        db.insert("User",null,values);
        values.clear();

        Cursor cursor = db.query("User", null, "Phone=?",
                new String[]{currentacc}, null, null, null);
        cursor.moveToFirst();
        String currname = "error";
        if(cursor.getCount() == 0)
        {
            //TODO: 错误
        }
        else {
            currname = cursor.getString(1);
        }
        cursor.close();

        values.put("Phone",currentacc);
        values.put("Name",currname);
        db.insert("Current",null,values);
        values.clear();

        values.put("Phone","15990736815");
        values.put("Password","123456");
        db.insert("information",null,values);
        values.clear();

        values.put("Phone","12345678901");
        values.put("Password","123456");
        db.insert("information",null,values);
        values.clear();

        values.put("Phone","12345678902");
        values.put("Password","123456");
        db.insert("information",null,values);
        values.clear();

        values.put("Phone","15990736815");
        values.put("Cardname","中国银行借记卡");
        values.put("Cardid","623465465465461975");
        db.insert("Card",null,values);
        values.clear();

        values.put("Phone","15990736815");
        values.put("Cardname","中国银行储蓄卡");
        values.put("Cardid","634254354353455555");
        db.insert("Card",null,values);
        values.clear();

        values.put("Phone","12345678901");
        values.put("Cardname","中国银行储蓄卡");
        values.put("Cardid","625894554664782549");
        db.insert("Card",null,values);
        values.clear();

        values.put("Phone","12345678902");
        values.put("Cardname","中国银行信用卡");
        values.put("Cardid","623454355543644655");
        db.insert("Card",null,values);
        values.clear();

        db.close();
    }

}