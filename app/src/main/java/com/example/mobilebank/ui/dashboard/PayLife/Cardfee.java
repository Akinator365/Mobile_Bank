package com.example.mobilebank.ui.dashboard.PayLife;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobilebank.Data;
import com.example.mobilebank.R;
import com.example.mobilebank.ui.login.DatabaseHelper;

import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cardfee extends AppCompatActivity {

    private Button next;
    private Button choosecard;
    private String cardchosen;
    private PhoneCode Pc_1;
    private Button define;
    private TextView mytip;
    private String code;
    private DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardfee);
        final Data app = (Data)getApplication();
        dbhelper = new DatabaseHelper(this);
        setTitle("选择卡");

        next = findViewById(R.id.Cardnext);
        choosecard = findViewById(R.id.Cardchoose);
        Pc_1 = findViewById(R.id.pc_1);
        define = findViewById(R.id.def);
        mytip = findViewById(R.id.tips);

        next.setBackgroundColor(Color.parseColor("#BEBEBE"));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(app.getlastview() == 3)
                {
                    Intent intent = new Intent(Cardfee.this,Cardinfo.class);
                    intent.putExtra("send",cardchosen);
                    startActivity(intent);
                }
                else if(app.getlastview() == 0 || app.getlastview() == 1 || app.getlastview() == 2)
                {
                    next.setVisibility(View.GONE);
                    Pc_1.setVisibility(View.VISIBLE);
                    define.setVisibility(View.VISIBLE);
                    mytip.setVisibility(View.VISIBLE);
                    choosecard.setEnabled(false);
                    define.setEnabled(false);
                    define.setBackgroundColor(Color.parseColor("#BEBEBE"));
                    int r = (int)(Math.random()*(9999-1000+1)+1000);
                    code = String.valueOf(r);

                    final NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);//通知栏管理器（得到系统服务）
                    String id = "channel_1"; //自定义设置通道ID属性
                    String description = "123";//自定义设置通道描述属性
                    int importance = NotificationManager.IMPORTANCE_HIGH;//通知栏管理重要提示消息声音设定
                    NotificationChannel mChannel = new NotificationChannel(id, "123", importance);//建立通知栏通道类（需要有ID，重要属性）
                    manager.createNotificationChannel(mChannel);////最后在notificationmanager中创建该通知渠道
                    Notification notification = new Notification.Builder(Cardfee.this, id)//创建Notification对象。
                            .setContentTitle("中国银行")  //设置通知标题
                            .setSmallIcon(R.mipmap.ic_launcher)//设置通知小图标
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))//设置通知大图标
                            .setContentText("您正在进行民生缴费操作，手机验证码为"+code)//设置通知内容
                            .setAutoCancel(true)//设置自动删除通知
                            .build();//运行

                    manager.notify((int) System.currentTimeMillis(),notification); //通知栏保留多条通知
                }
                else
                {

                }

            }
        });

        choosecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cardfee.this,Bankcard.class);
                startActivityForResult(intent, 11);

            }
        });

        define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(test())
                {

                    Double currmoney;
                    SQLiteDatabase db = dbhelper.getWritableDatabase();
                    Cursor cursor = db.query("Card", null, "Cardid=?",
                            new String[]{app.getcurrbankcard()}, null, null, null);
                    cursor.moveToFirst();
                    {
                        currmoney = cursor.getDouble(3);
                    }
                    cursor.close();


                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    // HH:mm:ss
                    // 获取当前时间
                    Date date = new Date(System.currentTimeMillis());

                    if(currmoney > app.getfee())
                    {
                        ContentValues values = new ContentValues();
                        values.put("Balance",currmoney-app.getfee());
                        db.update("Card", values, "Cardid=?",
                                new String[]{app.getcurrbankcard()});
                        values.clear();

                        String CardType;
                        cursor = db.query("Card", null, "Cardid=?",
                                new String[]{app.getcurrbankcard()}, null, null, null);
                        cursor.moveToFirst();
                        {
                            CardType = cursor.getString(1);
                        }
                        cursor.close();

                        if(app.getlastview() == 0)
                        {
                            values.put("Watertopay",0.00);
                            db.update("Waterfee", values, "Waternum=?",
                                    new String[]{app.getCurrPayid()});
                            values.clear();

                            values.put("Phone",app.getcurrentuser());
                            values.put("Money",app.getfee());
                            values.put("Date",simpleDateFormat.format(date));
                            values.put("BillType","水费");
                            values.put("CardType",CardType);
                            values.put("CardID",app.getcurrbankcard());
                            values.put("BillAttach","支出");
                            db.insert("bill",null,values);
                            values.clear();
                        }


                        else if(app.getlastview() == 1)
                        {
                            values.put("Electrtopay",0.00);
                            db.update("electricityfee", values, "Electrnum=?",
                                    new String[]{app.getCurrPayid()});
                            values.clear();

                            values.put("Phone",app.getcurrentuser());
                            values.put("Money",app.getfee());
                            values.put("Date",simpleDateFormat.format(date));
                            values.put("BillType","电费");
                            values.put("CardType",CardType);
                            values.put("CardID",app.getcurrbankcard());
                            values.put("BillAttach","支出");
                            db.insert("bill",null,values);
                            values.clear();
                        }


                        else if(app.getlastview() == 2)
                        {
                            values.put("Fare",0.00);
                            db.update("schoolfare", values, "Studentnum=? AND Year=?",
                                    new String[]{app.getCurrPayid(),app.getyear()});
                            values.clear();

                            values.put("Phone",app.getcurrentuser());
                            values.put("Money",app.getfee());
                            values.put("Date",simpleDateFormat.format(date));
                            values.put("BillType","学费");
                            values.put("CardType",CardType);
                            values.put("CardID",app.getcurrbankcard());
                            values.put("BillAttach","支出");
                            db.insert("bill",null,values);
                            values.clear();
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(Cardfee.this);
                        builder.setTitle("提示");
                        builder.setMessage("缴费成功");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                finish();
                            }
                        });
                        builder.show();
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Cardfee.this);
                        builder.setTitle("提示");
                        builder.setMessage("余额不足，请充值!");
                        builder.setPositiveButton("好吧", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                finish();
                            }
                        });
                        builder.show();
                    }

                }
                else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Cardfee.this);
                    builder.setTitle("提示");
                    builder.setMessage("验证码错误");
                    builder.setPositiveButton("好吧", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {

                        }
                    });
                    builder.show();

                }

            }
        });


        //注册事件回调（根据实际需要，可写，可不写）

        Pc_1.setOnInputListener(new PhoneCode.OnInputListener() {
            @Override
            public void onSucess(String code) {
                //TODO: 例如底部【下一步】按钮可点击
                define.setEnabled(true);
                define.setBackgroundColor(Color.parseColor("#0456D3"));

            }

            @Override
            public void onInput() {
                //TODO:例如底部【下一步】按钮不可点击
            }
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        String str = data.getStringExtra("data");

        choosecard.setText(str);

        cardchosen = String.valueOf(choosecard.getText());;
        if(!cardchosen.equals("请选择 >"))
        {
            next.setEnabled(true);
            next.setBackgroundColor(Color.parseColor("#0456D3"));
        }

    }

    private boolean test(){
        //获得验证码
        String phoneCode = Pc_1.getPhoneCode();
        Log.d("top",phoneCode);
        if(phoneCode.equals(code))
        {
            return true;
        }
        //......
        //......
        //更多操作
        return false;
    }

}