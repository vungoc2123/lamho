package com.fpoly.baithi.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context){
        super(context,"GHICHU",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GHICHU = "CREATE TABLE GHICHU(" +
                "id integer primary key AUTOINCREMENT," +
                "title text ," +
                "content text," +
                "date text)";
        db.execSQL(CREATE_GHICHU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS GHICHU");
            onCreate(db);
    }
}
