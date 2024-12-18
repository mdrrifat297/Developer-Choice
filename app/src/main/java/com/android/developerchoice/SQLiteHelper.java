package com.android.developerchoice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class SQLiteHelper extends SQLiteOpenHelper {

    private  static  final String DATABASE_NAME = "database.db";
    private  static  final String TABLE_NAME = "user";
    private  static  final String ID = "ID";
    private  static  final String NAME = "NAME";
    private  static  final String EMAIL = "EMAIL";
    private  static  final int DATABASE_VERSION = 1;
    private Context context;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME VERCHAR(30), "
                    + "EMAIL VERCHAR(20))");
            Toast.makeText(context, "Table Create Successfuly", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error " + e, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long insertData(String name, String email) {
        // Open the database for writing
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new ContentValues object to hold the data
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(EMAIL, email);

        long rowID = db.insert(TABLE_NAME, null, values);

        db.close();

        return rowID;
    }

    public Cursor getData() {
        // Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the query to fetch all data from the table
        String query = "SELECT * FROM " + TABLE_NAME;

        // Execute the query and return the result as a Cursor
        Cursor cursor = db.rawQuery(query, null);

        return cursor; // Caller will handle Cursor iteration
    }

    public boolean updateData() {
        return true;
    }
}
