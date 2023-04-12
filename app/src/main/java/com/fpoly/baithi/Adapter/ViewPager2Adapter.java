package com.fpoly.baithi.Adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.fpoly.baithi.Fragment.DanhSachFragment;
import com.fpoly.baithi.Fragment.ThemGhiChuFragment;

public class ViewPager2Adapter extends FragmentStateAdapter {
    private int trangThai;
    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity, int trangThai) {
        super(fragmentActivity);
        this.trangThai = trangThai;
    }

    @SuppressLint("SuspiciousIndentation")
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            DanhSachFragment danhSachFragment = new DanhSachFragment();
            Bundle bundle = new Bundle();
            danhSachFragment.setArguments(bundle);
            return danhSachFragment;
        }
            return new ThemGhiChuFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
