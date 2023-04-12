package com.fpoly.baithi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.fpoly.baithi.Adapter.ViewPager2Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPager2Adapter adapter;
    private int trangThai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPP2);


        ViewPager2Adapter adapter = new ViewPager2Adapter(MainActivity.this,trangThai);
//        adapter = new ViewPager2Adapter(MainActivity.this);
        viewPager2.setAdapter(adapter);
        ProgressDialog progressDialog = ProgressDialog.show(
                MainActivity.this,"Thông báo","đang tải dữ liệu,Vui lòng chờ..."
        );
        CountDownTimer countDownTimer = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                progressDialog.dismiss();
            }
        };
        countDownTimer.start();

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    switch (position){
                        case 0:
                            tab.setText("Danh Sách");
                            break;
                        case 1:
                            tab.setText("Thêm Ghi Chú");
                            break;
                    }
            }
        }).attach();
    }
}