package com.fpoly.baithi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fpoly.baithi.Database.DbHelper;
import com.fpoly.baithi.Model.ThemGhiChu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GhiChuDAO {
    private DbHelper dbHelper;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    public GhiChuDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<ThemGhiChu> layDSGhiChu(){
        ArrayList<ThemGhiChu> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM GHICHU ",new String[]{});
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                list.add(new ThemGhiChu(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean themGhiChu(ThemGhiChu ghiChu){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",ghiChu.getTitle());
        contentValues.put("content",ghiChu.getContent());
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString="";
        try {
            Date date = inputFormat.parse(ghiChu.getDate());
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd");
            dateString = outputFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        contentValues.put("date",dateString);
        long check = sqLiteDatabase.insert("GHICHU",null,contentValues);
        if (check == -1)
            return false;
        return true;
    }

    public boolean UpdateGhiChu(ThemGhiChu ghiChu){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",ghiChu.getTitle());
        contentValues.put("content",ghiChu.getContent());
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString="";
        try {
            Date date = inputFormat.parse(ghiChu.getDate());
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd");
            dateString = outputFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        contentValues.put("date",dateString);
        long check = sqLiteDatabase.update("GHICHU",contentValues,"id =?",new String[]{String.valueOf(ghiChu.getId())});
        if (check == -1)
            return false;
        return true;
    }

    public boolean DeleteGhiChu(int id){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("GHICHU","id =?",new String[]{String.valueOf(id)});
        if (check == -1)
            return false;
        return true;
    }
}
