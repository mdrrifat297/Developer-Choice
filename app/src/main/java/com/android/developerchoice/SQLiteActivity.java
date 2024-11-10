package com.android.developerchoice;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SQLiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sqlite);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


//        MyDatabase db = new MyDatabase(this);  // 'this' is the context, usually from an activity
//        db.addUser("John Doe", "johndoe@example.com", "password123");

    }

//    public void addUser(String name, String email, String password) {
//        // Get writable database
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // Use ContentValues to prepare the data
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, name);        // Column name and value
//        values.put(KEY_EMAIL, email);      // Column email and value
//        values.put(KEY_PASSWORD, password);// Column password and value
//
//        // Insert row
//        db.insert(TABLE_DATA, null, values);
//
//        // Close the database connection
//        db.close();
//    }

}