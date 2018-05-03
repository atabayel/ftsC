package com.example.user.fts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MyPreferences {

    private DBHelper dbHelper;
    private String table;
    //private SQLiteDatabase db;

    MyPreferences(Context context, String table) {
        dbHelper = new DBHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
        this.table = table;
    }

    public boolean setVariable(String name, String value) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor c = db.query(table, new String[]{"name"}, "name = ?", new String[]{name}, null, null, null);
        cv.put("name", name);
        cv.put("value", value);
        long rowCount;
        if (!c.moveToFirst()) {
            rowCount = db.insert(table, null, cv);
        } else {
            rowCount = db.update(table, cv, "name = ?", new String[]{name});
        }
        c.close();
        db.close();
        return  rowCount > 0;
    }

    public String getVariable(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor c = db.query(table, new String[]{"name", "value"}, "name = ?", new String[]{name}, null, null, null);
        String result;
        if (!c.moveToFirst()) {
            cv.put("name", name);
            cv.put("value", "0");
            db.insert(table, null, cv);
            result = "0";
        } else {
            result = c.getString(1);
        }
        c.close();
        db.close();
        return result;
    }

    class DBHelper extends SQLiteOpenHelper {

        private DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(MyContants.TAG, "--- onCreate database ---");
            // создаем таблицу с полями
            String CREATE_TABLE = "create table " + table + " ("
                    + "name text,"
                    + "value text" + ");";
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
