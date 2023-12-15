package com.diepvanty.sqlite;

import android.content.Context;

import java.util.LinkedList;

public class Dao_SinhVien {
    private SQLiteHelper dbHelper;
    public Dao_SinhVien(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public boolean insert_SinhVien(SinhVien sinhVien) {
        dbHelper.insert(sinhVien.getHovaten());
        return true;
    }

    public void updateSinhVien(int id, String name) {
        dbHelper.update(name, id );
    }

    public void deleteSinhVien(int id) {
        dbHelper.delete(id);
    }

    public LinkedList<SinhVien> getAllSinhVien(int orderby) {
        return dbHelper.getStudents();
    }
}
