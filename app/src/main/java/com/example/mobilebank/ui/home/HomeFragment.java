package com.example.mobilebank.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobilebank.Data;
import com.example.mobilebank.R;
import com.example.mobilebank.databinding.FragmentHomeBinding;
import com.example.mobilebank.ui.dashboard.BillingRecords.Record;
import com.example.mobilebank.ui.dashboard.PayLife.Pay;
import com.example.mobilebank.ui.login.DatabaseHelper;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private TextView helloview;
    private DatabaseHelper dbhelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        helloview = getActivity().findViewById(R.id.helloview);
        dbhelper = new DatabaseHelper(getActivity());
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.query("information", null, "Phone=?",
                new String[]{((Data)getActivity().getApplication()).getcurrentuser()}, null, null, null);
        cursor.moveToFirst();
        String currname = "error";
        if(cursor.getCount() == 0)
        {
            //TODO: 错误
        }
        else {
            currname = cursor.getString(2);
        }
        cursor.close();
        db.close();

        helloview.setText("早上好，"+currname);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}