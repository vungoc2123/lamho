package com.fpoly.baithi.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fpoly.baithi.GhiChuDAO;
import com.fpoly.baithi.Model.ThemGhiChu;
import com.fpoly.baithi.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ThemGhiChuFragment extends Fragment {
    private EditText edt_title,edt_content,edt_date;
    private Button btnAdd;
    private GhiChuDAO danhSachDAO;
    private ThemGhiChu ghiChu;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_themghichu,container,false);
        edt_title = view.findViewById((R.id.edt_Title));
        edt_content = view.findViewById(R.id.edt_Content);
        edt_date = view.findViewById(R.id.edt_date);
        btnAdd = view.findViewById(R.id.btn_add);
        danhSachDAO = new GhiChuDAO(getContext());

        //khi ấn giữ ô date thì mới hiện date picker
        edt_date.setOnLongClickListener(view1 -> {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog( getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(year,month,dayOfMonth);
                    edt_date.setText(format.format(calendar.getTime()));
                }},year,month,day);
            datePickerDialog.show();
            return true;
        });
        //Thêm ghi chú vào cơ sở dữ liệu
        btnAdd.setOnClickListener(view1 -> {
            //kiểm tra xem ô nhập có trống dữ liệu không
            if(edt_title.length() == 0 || edt_content.length() == 0 ){
                //nếu trống thì thông báo
                Snackbar snackbar = Snackbar
                        .make(view, "Không để trống các ô nhập liệu", Snackbar.LENGTH_LONG);
                snackbar.show();
            }else{
                // nếu không thì thêm vào csdl
                ghiChu = new ThemGhiChu();
                ghiChu.setTitle(edt_title.getText().toString());
                ghiChu.setContent(edt_content.getText().toString());
                ghiChu.setDate(edt_date.getText().toString());
                boolean kq = danhSachDAO.themGhiChu(ghiChu);
                //thêm thành công thì dùng snackbar thông báo
                if(kq == true){
                    Snackbar snackbar = Snackbar
                            .make(view, "Thêm thành công", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                //làm mới ô nhập
                edt_title.setText("");
                edt_content.setText("");
                edt_date.setText("");
            }
        });
        return view;
    }




}
