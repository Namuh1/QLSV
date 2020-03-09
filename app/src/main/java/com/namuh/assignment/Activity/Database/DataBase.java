package com.namuh.assignment.Activity.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase<TABLE_CREATE> extends SQLiteOpenHelper {
    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

        public void QueryData(String sql) {
            SQLiteDatabase database = getReadableDatabase();
            database.execSQL(sql);


        }

        public Cursor GetData(String sql) {
            SQLiteDatabase database = getWritableDatabase();
            return database.rawQuery(sql, null);
        }


        public void onCreate() {

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



}
