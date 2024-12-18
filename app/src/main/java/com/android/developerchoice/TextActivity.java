package com.android.developerchoice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.content.Context;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import androidx.appcompat.app.AppCompatActivity;

public class TextActivity extends AppCompatActivity {

    EditText largeTextInput;
    Button textDataSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        // Initialize views
        largeTextInput = findViewById(R.id.largeTextInput);
        textDataSave = findViewById(R.id.taxtDataSave);

        // Back button logic
        ImageButton goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(v -> finish());

        // Read and display saved data
        try {
            String savedData = readFromFile("data.txt", getApplicationContext());
            largeTextInput.setText(savedData);
        } catch (Exception e) {
            Toast.makeText(TextActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
        }

        // Save button logic
        textDataSave.setOnClickListener(v -> {
            String data = largeTextInput.getText().toString();
            Toast.makeText(TextActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
            writeToFile("data.txt", data, getApplicationContext());
        });
    }

    // File write operation
    public void writeToFile(String filename, String data, Context context) {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(data.getBytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // File read operation
    public String readFromFile(String filename, Context context) {
        StringBuilder result = new StringBuilder();
        try (FileInputStream fis = context.openFileInput(filename)) {
            int i;
            while ((i = fis.read()) != -1) {
                result.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
