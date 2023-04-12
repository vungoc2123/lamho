package com.fpoly.baithi.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ThemGhiChuAdapter extends RecyclerView.Adapter<ThemGhiChuAdapter.ViewHolder> {
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
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_ghichu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        holder.txtND.setText(list.get(position).getContent());
        holder.txtTitle.setText(list.get(position).getTitle());
        holder.txtTime.setText(format.format(Date.parse(list.get(position).getDate())));
        holder.ivXoa.setOnClickListener(view -> {
            danhSachDAO = new GhiChuDAO(context);
            danhSachDAO.DeleteGhiChu(list.get(position).getId());
            list = danhSachDAO.layDSGhiChu();
            notifyDataSetChanged();
        });
        holder.ivSua.setOnClickListener(view -> {
            openEditDialog(list.get(position));
        });
    }

    private void openEditDialog(ThemGhiChu themGhiChu) {
        // Tạo dialog
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Set view cho dialog
        dialog.setContentView(R.layout.custom_dialog);

        Window window = dialog.getWindow();
        if (window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // Ánh xạ đến các widget
        EditText edtTitle = dialog.findViewById(R.id.edtTitle);
        EditText edtContent = dialog.findViewById(R.id.edtContent);
        EditText edtTime = dialog.findViewById(R.id.edtTime);
        // Set nội dung của task đang sửa cho các ô nhập
        edtTitle.setText(themGhiChu.getTitle());
        edtContent.setText(themGhiChu.getContent());
        edtTime.setText(themGhiChu.getDate());

        // Mở datedialog khi nhấn giữ
        edtTime.setOnLongClickListener(view -> {
            createDatePicker(edtTime);
            return true;
        });

        Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(v -> {
            // Kiểm tra trống
            if (edtTitle.getText().toString().length() == 0
                    || edtContent.getText().toString().length() == 0
                    || edtTime.getText().toString().length() == 0
            ) {
                Toast.makeText(context, "Không được để trống dữ liệu!", Toast.LENGTH_SHORT).show();
            }else {
                // Khởi tạo DAO
                danhSachDAO = new GhiChuDAO(context);
                // Chỉnh sửa thông tin
                themGhiChu.setTitle(edtTitle.getText().toString());
                themGhiChu.setContent(edtContent.getText().toString());
                themGhiChu.setDate(edtTime.getText().toString());
                // Lưu vào database
                danhSachDAO.UpdateGhiChu(themGhiChu);
                // Load lại recyclerview
                notifyDataSetChanged();
                Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                // Tắt dialog
                dialog.dismiss();
            }

        });

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.setCancelable(true);
        dialog.show();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtND, txtTime, txtTitle;
        ImageView ivXoa, ivSua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
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
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(year, month, dayOfMonth);
                    edt.setText(format.format(calendar.getTime()));
                }
            }, year, month, day);
            datePickerDialog.show();
        });
    }
}
