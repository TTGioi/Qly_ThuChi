package com.example.asmht.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ThuChiDB extends SQLiteOpenHelper {

    public ThuChiDB(Context context){
        super(context,"THUCHI_DB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qLoai = "CREATE TABLE LOAI(maLoai integer primary key autoincrement, tenLoai text, trangThai text)";
        db.execSQL(qLoai);

        String qKhoan = "CREATE TABLE KHOANTHUCHI(maKhoan integer primary key autoincrement,tenKhoan text, tien interger, ngay date, maLoai interger)";
        db.execSQL(qKhoan);

        String insLoai = "INSERT INTO LOAI VALUES(null, 'lương', 'thu'),(null, 'đi chợ', 'chi')";
        db.execSQL(insLoai);

        String insKHOANTHUCHI = "INSERT INTO KHOANTHUCHI VALUES(null,'thang 8', 500000,'2022-8-15', 1),(null,'mua rau', 500000,'2022-8-20', 2)";
        db.execSQL(insKHOANTHUCHI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            String dLoai = "DROP TABLE IF EXISTS LOAI";
            db.execSQL(dLoai);
            String dKhoan = "DROP TABLE IF EXISTS KHOANTHUCHI";
            db.execSQL(dKhoan);
        }
    }
}
