package com.example.mobilebank.ui.dashboard.PayLife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.mobilebank.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pay extends AppCompatActivity {

    private ListView listView;
    private SimpleAdapter adapter;
    private List<Map<String,Object>>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        setTitle("生活缴费");
        listView = findViewById(R.id.mainListView1);
        list = new ArrayList<>();
        adapter = new SimpleAdapter(this,a(),R.layout.item,new String[]{"图片","文字"},new int []{R.id.itemImageView1,R.id.itemTextView1});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0)
                {
                    Intent intent = new Intent(Pay.this,Waterfee.class);
                    startActivity(intent);
                }
                if(i == 1)
                {
                    Intent intent = new Intent(Pay.this,Electricity.class);
                    startActivity(intent);
                }
                if(i == 2)
                {
                    Intent intent = new Intent(Pay.this,Schoolfare.class);
                    startActivity(intent);
                }
                if(i == 3)
                {
                    Intent intent = new Intent(Pay.this,Cardfee.class);
                    startActivity(intent);
                }

            }
        });
    }
    //设置数据源
    private List<Map<String,Object>>a()
    {
        Map<String,Object>map1 = new HashMap<String,Object>();
        map1.put("图片",R.drawable.shuifei);
        map1.put("文字","水费");
        Map<String,Object>map2 = new HashMap<String,Object>();
        map2.put("图片",R.drawable.dianfei);
        map2.put("文字","电费");
        Map<String,Object>map3 = new HashMap<String,Object>();
        map3.put("图片",R.drawable.xuefei);
        map3.put("文字","学费");
        Map<String,Object>map4 = new HashMap<String,Object>();
        map4.put("图片",R.drawable.xiaoyuan);
        map4.put("文字","校园卡");
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);

        return list;
    }
}