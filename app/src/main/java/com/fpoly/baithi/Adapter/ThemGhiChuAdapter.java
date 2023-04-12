package com.fpoly.baithi.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.baithi.GhiChuDAO;
import com.fpoly.baithi.Model.ThemGhiChu;
import com.fpoly.baithi.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ThemGhiChuAdapter extends RecyclerView.Adapter<ThemGhiChuAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ThemGhiChu> list;
    private GhiChuDAO danhSachDAO;

    public ThemGhiChuAdapter(Context context, ArrayList<ThemGhiChu> list) {
        this.context = context;
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_ghichu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        holder.txtND.setText(list.get(position).getTitle());
        holder.txtTime.setText(format.format(Date.parse(list.get(position).getDate())));
        holder.ivXoa.setOnClickListener(view -> {
            danhSachDAO = new GhiChuDAO(context);
            danhSachDAO.DeleteGhiChu(list.get(position).getId());
            list = danhSachDAO.layDSGhiChu();
            notifyDataSetChanged();
        });
//        holder.ivSua.setOnClickListener(view -> {
//            createDialog(list.get(position));
//        });
//        holder.itemView.setOnClickListener(view -> {
//            createDialog_chitiet(list.get(position));
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtND,txtTime;
        ImageView ivXoa,ivSua;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtND = itemView.findViewById(R.id.txtND);
            txtTime = itemView.findViewById(R.id.txtTime);
            ivSua = itemView.findViewById(R.id.ivSua);
            ivXoa = itemView.findViewById(R.id.ivXoa);
        }
    }
//    private void createDialog_chitiet(ThemGhiChu ghichu) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
//        View v = inflater.inflate(R.layout.dialog_chitiet,null);
//        builder.setView(v);
//        EditText edt_nd = v.findViewById(R.id.edt_nd);
//        EditText edt_time = v.findViewById(R.id.edt_time);
//        edt_nd.setText(ghichu.getTenGhiChu()) ;
//        edt_time.setText(ghichu.getThoiGian()) ;
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
//    private void createDialog(ThemGhiChu ghichu) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
//        View v = inflater.inflate(R.layout.dialog_chitiet,null);
//        builder.setView(v);
//        EditText edt_nd = v.findViewById(R.id.edt_nd);
//        EditText edt_time = v.findViewById(R.id.edt_time);
//        edt_nd.setText(ghichu.getTenGhiChu()) ;
//        edt_time.setText(ghichu.getThoiGian()) ;
//        edt_time.setOnClickListener(view -> {
//            createDatePicker(edt_time);
//        });
//        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ghichu.setTenGhiChu(edt_nd.getText().toString());
//                ghichu.setThoiGian(edt_time.getText().toString());
//                danhSachDAO = new GhiChuDAO(context);
//                boolean check = danhSachDAO.UpdateGhiChu(ghichu);
//                notifyDataSetChanged();
//                if(check==true){
//                    Toast.makeText(context,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(context,"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
    public void createDatePicker(EditText edt) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        Date date;
        try {
            date = format.parse(edt.getText().toString());
        } catch (ParseException e) {
            // Nếu không thể chuyển đổi ngày tháng từ ô text thành Date object, sử dụng ngày hiện tại
            date = new Date();
        }
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        edt.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog( context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(year,month,dayOfMonth);
                    edt.setText(format.format(calendar.getTime()));
                }
            },year,month,day);
            datePickerDialog.show();
        });
    }
}
