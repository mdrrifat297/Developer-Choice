package com.android.developerchoice;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SQLiteActivity extends AppCompatActivity {

    String data = "";

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


        // go back
        ImageButton goBack = findViewById(R.id.goBack);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SQLiteHelper sqlitehelper = new SQLiteHelper(this);
        SQLiteDatabase sqLiteDatabase = sqlitehelper.getWritableDatabase();

        Button putButton = findViewById(R.id.putButton);
        EditText name = findViewById(R.id.name);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        putButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = String.valueOf(name.getText());
                String uemail = String.valueOf(email.getText());
                String upassword = String.valueOf(password.getText());

                if (uname.isEmpty() || uemail.isEmpty() || upassword.isEmpty()) {
                    if (uname.isEmpty()) {
                        name.requestFocus();
                        name.setError("Name is required");
                    }
                    else if (uemail.isEmpty()) {
                        email.requestFocus();
                        email.setError("Email is required");
                    } else {
                        password.requestFocus();
                        password.setError("Password is required");
                    }
                    return;
                }

                long rowID = sqlitehelper.insertData(uname, uemail);
                if (rowID == -1) {
                    Toast.makeText(SQLiteActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
                } else {
                    name.setText("");
                    email.setText("");
                    Toast.makeText(SQLiteActivity.this, "Data insert succesfully.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button showButton = findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = sqlitehelper.getData();

                if (cursor != null && cursor.moveToNext()) {
                    do {
                        // Retrieve data by column names
                        String id = cursor.getString(0);
                        String name = cursor.getString(1);
                        String email = cursor.getString(2);

                        // Do something with the retrieved data (e.g., print it or add it to a list)
                        data = data + (id + " -- " + name + " -- " + email + "\n");

                    } while (cursor.moveToNext());

                    TextView displayData = (findViewById(R.id.displayData));
                    displayData.setText(data);
                    data = "";
                }
            }
        });

    }
}