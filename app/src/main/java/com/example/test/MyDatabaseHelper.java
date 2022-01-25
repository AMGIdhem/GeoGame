package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.sql.Blob;

class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Locations.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_locations";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_IMG = "location_img";
    private static final String COLUMN_ADDRESS = "location_address";
    private static final String COLUMN_LAT = "location_lat";
    private static final String COLUMN_LONGIT = "location_longit";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_IMG + " INTEGER, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_LAT + " TEXT, " +
                COLUMN_LONGIT + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addLocation(int id, int img, String address, String lat, String longit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, id);
        cv.put(COLUMN_IMG, img);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_LAT, lat);
        cv.put(COLUMN_LONGIT, longit);
        long result = db.insert(TABLE_NAME, null, cv);

    }
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor getLocation(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery("SELECT * FROM my_locations WHERE id=1", null);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=?", new String[] {String.valueOf(id)} );

            //loc = new Location(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getLong(3), cursor.getLong(4));

        return cursor;
    }
}