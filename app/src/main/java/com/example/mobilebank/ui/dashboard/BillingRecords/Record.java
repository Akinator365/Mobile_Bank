package com.example.mobilebank.ui.dashboard.BillingRecords;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mobilebank.Data;
import com.example.mobilebank.R;
import com.example.mobilebank.ui.login.DatabaseHelper;
import com.example.mobilebank.ui.login.Login_MainActivity;
import com.example.mobilebank.ui.login.Login_RegisterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Record extends AppCompatActivity {

    private DatabaseHelper dbhelper;
    private SimpleAdapter adapter;
    private List<Map<String,Object>>list;
    private ListView listView;

    private Button btn_starttime;
    private Button btn_endtime;
    private TextView tv_starttime;
    private TextView tv_endtime;

    private Spinner sp_cardtype;
    private Spinner sp_billtype;
    private EditText et_minmoney;
    private EditText et_maxmoney;

    private Button btn_reset;
    private Button btn_confirm;

    private String starttime;
    private String endtime;
    private double minmoney;
    private double maxmoney;
    private String cardtype;
    private String billtype;

    public int press_flag = 0;
    public boolean initial = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        setTitle("账单记录");

        tv_starttime = findViewById(R.id.tv_starttime);
        tv_endtime = findViewById(R.id.tv_endtime);
        btn_starttime = findViewById(R.id.btn_starttime);
        btn_endtime = findViewById(R.id.btn_endtime);

        sp_cardtype = findViewById(R.id.sp_cardtype);
        sp_billtype = findViewById(R.id.sp_billtype);
        et_minmoney = findViewById(R.id.et_minmoney);
        et_maxmoney = findViewById(R.id.et_maxmoney);

        btn_reset = findViewById(R.id.btn_reset);
        btn_confirm = findViewById(R.id.btn_confirm);



        Calendar ca = Calendar.getInstance();
        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(Calendar.MONTH);
        int mDay = ca.get(Calendar.DAY_OF_MONTH);

        listView = findViewById(R.id.mainListView3);
        dbhelper = new DatabaseHelper(this);

        btn_starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Record.this, onDateSetListener, mYear, mMonth, mDay).show();
                press_flag = 1;
            }
        });

        btn_endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Record.this, onDateSetListener, mYear, mMonth, mDay).show();
                press_flag = 2;
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv_starttime.getText().length() == 0 || tv_endtime.getText().length() == 0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Record.this);
                    builder.setTitle("警告");
                    builder.setMessage("请输入确定的时间范围");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {

                        }
                    });
                    builder.show();
                }

                else if(et_minmoney.getText().length() == 0 || et_maxmoney.getText().length() == 0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Record.this);
                    builder.setTitle("警告");
                    builder.setMessage("请输入确定的金额范围");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {

                        }
                    });
                    builder.show();
                }

                else
                {
                    minmoney = Double.parseDouble(et_minmoney.getText().toString());
                    maxmoney = Double.parseDouble(et_maxmoney.getText().toString());
                    cardtype = (String) sp_cardtype.getSelectedItem();
                    billtype = (String) sp_billtype.getSelectedItem();

                    list = new ArrayList<>();
                    initial = false;
                    adapter = new SimpleAdapter(
                            Record.this,
                            a(),
                            R.layout.item4,
                            new String[]{"文字1","文字2","文字3","文字4"},
                            new int []{R.id.itemTextView1,R.id.itemTextView2,R.id.itemTextView3,R.id.itemTextView4});
                    listView.setAdapter(adapter);
                }

            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_starttime.setText("");
                tv_endtime.setText("");
                sp_cardtype.setSelection(0,true);
                sp_billtype.setSelection(0,true);
                et_minmoney.setText("");
                et_maxmoney.setText("");

                list = new ArrayList<>();
                initial = true;
                adapter = new SimpleAdapter(
                        Record.this,
                        a(),
                        R.layout.item4,
                        new String[]{"文字1","文字2","文字3","文字4"},
                        new int []{R.id.itemTextView1,R.id.itemTextView2,R.id.itemTextView3,R.id.itemTextView4});
                listView.setAdapter(adapter);

            }
        });


        list = new ArrayList<>();
        adapter = new SimpleAdapter(
                this,
                a(),
                R.layout.item4,
                new String[]{"文字1","文字2","文字3","文字4"},
                new int []{R.id.itemTextView1,R.id.itemTextView2,R.id.itemTextView3,R.id.itemTextView4});
        listView.setAdapter(adapter);

    }


    private List<Map<String,Object>> a()
    {
        final Data app = (Data)getApplication();
        ArrayList<Map<String,Object>> mapset = new ArrayList<Map<String,Object>>();

        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = null;
        if(initial)
        {
             cursor = db.query("bill", null, "Phone=?",
                     new String[]{app.getcurrentuser()}, null, null, null);
        }
        else if(cardtype.equals("全部"))
        {
            if(billtype.equals("全部"))
            {
                cursor = db.query("bill", null, "Phone=? AND Money>=? AND Money<=? AND Date between ? AND ?",
                        new String[]{app.getcurrentuser(), String.valueOf(minmoney), String.valueOf(maxmoney),starttime,endtime}, null, null, null);
            }
            else
            {
                cursor = db.query("bill", null, "Phone=? AND BillAttach=? AND Money>=? AND Money<=? AND Date between ? AND ?",
                        new String[]{app.getcurrentuser(),billtype, String.valueOf(minmoney), String.valueOf(maxmoney),starttime,endtime}, null, null, null);
            }
        }
        else
        {
            if(billtype.equals("全部"))
            {
                cursor = db.query("bill", null, "Phone=? AND CardType=? AND Money>=? AND Money<=? AND Date between ? AND ?",
                        new String[]{app.getcurrentuser(),"中国银行"+cardtype, String.valueOf(minmoney), String.valueOf(maxmoney),starttime,endtime}, null, null, null);
            }
            else
            {
                cursor = db.query("bill", null, "Phone=? AND BillAttach=?  AND CardType=? AND Money>=? AND Money<=? AND Date between ? AND ?",
                        new String[]{app.getcurrentuser(),billtype,"中国银行"+cardtype, String.valueOf(minmoney), String.valueOf(maxmoney),starttime,endtime}, null, null, null);
            }
        }

        int i=0;

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
                if(cursor.getString(7).equals("收入"))
                {
                    map1.put("文字4","+"+money);
                }else
                {
                    map1.put("文字4","-"+money);
                }

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

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            String days;
            String formatday;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                    formatday = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                    formatday = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                    formatday = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                    formatday = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }

            }

            if(press_flag == 1)
            {
                tv_starttime.setText(days);
                starttime = formatday + " 00:00:00";
                Log.d("day",starttime);
            }

            if(press_flag == 2)
            {
                tv_endtime.setText(days);
                endtime = formatday + " 23:59:59";
            }
        }
    };
}