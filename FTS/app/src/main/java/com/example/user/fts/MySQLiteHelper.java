package com.example.user.fts;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 26.02.2018.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FTSDB";
    private static final String TABLE_NAME = "TableVariable";
    private static final String COLUMN_1 = "var_nm";
    private static final String COLUMN_2 = "var_vl";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOK_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
                "var_nm TEXT, "+
                "var_vl TEXT )";
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // create fresh books table
        this.onCreate(db);
    }

    public void addVariable (String name, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_1, name);
        values.put(COLUMN_2, value);

    }
}
