package com.mobiotics.deeptimay.deeptimaymobiotics.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobiotics.deeptimay.deeptimaymobiotics.model.Records;

import java.util.ArrayList;

public class MobioticsDb extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "mobiotics";

    // Contacts table name
    private static final String RECORD_TABLE = "record_table";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String THUMB = "thumb";
    private static final String URL = "url";
    private static final String START_TIME = "startTime";

    public MobioticsDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTIFICATION_TABLE = "CREATE TABLE " + RECORD_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT," + DESCRIPTION + " TEXT," + THUMB + " TEXT,"
                + URL + " TEXT," + START_TIME + " TEXT"
                + ")";

        db.execSQL(CREATE_NOTIFICATION_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + RECORD_TABLE);
        // Create tables again
        onCreate(db);
    }

    // Adding new
    public void addRecords(ArrayList<Records> recordsArrayList) {

        SQLiteDatabase db = this.getWritableDatabase();
        for (Records records : recordsArrayList) {

            ContentValues values = new ContentValues();
            values.put(TITLE, records.getTitle());
            values.put(DESCRIPTION, records.getDescription());
            values.put(THUMB, records.getThumb());
            values.put(URL, records.getUrl());
            values.put(START_TIME, records.getStartTime());

            Log.d("Inserting: ", records.toString());
            // Inserting Row
            db.insert(RECORD_TABLE, null, values);
        }
        db.close(); // Closing database connection
    }

    // Getting All
    public ArrayList<Records> getAllRecords() {
        ArrayList<Records> recordsArrayList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + RECORD_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Records records = new Records();
                records.setId(cursor.getString(0));
                records.setTitle(cursor.getString(1));
                records.setDescription(cursor.getString(2));
                records.setThumb(cursor.getString(3));
                records.setUrl(cursor.getString(4));
                records.setStartTime(cursor.getString(5));

                recordsArrayList.add(records);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return recordsArrayList;
    }

    public int updateRecord(Records records) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(START_TIME, records.getStartTime());

        // updating row
        int i = db.update(RECORD_TABLE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(records.getId())});
        Log.d("after Insert", i + "");
        db.close();
        return i;
    }


    public Records getRecord(String id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(RECORD_TABLE, new String[]{KEY_ID, TITLE, DESCRIPTION, THUMB, URL, START_TIME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Records records = new Records();
        if (cursor != null) {
            records.setId(cursor.getString(0));
            records.setTitle(cursor.getString(1));
            records.setDescription(cursor.getString(2));
            records.setThumb(cursor.getString(3));
            records.setUrl(cursor.getString(4));
            records.setStartTime(cursor.getString(5));
        }
        cursor.close();
        db.close();
        return records;
    }

    public Records getNextRecord(String id) {

        SQLiteDatabase db = this.getReadableDatabase();

        int i = getRecordCount();
        int j = Integer.parseInt(id);

        if (j < i) {
            id = (j + 1) + "";
        } else {
            id = 1 + "";
        }

        Cursor cursor = db.query(RECORD_TABLE, new String[]{KEY_ID, TITLE, DESCRIPTION, THUMB, URL, START_TIME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Records records = new Records();
        if (cursor != null) {
            records.setId(cursor.getString(0));
            records.setTitle(cursor.getString(1));
            records.setDescription(cursor.getString(2));
            records.setThumb(cursor.getString(3));
            records.setUrl(cursor.getString(4));
            records.setStartTime(cursor.getString(5));
        }
        Log.d("cursor", records.toString());
        cursor.close();
        db.close();
        return records;
    }


    public int getRecordCount() {
        String countQuery = "SELECT  * FROM " + RECORD_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public void deleteAllRecord() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + RECORD_TABLE); //delete all rows in a table
        db.close();
    }
}