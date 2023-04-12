package com.fpoly.baithi.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.baithi.Adapter.ThemGhiChuAdapter;
import com.fpoly.baithi.GhiChuDAO;
import com.fpoly.baithi.Model.ThemGhiChu;
import com.fpoly.baithi.R;

import java.util.ArrayList;

public class DanhSachFragment extends Fragment {
    private RecyclerView recyclerViewDanhSach;
    private GhiChuDAO danhSachDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhsach,container,false);
        recyclerViewDanhSach = view.findViewById(R.id.rcv_DanhSach);
        getDS();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDS();
    }

    private void getDS() {
        danhSachDAO = new GhiChuDAO(getContext());
        ArrayList<ThemGhiChu> list = danhSachDAO.layDSGhiChu();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewDanhSach.setLayoutManager(linearLayoutManager);
        ThemGhiChuAdapter adapter = new ThemGhiChuAdapter(getContext(),list);
        recyclerViewDanhSach.setAdapter(adapter);
    }
}
