package com.example.mobilebank.ui.dashboard.BillingRecords;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.mobilebank.Data;
import com.example.mobilebank.R;
import com.example.mobilebank.ui.login.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Record extends AppCompatActivity {

    private DatabaseHelper dbhelper;
    private SimpleAdapter adapter;
    private List<Map<String,Object>>list;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        setTitle("账单记录");


        dbhelper = new DatabaseHelper(this);
        listView = findViewById(R.id.mainListView3);
        list = new ArrayList<>();
        adapter = new SimpleAdapter(
                this,
                a(),
                R.layout.item4,
                new String[]{"文字1","文字2","文字3","文字4"},
                new int []{R.id.itemTextView1,R.id.itemTextView2,R.id.itemTextView3,R.id.itemTextView4});
        listView.setAdapter(adapter);

        SQLiteDatabase db = dbhelper.getWritableDatabase();
    }


    private List<Map<String,Object>> a()
    {
        final Data app = (Data)getApplication();
        ArrayList<Map<String,Object>> mapset = new ArrayList<Map<String,Object>>();

        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.query("bill", null, "Phone=?",
                new String[]{app.getcurrentuser()}, null, null, null);
        int i=0;
        Log.d("msg","billType");
        if(cursor.moveToFirst())
        {
            do{

                String billType = cursor.getString(4);
                String cardID = cursor.getString(5);
                String date = cursor.getString(3);
                String money = cursor.getString(2);
                String CardType = cursor.getString(6);
                CardType = CardType.substring(CardType.length()-3,CardType.length());


                i++;
                Map<String,Object>map1 = new HashMap<String,Object>();
                map1.put("文字1",billType);
                map1.put("文字2",
                        CardType
                        + "(" +
                        cardID.substring(cardID.length()-4, cardID.length())
                        + ")");
                map1.put("文字3",date);
                map1.put("文字4",money);
                mapset.add(map1);

            }while (cursor.moveToNext());
        }
        cursor.close();

        for(int j=0;j<i;j++)
        {
            list.add(mapset.get(j));
        }
        return list;
    }
}