package com.example.classdemo.Util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {
    public DB(@Nullable Context context) {
        super(context, "Person.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(" +
                "id integer primary key autoincrement," +
                "name varchar(10)," +
                "phone varchar(20)," +
                "sex varchar(20)," +
                "userName varchar(20)," +
                "passaword varchar(20) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void setUserInfo(String name, String phone, String sex, String userName, String password) {
        getWritableDatabase().execSQL("insert into users values(null,?,?,?,?,?)", new Object[]{name, phone, sex, userName, password});
        close();
    }

    public List<DBValue> getUserInfo(String clum, String value) {
        List<DBValue> list = new ArrayList<>();
        Cursor cursor = null;
        if (!value.isEmpty()) {
            cursor = getWritableDatabase().rawQuery("select * from users where " + clum + " like ?", new String[]{value});
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new DBValue(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5)));
            }
            close();
            cursor.close();
            return list;
        }
        return list;
    }

    public void upDate(String phone, String passWord) {
        getWritableDatabase().execSQL("update users set passaword = ? where phone = ?", new Object[]{passWord, phone});
        close();
    }
}
