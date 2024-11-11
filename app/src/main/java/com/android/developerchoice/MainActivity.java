package com.android.developerchoice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // all variable
        ImageButton openNavigation = findViewById(R.id.openNavigation);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView  navigationView = findViewById(R.id.navigationView);


        // open  navigation drawer
        openNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        // navigation item selected
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID =  item.getItemId();

                if (itemID == R.id.drawerAccount) {
                    startActivity(new Intent(MainActivity.this, AccountActivity.class));
                } else if (itemID == R.id.drawerSQLite) {
                    startActivity(new Intent(MainActivity.this, SQLiteActivity.class));
                } else if (itemID == R.id.drawerSensor) {
                    startActivity(new Intent(MainActivity.this, SensorActivity.class));
                } else if (itemID == R.id.drawerWebsite) {
                    startActivity(new Intent(MainActivity.this, WebsiteActivity.class));
                } else if (itemID == R.id.drawerLogout) {
                    showConfirmationDialog();
                }

                drawerLayout.close();
                return false;
            }
        });
    }


    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to log out?");

        // Set up the "Yes" button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Code to execute when "Yes" is pressed
                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set up the "No" button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Code to execute when "No" is pressed
                dialog.dismiss();
            }
        });

        // Add a "Dismiss" button
        builder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Close the dialog when clicked
            }
        });

        // Make the dialog not cancelable by tapping outside
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);  // Prevent dismissing by tapping outside
        dialog.show();
    }

}