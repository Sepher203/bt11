package com.diepvanty.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "dbStudents";
    private static final String tbStudents = "students";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    public SQLiteHelper(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + tbStudents + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + tbStudents);
        onCreate(db);
    }
    void insert(String name){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(tbStudents,null, cValues);
        db.close();
    }
    @SuppressLint("Range") //todo : RANGE
    public ArrayList<HashMap<String, String>> _getStudents(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name FROM "+ tbStudents;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            userList.add(user);
        }
        return  userList;
    }

    public LinkedList<SinhVien> getStudents(){
        SQLiteDatabase db = this.getWritableDatabase();
        LinkedList<SinhVien> userList = new LinkedList<>();
        String query = "SELECT name FROM "+ tbStudents;
//        Cursor cursor = db.rawQuery(query,null);
        Cursor cursor = db.rawQuery("SELECT * FROM " + tbStudents, null);
//        Cursor cursor = db.query(tbStudents, new String[]{ KEY_ID,KEY_NAME },new String[]{String.valueOf(userid)},null, null, null, null);
        while (cursor.moveToNext()){
            @SuppressLint("Range") SinhVien sinhVien = new SinhVien(
                    cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_NAME))
            );
            userList.add(sinhVien);
        }
        return  userList;
    }

    // Get User Details based on userid
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, location, designation FROM "+ tbStudents;
        Cursor cursor = db.query(tbStudents, new String[]{KEY_NAME}, KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            userList.add(user);
        }
        return  userList;
    }
    public void delete(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tbStudents, KEY_ID+" = ?",new String[]{String.valueOf(userid)});
        db.close();
    }
    public int update(String name, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_NAME, name);
        int count = db.update(tbStudents, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }
}

