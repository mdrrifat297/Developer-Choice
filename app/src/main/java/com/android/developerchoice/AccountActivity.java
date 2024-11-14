package com.android.developerchoice;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuthEmailException;


public class AccountActivity extends AppCompatActivity {

    private static final int GALLERY_REQ_CODE = 1000;
    ImageView userPhoto;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // databaseReference = FirebaseDatabase.getInstance().getReference();

        // Initialize variables
        ImageButton goBack = findViewById(R.id.goBack);
        userPhoto = findViewById(R.id.userPhoto);
        // Button uploadPhoto = findViewById(R.id.uplodePhoto);
        Button saveDataToDarabase = findViewById(R.id.saveDataToDarabase);

        goBack.setOnClickListener(v -> finish());


        userPhoto.setOnClickListener(v -> {
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery, GALLERY_REQ_CODE);
        });

        saveDataToDarabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uplodeDataOnFirebase();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE && data != null) {
                userPhoto.setImageURI(data.getData());
            }
        }
    }

    public void uplodeDataOnFirebase() {
        String userName = "Md Rifat Rahman";
        String userAge = "17";


    }
}
