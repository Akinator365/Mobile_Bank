package com.example.mobilebank.ui.dashboard.Remit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobilebank.R;

public class RemittanceActivity extends AppCompatActivity {

    private EditText mEtPayAccount;
    private EditText mEtPayMoney;
    private EditText mEtReceiver;
    private EditText mEtGetAccount;
    private EditText mEtAttachment;
    private Button mBtnNextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remittance);
        mEtPayAccount = findViewById(R.id.et_payaccount);
        mEtPayMoney = findViewById(R.id.et_paymoney);
        mEtReceiver = findViewById(R.id.et_receiver);
        mEtGetAccount = findViewById(R.id.et_getaccount);
        mEtAttachment = findViewById(R.id.et_attachment);
        mBtnNextStep = findViewById(R.id.btn_nextstep);
        mBtnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}