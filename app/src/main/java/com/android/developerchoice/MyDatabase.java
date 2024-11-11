package com.android.developerchoice;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {

    // All variables
    private static final String DATABASE_NAME = "UserData";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_DATA = "data";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_DATA +
                " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_EMAIL + " TEXT, "
                + KEY_PASSWORD + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);

        // Recreate the table
        onCreate(db);
    }

    public void insertData(String name, String email, String password) {
        // Open the database for writing
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new ContentValues object to hold the data
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);        // Insert the name
        values.put(KEY_EMAIL, email);      // Insert the email
        values.put(KEY_PASSWORD, password); // Insert the password

        // Insert the new row into the table
        db.insert(TABLE_DATA, null, values);

        // Close the database connection
        db.close();
    }

    public void getData() {
        // select quare
    }

}
