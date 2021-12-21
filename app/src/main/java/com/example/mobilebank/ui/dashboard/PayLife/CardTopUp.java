package com.example.mobilebank.ui.dashboard.PayLife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobilebank.R;

public class CardTopUp extends AppCompatActivity {

    private Button starttop;
    private PhoneCode Pc_2;
    private Button define;
    private TextView mytip;
    private EditText editmoney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_top_up);
        setTitle("校园卡");

        starttop = findViewById(R.id.cardstart);
        Pc_2 = findViewById(R.id.pc_2);
        define = findViewById(R.id.def);
        mytip = findViewById(R.id.tips);
        editmoney = findViewById(R.id.moneynum);

        starttop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starttop.setVisibility(View.GONE);
                Pc_2.setVisibility(View.VISIBLE);
                define.setVisibility(View.VISIBLE);
                mytip.setVisibility(View.VISIBLE);
                editmoney.setEnabled(false);
            }
        });


        //注册事件回调（根据实际需要，可写，可不写）

        Pc_2.setOnInputListener(new PhoneCode.OnInputListener() {
            @Override
            public void onSucess(String code) {
                //TODO: 例如底部【下一步】按钮可点击
                define.setEnabled(true);

            }

            @Override
            public void onInput() {
                //TODO:例如底部【下一步】按钮不可点击
            }
        });

    }

    private void test(){
        //获得验证码
        String phoneCode = Pc_2.getPhoneCode();
        //......
        //......
        //更多操作
    }




}