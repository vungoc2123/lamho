package com.fpoly.baithi.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
    private EditText edt_search;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhsach,container,false);
        recyclerViewDanhSach = view.findViewById(R.id.rcv_DanhSach);
        edt_search = view.findViewById(R.id.edt_search);

        //bắt sự kiện khi text thay đổi để tìm kiếm
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text is changed.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is called when the text is changed.
                danhSachDAO = new GhiChuDAO(getContext());
                ArrayList<ThemGhiChu> list = danhSachDAO.getGhiChuByTitle(edt_search.getText().toString());
                Log.e("tt",list.size()+"");
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                recyclerViewDanhSach.setLayoutManager(linearLayoutManager);
                ThemGhiChuAdapter adapter = new ThemGhiChuAdapter(getContext(),list);
                recyclerViewDanhSach.setAdapter(adapter);
                if(edt_search.length() == 0){
                    getDS();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        edt_search.addTextChangedListener(textWatcher);
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
