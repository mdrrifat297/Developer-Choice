package com.android.developerchoice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class AccountActivity extends AppCompatActivity {

    private static final int GALLERY_REQ_CODE = 1000;
    TextView userPhotoUplode;
    ImageView userPhoto;
    private DatabaseReference usersRef;
    String userName, name, dob, email, location, profile, updateName, updateDob, updateLocation, updateProfile;

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


        // Initialize Firebase Database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        // Initialize variables
        ImageButton goBack = findViewById(R.id.goBack);
        userPhotoUplode = findViewById(R.id.userPhotoUplode);
        userPhoto = findViewById(R.id.userPhoto);
        Button saveDataToDarabase = findViewById(R.id.saveDataToDarabase);

        goBack.setOnClickListener(v -> finish());

        // user name gat
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", "");
        userName = "";
        for (int i = 0; i < userEmail.length(); i++) {
            if (userEmail.charAt(i) == '@') {
                break;
            } else {
                userName += userEmail.charAt(i);
            }
        }
        fetchUserData(userName);


        userPhotoUplode.setOnClickListener(v -> {
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

    // fatch user data
    private void fetchUserData(String username) {
        // Fetch user data by username
        usersRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get user data
                    name = dataSnapshot.child("name").getValue(String.class);
                    dob = dataSnapshot.child("date_of_birth").getValue(String.class);
                    email = dataSnapshot.child("email").getValue(String.class);
                    location = dataSnapshot.child("location").getValue(String.class);
                    profile = dataSnapshot.child("profile_img").getValue(String.class);

                    ((TextView) findViewById(R.id.userName)).setText(name);
                    ((TextView) findViewById(R.id.userEmail)).setText(email);
                    ((TextView) findViewById(R.id.userDob)).setText(dob);
                    ((TextView) findViewById(R.id.userLocation)).setText(location);

                    Toast.makeText(AccountActivity.this, profile, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AccountActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error: " + databaseError.getMessage());
                Toast.makeText(AccountActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // update user data
    public void uplodeDataOnFirebase() {

    }
}
