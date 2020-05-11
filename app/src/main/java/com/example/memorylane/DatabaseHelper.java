package com.example.memorylane;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "memories";
    public static final String TABLE_NAME = "day_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DAY";
    public static final String COL_3 = "MONTH";
    public static final String COL_4 = "YEAR";
    public static final String COL_5 = "MORNING";
    public static final String COL_6 = "MORNING_MOOD";
    public static final String COL_7 = "AFTERNOON";
    public static final String COL_8 = "AFTERNOON_MOOD";
    public static final String COL_9 = "EVENING";
    public static final String COL_10 = "EVENING_MOOD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID TEXT PRIMARY KEY," +
                "DAY TEXT,MONTH TEXT,YEAR TEXT,MORNING TEXT,MORNING_MOOD TEXT,AFTERNOON TEXT," +
                "AFTERNOON_MOOD TEXT,EVENING TEXT,EVENING_MOOD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean isDayRegistered(String id) {
        SQLiteDatabase  db = getWritableDatabase();
        String Query = "select * from " + "day_table" + " where ID = '" + id + "'";
        Cursor res = db.rawQuery(Query, null);
        if(res.getCount() <= 0){
            res.close();
            return false;
        }
        res.close();
        return true;
    }

    public boolean insertData(String id,String day,String month,String year,String morning,
                              String morning_mood,String afternoon,
                              String afternoon_mood,String evening,String evening_mood) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,day);
        contentValues.put(COL_3,month);
        contentValues.put(COL_4,year);
        contentValues.put(COL_5,morning);
        contentValues.put(COL_6,morning_mood);
        contentValues.put(COL_7,afternoon);
        contentValues.put(COL_8,afternoon_mood);
        contentValues.put(COL_9,evening);
        contentValues.put(COL_10,evening_mood);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Cursor getDataById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +
                TABLE_NAME + " where ID = '" + id + "'", null);
        return res;
    }

    public boolean updateData(String id,String day,String month,String year,String morning,
                              String morning_mood,String afternoon,
                              String afternoon_mood,String evening,String evening_mood) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,day);
        contentValues.put(COL_3,month);
        contentValues.put(COL_4,year);
        contentValues.put(COL_5,morning);
        contentValues.put(COL_6,morning_mood);
        contentValues.put(COL_7,afternoon);
        contentValues.put(COL_8,afternoon_mood);
        contentValues.put(COL_9,evening);
        contentValues.put(COL_10,evening_mood);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
}