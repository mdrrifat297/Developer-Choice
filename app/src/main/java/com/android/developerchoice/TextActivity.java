package com.android.developerchoice;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Context;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class TextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_text);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(v -> finish());


//        writeToFile(FILENAME, data, getApplicationContext());
//        String fileContent = readFromFile(FILENAME, getApplicationContext());
    }

    // file write
    public void writeToFile(String filename, String data, Context context) {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(data.getBytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // file read
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