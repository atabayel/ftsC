package com.example.user.ftst;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 07.03.2018.
 */

public class DB {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private String table;
    private final Context context;

    public DB(Context _context, String _table) {
        context = _context;
        table = _table;
    }

    public void open() {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (dbHelper != null) dbHelper.close();
    }

    // Только для таблицы "Orders"
    public Cursor getAllOrders() {
        Cursor cursor;
        String str;
        if (table.equals(MyConstants.DB_TABLE_ORDER)) {
            cursor = db.query(table, null, null, null, null, null, null);
            return cursor;
        } else {
            return null;
        }

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    str = "";
                    for (String cn : cursor.getColumnNames()) {
                        str = str.concat(cn + " = "
                                + cursor.getString(cursor.getColumnIndex(cn)) + "; ");
                    }

                } while (cursor.moveToNext());
            }
            cursor.close();
        } else
            dbHelper.close();
    }


    public String getOrder(String id) {
        Cursor c = db.query(table, new String[]{"id"}, "name = ?", new String[]{id}, null, null, null);
        if (c.moveToFirst()) {
            return c.getString(2);
        }
        return "no";
    }

    // Только для таблицы "Orders"
    public void addOrder(Order order) {
        if (table.equals(MyConstants.DB_TABLE_ORDER)) {
            ContentValues cv = new ContentValues();
            cv.put(MyConstants.ORDER_ID, order.id);
            cv.put(MyConstants.ORDER_DEADLINE, order.deadLine);
            cv.put(MyConstants.ORDER_LANGUAGE, order.language);
            cv.put(MyConstants.ORDER_DIRECTION, order.direction);
            cv.put(MyConstants.ORDER_PAGE_COUNT, order.pageCount);
            cv.put(MyConstants.ORDER_PRICE, order.price);
            db.insert(table, null, cv);
        }
    }

    // Только для таблицы "Orders"
    public void delOrder(String id) {
        if (table.equals(MyConstants.DB_TABLE_ORDER)) {
            db.delete(table, MyConstants.ORDER_ID + " = " + id, null);
        }
    }

    // Только для таблицы "Preference"
    public void setVariable(String name, String value) {
        ContentValues cv = new ContentValues();
        Cursor c = db.query(table, new String[]{"name"}, "name = ?", new String[]{name}, null, null, null);
        cv.put("name", name);
        cv.put("value", value);
        long rowCount;
        if (!c.moveToFirst()) {
            db.insert(table, null, cv);
        } else {
            db.update(table, cv, "name = ?", new String[]{name});
        }
        c.close();
    }

    public String getVariable(String name) {
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
        return result;
    }

    class DBHelper extends SQLiteOpenHelper {

        private DBHelper(Context context) {
            // конструктор суперкласса
            super(context, MyConstants.MY_DB, null, MyConstants.MY_DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // создаем таблицу с полями
            if (table.equals(MyConstants.DB_TABLE_ORDER)) {
                String CREATE_TABLE = "create table " + table + " ("
                        + MyConstants.ORDER_ID + "text,"
                        + MyConstants.ORDER_DEADLINE + "text,"
                        + MyConstants.ORDER_LANGUAGE + "text,"
                        + MyConstants.ORDER_DIRECTION + "text,"
                        + MyConstants.ORDER_PAGE_COUNT + "text,"
                        + MyConstants.ORDER_PRICE + "text);";
                db.execSQL(CREATE_TABLE);
            } else if (table.equals(MyConstants.DB_TABLE_PREFERENS)) {
                String CREATE_TABLE = "create table " + table + " ("
                        + "name text,"
                        + "value text);";
                db.execSQL(CREATE_TABLE);
            }
            ContentValues cv = new ContentValues();
            cv.put(MyConstants.ORDER_ID, "20180307305");
            cv.put(MyConstants.ORDER_DEADLINE, "10.03.2018");
            cv.put(MyConstants.ORDER_LANGUAGE, "Анлийский - Казахский");
            cv.put(MyConstants.ORDER_DIRECTION, "Нефть");
            cv.put(MyConstants.ORDER_PAGE_COUNT, "90");
            cv.put(MyConstants.ORDER_PRICE, "75 000");
            db.insert(table, null, cv);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //TODO: Реализовать Upgrade базы.
        }
    }
}
