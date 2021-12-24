package com.example.mobilebank.ui.dashboard.Remit;

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
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobilebank.Data;
import com.example.mobilebank.R;
import com.example.mobilebank.ui.dashboard.PayLife.Bankcard;
import com.example.mobilebank.ui.dashboard.PayLife.Cardfee;
import com.example.mobilebank.ui.dashboard.PayLife.PhoneCode;
import com.example.mobilebank.ui.login.DatabaseHelper;
import com.example.mobilebank.ui.login.Login_MainActivity;
import com.example.mobilebank.ui.login.Login_RegisterView;

public class RemittanceActivity extends AppCompatActivity {

    private Button mBtnPayAccount;
    private EditText mEtPayMoney;
    private EditText mEtReceiver;
    private EditText mEtGetAccount;
    private EditText mEtAttachment;
    private Button mBtnNextStep;
    private DatabaseHelper dbhelper;
    private String code;
    private PhoneCode Pc_3;
    private TextView mytip;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remittance);
        setTitle("转账汇款");
        mBtnPayAccount = findViewById(R.id.btn_payaccount);
        mEtPayMoney = findViewById(R.id.et_paymoney);
        mEtReceiver = findViewById(R.id.et_receiver);
        mEtGetAccount = findViewById(R.id.et_getaccount);
        mEtAttachment = findViewById(R.id.et_attachment);
        mBtnNextStep = findViewById(R.id.btn_nextstep);
        Pc_3 = findViewById(R.id.pc_3);
        mytip = findViewById(R.id.tips);
        final Data app = (Data)getApplication();
        dbhelper = new DatabaseHelper(this);

        mBtnPayAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RemittanceActivity.this, Bankcard.class);
                startActivityForResult(intent, 11);
            }
        });



        mBtnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String PayCardID = app.getcurrbankcard();
                    String PayMoney =   mEtPayMoney.getText().toString();
                    String Receiver =   mEtReceiver.getText().toString();
                    String GetCardID =  mEtGetAccount.getText().toString();
                    String Attachment =   mEtAttachment.getText().toString();

                    if(flag){
                        int r = (int)(Math.random()*(9999-1000+1)+1000);
                        code = String.valueOf(r);
                        final NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);//通知栏管理器（得到系统服务）
                        String id = "channel_1"; //自定义设置通道ID属性
                        String description = "123";//自定义设置通道描述属性
                        int importance = NotificationManager.IMPORTANCE_HIGH;//通知栏管理重要提示消息声音设定
                        NotificationChannel mChannel = new NotificationChannel(id, "123", importance);//建立通知栏通道类（需要有ID，重要属性）
                        manager.createNotificationChannel(mChannel);////最后在notificationmanager中创建该通知渠道
                        Notification notification = new Notification.Builder(RemittanceActivity.this, id)//创建Notification对象。
                                .setContentTitle("中国银行")  //设置通知标题
                                .setSmallIcon(R.mipmap.ic_launcher)//设置通知小图标
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))//设置通知大图标
                                .setContentText("您正在进行民生缴费操作，手机验证码为"+code)//设置通知内容
                                .setAutoCancel(true)//设置自动删除通知
                                .build();//运行

                        manager.notify((int) System.currentTimeMillis(),notification); //通知栏保留多条通知

                        mBtnNextStep.setText("确定");
                        flag = false;
                        Pc_3.setVisibility(View.VISIBLE);
                        mytip.setVisibility(View.VISIBLE);
                        mBtnPayAccount.setEnabled(false);
                        mEtPayMoney.setEnabled(false);
                        mEtReceiver.setEnabled(false);
                        mEtGetAccount.setEnabled(false);
                        mEtAttachment.setEnabled(false);

                    }
                    else
                        {//判断验证码合法性

                            if(!test())
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RemittanceActivity.this);
                                builder.setTitle("提示");
                                builder.setMessage("验证码错误");
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
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
                                SQLiteDatabase db = dbhelper.getWritableDatabase();

                                Cursor cursor = db.query("Card", null, "Cardid=?",
                                        new String[]{PayCardID}, null, null, null);
                                cursor.moveToFirst();
                                Double currmoney = cursor.getDouble(3);
                                if(currmoney<Double.parseDouble(PayMoney)){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RemittanceActivity.this);
                                    builder.setTitle("提示");
                                    builder.setMessage("余额不足");
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i)
                                        {
                                            mEtPayMoney.setText("");
                                        }
                                    });
                                    builder.show();

                                }
                                else{
                                    cursor = db.query("Card", null, "Cardid=?",
                                            new String[]{GetCardID}, null, null, null);
                                    cursor.moveToFirst();
                                    if(cursor.getCount() == 0)
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RemittanceActivity.this);
                                        builder.setTitle("提示");
                                        builder.setMessage("该收款账户不存在");
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i)
                                            {
                                                mEtGetAccount.setText("");
                                            }
                                        });
                                        builder.show();

                                    }
                                    else{
                                        cursor = db.query("Card", null, "Cardid=?",
                                                new String[]{GetCardID}, null, null, null);
                                        cursor.moveToFirst();
                                        String PhoneNumber = cursor.getString(0);
                                        cursor = db.query("information", null, "Phone=?",
                                                new String[]{PhoneNumber}, null, null, null);
                                        cursor.moveToFirst();
                                        String receiver = cursor.getString(2);
                                        if(!receiver.equals(Receiver)){
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RemittanceActivity.this);
                                            builder.setTitle("提示");
                                            builder.setMessage("收款卡号或收款人名称错误");
                                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                                            {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i)
                                                {
                                                    mEtGetAccount.setText("");
                                                    mEtReceiver.setText("");
                                                }
                                            });
                                            builder.show();
                                        }

                                        else{

                                            ContentValues values = new ContentValues();
                                            values.put("Balance",currmoney-Double.parseDouble(PayMoney));
                                            db.update("Card", values, "Cardid=?",
                                                    new String[]{PayCardID});
                                            values.clear();

                                            cursor = db.query("Card", null, "Cardid=?",
                                                    new String[]{GetCardID}, null, null, null);
                                            cursor.moveToFirst();
                                            double rcvBalance = cursor.getDouble(3);

                                            values.put("Balance",rcvBalance+Double.parseDouble(PayMoney));
                                            db.update("Card", values, "Cardid=?",
                                                    new String[]{GetCardID});
                                            values.clear();

                                            //生成转账记录
                                            ContentValues cv = new ContentValues();
                                            cv.put("PayCardID",PayCardID);
                                            cv.put("Money",Double.parseDouble(PayMoney));
                                            cv.put("Receiver",Receiver);
                                            cv.put("GetCardID",GetCardID);
                                            cv.put("Attachment",Attachment);
                                            db.insert("transfer",null,cv);//将数据添加到数据表
                                            db.update("transfer",cv,"PayCardID=?",new String[]{PayCardID});
                                            cv.clear();






                                            AlertDialog.Builder builder = new AlertDialog.Builder(RemittanceActivity.this);
                                            builder.setTitle("提示");
                                            builder.setMessage("转账成功");
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

                                    }
                                }

                            }


                    }

            }


        });

        Pc_3.setOnInputListener(new PhoneCode.OnInputListener() {
            @Override
            public void onSucess(String code) {
                //TODO: 例如底部【下一步】按钮可点击
                mBtnNextStep.setEnabled(true);

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

        mBtnPayAccount.setText(str);

    }
    private boolean test(){
        //获得验证码
        String phoneCode = Pc_3.getPhoneCode();
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