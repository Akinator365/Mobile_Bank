package com.example.mobilebank;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    int checked = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);*/

        final BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        final NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        if (checked != 1) {//判断点击的界面是不是当前界面，不是才发生跳转
                            controller.popBackStack();
                            controller.navigate(R.id.navigation_home);
                            checked = 1;
                        }
                        break;
                    case R.id.navigation_dashboard:
                        if (checked != 2) {
                            controller.popBackStack();
                            controller.navigate(R.id.navigation_dashboard);
                            checked = 2;
                        }
                        break;
                    case R.id.navigation_notifications:
                        if (checked != 3) {
                            controller.popBackStack();
                            controller.navigate(R.id.navigation_notifications);
                            checked = 3;
                        }
                        break;
                }
                return true;
            }
        });


        dbhelper = new DatabaseHelper(this);

        Intent i = getIntent();
        currentacc = i.getStringExtra("send");
        final Data app = (Data)getApplication();
        app.setcurrentuser(currentacc);

        makedata();
    }

    private void makedata() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
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
        db.close();
    }

}