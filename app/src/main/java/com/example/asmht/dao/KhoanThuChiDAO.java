package com.example.asmht.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asmht.database.ThuChiDB;
import com.example.asmht.model.KhoanThuChi;
import com.example.asmht.model.Loai;
import com.example.asmht.database.ThuChiDB;
import com.example.asmht.model.KhoanThuChi;
import com.example.asmht.model.Loai;

import java.util.ArrayList;

public class KhoanThuChiDAO {
    ThuChiDB thuChiDB;

    public KhoanThuChiDAO(Context context) {
        thuChiDB = new ThuChiDB(context);
    }


    public ArrayList<Loai> getDSLoai(String loai) {
        ArrayList<Loai> list = new ArrayList<>();
        SQLiteDatabase database = thuChiDB.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM LOAI", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String trangthai = cursor.getString(2);
                if (trangthai.equals(loai)) {
                    list.add(new Loai(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
                }
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean insertLoai(Loai loai) {
        SQLiteDatabase database = thuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenLoai", loai.getTenLoai());
        contentValues.put("trangThai", loai.getTrangThai());
        long check = database.insert("LOAI", null, contentValues);
        if (check == -1) {
            return false;
        }
        return true;
    }

    public boolean updateLoai(Loai loai) {
        SQLiteDatabase database = thuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maLoai", loai.getMaLoai());
        contentValues.put("tenLoai", loai.getTenLoai());
        contentValues.put("trangThai", loai.getTrangThai());
        long check = database.update("LOAI", contentValues, "maLoai=?", new String[]{String.valueOf(loai.getMaLoai())});
        if (check == -1) {
            return false;
        }
        return true;
    }

    public boolean deleteLoai(int maLoai) {
        SQLiteDatabase database = thuChiDB.getWritableDatabase();
        long check = database.delete("LOAI", "maLoai=?", new String[]{String.valueOf(maLoai)});
        if (check == -1) {
            return false;
        }
        return true;
    }

    public ArrayList<KhoanThuChi> getDSKhoanThuChi(String loai) {
        ArrayList<KhoanThuChi> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = thuChiDB.getReadableDatabase();
        /*
         * loai(maLoai, tenLoai, trangThai)
         * khoanthuchi(maKhoan, tien, maLoai)
         *
         * select k.maKhoan, k.tien, k.maLoai, l.tenLoai
         * from khoanthuchi k , loai l
         * where k.maLoai = l.maLoai and l.trangThai = 'thu/chi'
         *
         * */

        String query = "select k.maKhoan,k.tenKhoan, k.tien, k.ngay,  k.maLoai, l.tenLoai from khoanthuchi k , loai l where k.maLoai = l.maLoai and l.trangThai = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{loai});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new KhoanThuChi(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3),cursor.getInt(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }

        return list;
    }

    public boolean insertKhoanThuChi(KhoanThuChi khoanThuChi) {
        SQLiteDatabase sqLiteDatabase = thuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maLoai", khoanThuChi.getMaLoai());
        contentValues.put("tenKhoan", khoanThuChi.getTenKhoan());
        contentValues.put("tien", khoanThuChi.getTien());
        contentValues.put("ngay", khoanThuChi.getNgay());
        long check = sqLiteDatabase.insert("KHOANTHUCHI", null, contentValues);
        if (check == -1) {
            return false;
        }
        return true;
    }

    public boolean updateKhoanThuChi(KhoanThuChi khoanThuChi) {
        SQLiteDatabase sqLiteDatabase = thuChiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maLoai", khoanThuChi.getMaLoai());
        contentValues.put("tenKhoan", khoanThuChi.getTenKhoan());
        contentValues.put("tien", khoanThuChi.getTien());
        contentValues.put("ngay", khoanThuChi.getNgay());
        long check = sqLiteDatabase.update("khoanthuchi", contentValues, "maKhoan=?", new String[]{String.valueOf(khoanThuChi.getMaKhoan())});
        if (check == -1) {
            return false;
        }
        return true;
    }

    public boolean deleteKhoanThuChi(int maKhoan) {
        SQLiteDatabase sqLiteDatabase = thuChiDB.getWritableDatabase();
        long check = sqLiteDatabase.delete("khoanthuchi", "maKhoan=?", new String[]{String.valueOf(maKhoan)});
        if (check == -1) {
            return false;
        }
        return true;
    }

    public float[] getThongTinThuChi() {
        SQLiteDatabase sqLiteDatabase = thuChiDB.getReadableDatabase();
        int thu = 0, chi = 0;
        //select sum(tien)
        //from giaodich
        //where maloai in (select maloai from phanloai where thangthai = 'thu')
        Cursor cursorThu = sqLiteDatabase.rawQuery("select sum(tien) from KHOANTHUCHI where maloai in (select maloai from loai where trangthai = 'thu') ", null);
        if (cursorThu.getCount() != 0) {
            cursorThu.moveToFirst();
            thu = cursorThu.getInt(0);
        }
        //select sum(tien)
        //from giaodich
        //where maloai in (select maloai from phanloai where thangthai = 'chi')
        Cursor cursorChi = sqLiteDatabase.rawQuery("select sum(tien) from KHOANTHUCHI where maloai in (select maloai from loai where trangthai = 'chi') ", null);
        if (cursorChi.getCount() != 0) {
            cursorChi.moveToFirst();
            chi = cursorChi.getInt(0);
        }
        float[] ketQua = new float[]{thu, chi};
        return ketQua;
    }

}